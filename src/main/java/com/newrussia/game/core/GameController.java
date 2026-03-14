package com.newrussia.game.core;

import com.newrussia.game.content.WorldBuilder;
import com.newrussia.game.model.Enemy;
import com.newrussia.game.model.GameState;
import com.newrussia.game.model.Location;
import com.newrussia.game.model.Npc;
import com.newrussia.game.model.Player;
import com.newrussia.game.model.Quest;
import com.newrussia.game.core.modules.CombatModule;
import com.newrussia.game.core.modules.DialogueModule;
import com.newrussia.game.core.modules.ExplorationModule;
import com.newrussia.game.core.modules.GameModule;
import com.newrussia.game.core.modules.QuestModule;
import com.newrussia.game.systems.CombatSystem;
import com.newrussia.game.systems.MusicEngine;
import com.newrussia.game.systems.VoiceEngine;
import com.newrussia.game.ui.GameFrame;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GameController {
    private final WorldBuilder worldBuilder;
    private final MusicEngine musicEngine;
    private final VoiceEngine voiceEngine;
    private final CombatSystem combatSystem;
    private final Random random;

    private final ExplorationModule explorationModule;
    private final DialogueModule dialogueModule;
    private final CombatModule combatModule;
    private final QuestModule questModule;
    private final List<GameModule> modules;

    private GameState state;
    private GameFrame frame;

    public GameController() {
        this.worldBuilder = new WorldBuilder();
        this.musicEngine = new MusicEngine();
        this.voiceEngine = new VoiceEngine();
        this.combatSystem = new CombatSystem();
        this.random = new Random();
        this.explorationModule = new ExplorationModule();
        this.dialogueModule = new DialogueModule();
        this.combatModule = new CombatModule();
        this.questModule = new QuestModule();
        this.modules = List.of(explorationModule, dialogueModule, combatModule, questModule);
    }

    public void boot() {
        state = worldBuilder.build();
        SwingUtilities.invokeLater(() -> {
            frame = new GameFrame(this);
            frame.open();
            startLocationMusic();
            initializeModules();
            autoActivateIntroQuest();
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

    public Location location() {
        return state.currentLocation();
    }

    public Player player() {
        return state.player();
    }

    public List<String> availableDestinations() {
        return new ArrayList<>(location().neighbors());
    }

    public void travelTo(String destinationId) {
        if (destinationId == null || destinationId.isBlank()) {
            append("Travel aborted: no destination selected.\n");
            return;
        }
        explorationModule.onTravel(this, destinationId);
        if (!state.travelTo(destinationId)) {
            append("Travel denied. Route not available from this location.\n");
            return;
        }

        append("You travel to " + location().title() + " in " + location().region() + ".\n");
        append(location().description() + "\n");
        append("Texture direction: " + location().textureDirection() + "\n");
        startLocationMusic();

        if ("reactor_catacombs".equals(location().id())) {
            activateQuest("core");
            append("Quest update: Silent Reactor Oath is now ACTIVE.\n");
        }

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
        dialogueModule.onDialogueStart(this, npc);
        voiceEngine.playNpcVoiceCue(npc.voiceTag());

        append("\n--- Dialogue: " + npc.name() + " [" + npc.role() + "] ---\n");
        npc.lines().forEach(line -> append(line.speaker() + ": \"" + line.text() + "\"\n"));

        int speech = player().special().speechPower();
        boolean success = speech >= npc.speechDifficulty();
        dialogueModule.onDialogueResult(this, npc, success);
        if (success) {
            append("Speech check: " + speech + " vs " + npc.speechDifficulty() + " -> SUCCESS\n");
            append("Reward unlocked: " + npc.successReward() + "\n");
            maybeAddRewardToInventory(npc.successReward());
            if ("kirov".equals(npc.portraitSeed())) {
                activateQuest("checkpoint");
                completeQuest("checkpoint", "Kirov is convinced, and you secure authentic checkpoint routes.");
            }
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
        combatModule.onCombatStart(this, enemy);
        append("\n[COMBAT] You engage " + enemy.name() + "...\n");
        String result = combatSystem.runFight(player(), enemy);
        append(result);
        maybePostCombatLoot(enemy);
        combatModule.onCombatEnd(this, enemy);

        if ("reactor_catacombs".equals(location().id())) {
            completeQuest("core", "You survived combat in the reactor corridors.");
        }

        refreshUi();
    }

    public void scanForHiddenPlaces() {
        if (location().hiddenPlaces().isEmpty()) {
            append("No hidden signatures detected.\n");
            return;
        }

        explorationModule.onScan(this, location().id());
        append("\n[SCAN] You inspect structural seams and radio reflections...\n");
        for (String hidden : location().hiddenPlaces()) {
            boolean discovered = tryDiscover(hidden);
            if (discovered) {
                append("Discovered: " + hidden + "\n");
                state.markHiddenDiscovered(hidden);
                maybeHiddenReward(hidden);

                if (location().id().equals("novaya_metro")) {
                    completeQuest("relay", "Relay evidence secured from Novaya Metro hidden infrastructure.");
                }
            } else {
                append("Possible trace found but not enough evidence: " + hidden + "\n");
            }
        }

        refreshUi();
    }

    public void showQuestLog() {
        append("\n=== Quest Log ===\n");
        for (Quest q : state.quests().values()) {
            append("- [" + q.status() + "] " + q.title() + "\n");
            append("  " + q.description() + "\n");
            append("  Objective: " + q.objectiveHint() + "\n");
        }
        append("=================\n");
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

        b.append("\nLocation: ").append(location().title()).append(" (").append(location().region()).append(")\n");
        b.append("Track: ").append(musicEngine.currentTrack()).append("\n");
        b.append("Known hidden places: ").append(state.discoveredHidden().size()).append("\n");

        long active = state.quests().values().stream().filter(q -> q.status() == Quest.Status.ACTIVE).count();
        long done = state.quests().values().stream().filter(q -> q.status() == Quest.Status.COMPLETED).count();
        b.append("Active quests: ").append(active).append(" | Completed quests: ").append(done).append("\n");
        b.append("Modules loaded: ").append(modules.size()).append("\n");

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


    private void initializeModules() {
        for (GameModule module : modules) {
            module.initialize(this);
        }
    }

    private void startLocationMusic() {
        musicEngine.start(location().ambientTrackName());
    }

    private void logIntro() {
        append("Welcome to Fallout: New Russia\n");
        append("A new run begins in the ruins of the capital.\n");
        append(buildSceneHeader());
        append("Tip: open Quest Log from the bottom action bar.\n");
    }

    private void autoActivateIntroQuest() {
        activateQuest("relay");
    }

    private boolean tryDiscover(String hidden) {
        if (state.discoveredHidden().contains(hidden)) {
            return true;
        }
        int roll = random.nextInt(100);
        int threshold = 42 + player().special().perception() * 4 + player().special().luck();
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

    private void activateQuest(String questId) {
        Quest q = state.quests().get(questId);
        if (q != null) {
            q.activate();
            questModule.onQuestActivated(this, q);
        }
    }

    private void completeQuest(String questId, String reason) {
        Quest q = state.quests().get(questId);
        if (q == null) {
            return;
        }
        q.activate();
        if (q.complete()) {
            player().gainXp(q.xpReward());
            append("Quest complete: " + q.title() + " (" + reason + ") +" + q.xpReward() + " XP\n");
            questModule.onQuestCompleted(this, q);
        }
    }
}
