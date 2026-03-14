package com.newrussia.game.core;

import com.newrussia.game.content.WorldBuilder;
import com.newrussia.game.model.Enemy;
import com.newrussia.game.model.GameState;
import com.newrussia.game.model.Location;
import com.newrussia.game.model.Npc;
import com.newrussia.game.model.Player;
import com.newrussia.game.systems.CombatSystem;
import com.newrussia.game.systems.MusicEngine;
import com.newrussia.game.systems.VoiceEngine;
import com.newrussia.game.ui.GameFrame;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Central coordinator for the whole game.
 *
 * This class intentionally owns most high-level flow, so Main stays minimal and
 * UI/components can stay focused on rendering and input.
 */
public final class GameController {
    private final WorldBuilder worldBuilder;
    private final MusicEngine musicEngine;
    private final VoiceEngine voiceEngine;
    private final CombatSystem combatSystem;
    private final Random random;

    private GameState state;
    private GameFrame frame;

    public GameController() {
        this.worldBuilder = new WorldBuilder();
        this.musicEngine = new MusicEngine();
        this.voiceEngine = new VoiceEngine();
        this.combatSystem = new CombatSystem();
        this.random = new Random();
    }

    public void boot() {
        state = worldBuilder.build();
        SwingUtilities.invokeLater(() -> {
            frame = new GameFrame(this);
            frame.open();
            startLocationMusic();
            logIntro();
            refreshUi();
        });
    }

    public void shutdown() {
        musicEngine.stop();
        if (frame != null) {
            frame.dispose();
        }
    }

    public GameState state() {
        return state;
    }

    public Player player() {
        return state.player();
    }

    public Location location() {
        return state.currentLocation();
    }

    public List<String> availableDestinations() {
        return new ArrayList<>(location().neighbors());
    }

    public void travelTo(String destinationId) {
        if (destinationId == null || destinationId.isBlank()) {
            append("Travel aborted: no destination selected.\n");
            return;
        }
        boolean ok = state.travelTo(destinationId);
        if (!ok) {
            append("Travel denied. Route not available from this location.\n");
            return;
        }

        append("You travel to " + location().title() + " in " + location().region() + ".\n");
        append("\n" + location().description() + "\n");
        append("Texture direction: " + location().textureDirection() + "\n");
        startLocationMusic();
        refreshUi();
    }

    public void playCutscene() {
        append("[CUTSCENE] " + location().cutscene() + "\n");
    }

    public void rest() {
        int before = player().hp();
        player().fullRest();
        append("You rest behind reinforced shutters. HP " + before + " -> " + player().hp() + ".\n");
        maybeGrantRestEvent();
        refreshUi();
    }

    public void talkToFirstNpc() {
        if (location().npcs().isEmpty()) {
            append("No active NPC dialogue in this location.\n");
            return;
        }

        Npc npc = location().npcs().get(0);
        frame.setPortraitSeed(npc.portraitSeed());
        voiceEngine.playNpcVoiceCue(npc.voiceTag());

        append("\n--- Dialogue: " + npc.name() + " [" + npc.role() + "] ---\n");
        npc.lines().forEach(line -> append(line.speaker() + ": \"" + line.text() + "\"\n"));

        int speech = player().special().speechPower();
        if (speech >= npc.speechDifficulty()) {
            append("Speech check: " + speech + " vs " + npc.speechDifficulty() + " -> SUCCESS\n");
            append("Reward unlocked: " + npc.successReward() + "\n");
            maybeAddRewardToInventory(npc.successReward());
        } else {
            append("Speech check: " + speech + " vs " + npc.speechDifficulty() + " -> FAILED\n");
            append("Reaction: " + npc.failReaction() + "\n");
        }

        refreshUi();
    }

    public void fightFirstEnemy() {
        if (location().enemies().isEmpty()) {
            append("No enemies currently spotted in this area.\n");
            return;
        }

        Enemy enemy = location().enemies().get(0);
        append("\n[COMBAT] You engage " + enemy.name() + "...\n");
        String result = combatSystem.runFight(player(), enemy);
        append(result);
        maybePostCombatLoot(enemy);
        refreshUi();
    }

