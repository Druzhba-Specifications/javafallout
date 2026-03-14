package com.newrussia.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class GameFrame extends JFrame {
    private final GameState state;
    private final MusicEngine musicEngine = new MusicEngine();
    private final VoiceEngine voiceEngine = new VoiceEngine();
    private final CombatEngine combatEngine = new CombatEngine();

    private final JTextArea storyArea = new JTextArea();
    private final JTextArea hudArea = new JTextArea();
    private final PortraitPanel portrait = new PortraitPanel();

    public GameFrame(GameState state) {
        super("Fallout: New Russia");
        this.state = state;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1180, 780);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);
        storyArea.setFont(new Font("Serif", Font.PLAIN, 17));

        hudArea.setEditable(false);
        hudArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel center = new JPanel(new GridLayout(1, 2, 8, 8));
        center.add(new JScrollPane(storyArea));
        center.add(portrait);

        add(createMenuPanel(), BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(new JScrollPane(hudArea), BorderLayout.EAST);

        refreshScene(true);
        musicEngine.startAmbientLoop();
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton travelBtn = new JButton("Travel");
        JButton talkBtn = new JButton("Talk");
        JButton battleBtn = new JButton("Battle");
        JButton searchBtn = new JButton("Search Hidden");
        JButton cutsceneBtn = new JButton("Play Cutscene");
        JButton restBtn = new JButton("Rest");

        travelBtn.addActionListener(e -> openTravelDialog());
        talkBtn.addActionListener(e -> talkToNpc());
        battleBtn.addActionListener(e -> fightEnemy());
        searchBtn.addActionListener(e -> searchHidden());
        cutsceneBtn.addActionListener(e -> append("[CUTSCENE]\n" + state.currentLocation().cutscene() + "\n"));
        restBtn.addActionListener(e -> {
            state.player().healToFull();
            append("You rest under cracked murals and recover to full HP.\n");
            refreshHud();
        });

        panel.add(travelBtn);
        panel.add(talkBtn);
        panel.add(battleBtn);
        panel.add(searchBtn);
        panel.add(cutsceneBtn);
        panel.add(restBtn);
        return panel;
    }

    private void openTravelDialog() {
        List<String> neighbors = state.currentLocation().neighbors();
        String target = (String) JOptionPane.showInputDialog(this, "Choose destination", "Travel",
                JOptionPane.PLAIN_MESSAGE, null, neighbors.toArray(), neighbors.getFirst());
        if (target != null) {
            state.moveTo(target);
            refreshScene(true);
            append("You travel to " + state.currentLocation().title() + ".\n");
        }
    }

    private void talkToNpc() {
        List<Npc> npcs = state.currentLocation().npcs();
        if (npcs.isEmpty()) {
            append("No one is willing to talk here.\n");
            return;
        }
        Npc npc = npcs.getFirst();
        portrait.setSeed(npc.portraitSeed());
        voiceEngine.playVoice(npc.voiceTag());

        append("--- Dialogue with " + npc.name() + " (" + npc.role() + ") ---\n");
        for (DialogueNode node : npc.dialogue()) {
            append(node.speaker() + ": \"" + node.line() + "\"\n");
        }
        int speech = state.player().special().speechPower();
        if (speech >= npc.speechDifficulty()) {
            append("Speech check " + speech + " vs " + npc.speechDifficulty() + ": SUCCESS. New intel unlocked.\n");
        } else {
            append("Speech check " + speech + " vs " + npc.speechDifficulty() + ": FAILED. Come back stronger.\n");
        }
    }

    private void fightEnemy() {
        List<Enemy> enemies = new ArrayList<>(state.currentLocation().enemies());
        if (enemies.isEmpty()) {
            append("No hostiles in this zone.\n");
            return;
        }
        Enemy enemy = enemies.getFirst();
        append(combatEngine.fight(state.player(), enemy) + "\n");
        refreshHud();
    }

    private void searchHidden() {
        List<String> hidden = state.currentLocation().hiddenPlaces();
        append("You scan for hidden places...\n");
        for (String place : hidden) {
            append("* " + place + "\n");
        }
    }

    private void refreshScene(boolean resetText) {
        Location loc = state.currentLocation();
        if (resetText) {
            storyArea.setText("");
            append("=== " + loc.title() + " ===\n");
            append(loc.description() + "\n");
            append("Texture direction: " + loc.textureHint() + "\n\n");
        }
        refreshHud();
    }

    private void refreshHud() {
        Player p = state.player();
        StringBuilder hud = new StringBuilder();
        hud.append("PLAYER: ").append(p.name()).append("\n");
        hud.append("HP: ").append(p.hp()).append('/').append(p.special().maxHp()).append("\n");
        hud.append("XP: ").append(p.xp()).append("\n");
        hud.append("AP: ").append(p.special().actionPoints()).append("\n\n");
        hud.append("S.P.E.C.I.A.L\n");
        hud.append("S ").append(p.special().strength()).append("  P ").append(p.special().perception()).append('\n');
        hud.append("E ").append(p.special().endurance()).append("  C ").append(p.special().charisma()).append('\n');
        hud.append("I ").append(p.special().intelligence()).append("  A ").append(p.special().agility()).append('\n');
        hud.append("L ").append(p.special().luck()).append("\n\nPerks:\n");
        for (Perk perk : p.perks()) {
            hud.append("- ").append(perk.name()).append(": ").append(perk.description()).append("\n");
        }
        hudArea.setText(hud.toString());
    }

    private void append(String text) {
        storyArea.append(text);
        storyArea.setCaretPosition(storyArea.getDocument().getLength());
    }

    @Override
    public void dispose() {
        musicEngine.stop();
        super.dispose();
    }
}