    public void scanForHiddenPlaces() {
        if (location().hiddenPlaces().isEmpty()) {
            append("No hidden signatures detected.\n");
            return;
        }

        append("\n[SCAN] You inspect structural seams and radio reflections...\n");
        for (String hidden : location().hiddenPlaces()) {
            boolean discovered = tryDiscover(hidden);
            if (discovered) {
                append("Discovered: " + hidden + "\n");
                state.markHiddenDiscovered(hidden);
                maybeHiddenReward(hidden);
            } else {
                append("Possible trace found but not enough evidence: " + hidden + "\n");
            }
        }

        refreshUi();
    }

    public String buildHudText() {
        Player p = player();
        StringBuilder b = new StringBuilder();
        b.append("PLAYER: ").append(p.name()).append("\n");
        b.append("LEVEL: ").append(p.level()).append("\n");
        b.append("XP: ").append(p.xp()).append(" / ").append(p.xpNeededForNextLevel()).append("\n");
        b.append("HP: ").append(p.hp()).append(" / ").append(p.special().maxHp()).append("\n");
        b.append("AP: ").append(p.special().actionPoints()).append("\n");
        b.append("Initiative: ").append(p.special().initiative()).append("\n\n");

        b.append("S.P.E.C.I.A.L\n");
        b.append("S ").append(p.special().strength()).append("  P ").append(p.special().perception()).append("\n");
        b.append("E ").append(p.special().endurance()).append("  C ").append(p.special().charisma()).append("\n");
        b.append("I ").append(p.special().intelligence()).append("  A ").append(p.special().agility()).append("\n");
        b.append("L ").append(p.special().luck()).append("\n\n");

        b.append("Perks\n");
        p.perks().forEach(perk -> b.append("- ").append(perk.name()).append(": ").append(perk.description()).append("\n"));

        b.append("\nInventory\n");
        p.inventory().forEach(item -> b.append("- ").append(item).append("\n"));

        b.append("\nLocation: ").append(location().title()).append(" (" + location().region() + ")\n");
        b.append("Track: ").append(musicEngine.currentTrack()).append("\n");
        b.append("Known hidden places: ").append(state.discoveredHidden().size()).append("\n");

        return b.toString();
    }

    public String buildSceneHeader() {
        return "=== " + location().title() + " ===\n" +
                location().description() + "\n" +
                "Visual direction: " + location().textureDirection() + "\n";
    }

    public void append(String text) {
        if (frame != null) {
            frame.appendToStory(text);
        }
    }

    public void refreshUi() {
        if (frame != null) {
            frame.setHudText(buildHudText());
            frame.setLocationTitle(location().title());
        }
    }

    private void startLocationMusic() {
        musicEngine.start(location().ambientTrackName());
    }

    private void logIntro() {
        append("Welcome to Fallout: New Russia\n");
        append("A new run begins in the ruins of the capital.\n");
        append(buildSceneHeader());
    }

    private boolean tryDiscover(String hidden) {
        int roll = random.nextInt(100);
        int threshold = 42 + player().special().perception() * 4 + player().special().luck();
        if (state.discoveredHidden().contains(hidden)) {
            return true;
        }
        return roll < threshold;
    }

    private void maybeAddRewardToInventory(String reward) {
        if (!player().inventory().contains(reward)) {
            player().inventory().add(reward);
        }
    }

    private void maybeHiddenReward(String hidden) {
        if (hidden.toLowerCase().contains("med")) {
            player().inventory().add("Medkit");
            append("Loot: Medkit added to inventory.\n");
            return;
        }
        if (hidden.toLowerCase().contains("vault") || hidden.toLowerCase().contains("bunker")) {
            player().gainXp(25);
            append("Discovery XP: +25\n");
            return;
        }
        if (random.nextBoolean()) {
            player().inventory().add("Scrap Components");
            append("Loot: Scrap Components collected.\n");
        }
    }

    private void maybePostCombatLoot(Enemy enemy) {
        if (enemy.name().toLowerCase().contains("guard") || enemy.name().toLowerCase().contains("elite")) {
            player().inventory().add("Rifle Ammo");
            append("Loot: Rifle Ammo\n");
            return;
        }

        if (random.nextInt(100) < 35) {
            player().inventory().add("Stimpak");
            append("Loot: Stimpak\n");
        }
    }

    private void maybeGrantRestEvent() {
        int roll = random.nextInt(100);
        if (roll < 30) {
            append("Night Event: A trader passed by and left canned food.\n");
            player().inventory().add("Canned Food");
        } else if (roll < 50) {
            append("Night Event: You hear encrypted radio chatter. +10 XP\n");
            player().gainXp(10);
        } else {
            append("Night Event: Quiet night. No incidents.\n");
        }
    }
}
