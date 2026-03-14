package com.newrussia.game.ui;

import com.newrussia.game.core.GameController;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Main UI window. Handles rendering and user input dispatch.
 */
public final class GameFrame extends JFrame {
    private final GameController controller;

    private final JTextArea storyArea;
    private final JTextArea hudArea;
    private final PortraitPanel portraitPanel;

    private final JLabel locationLabel;
    private final JLabel statusLabel;

    private final JButton travelButton;
    private final JButton talkButton;
    private final JButton battleButton;
    private final JButton scanButton;
    private final JButton cutsceneButton;
    private final JButton restButton;
    private final JButton questButton;

    private final JButton clearLogButton;
    private final JButton helpButton;

    public GameFrame(GameController controller) {
        super("Fallout: New Russia");
        this.controller = controller;

        storyArea = buildStoryArea();
        hudArea = buildHudArea();
        portraitPanel = new PortraitPanel();

        locationLabel = new JLabel("Location", SwingConstants.LEFT);
        statusLabel = new JLabel("Status: Ready", SwingConstants.LEFT);

        travelButton = new JButton("Travel");
        talkButton = new JButton("Talk");
        battleButton = new JButton("Battle");
        scanButton = new JButton("Search Hidden");
        cutsceneButton = new JButton("Play Cutscene");
        restButton = new JButton("Rest");
        questButton = new JButton("Quest Log");

        clearLogButton = new JButton("Clear Log");
        helpButton = new JButton("Help");

        configureWindow();
        bindActions();
    }

    public void open() {
        setVisible(true);
    }

    public void setLocationTitle(String location) {
        locationLabel.setText("Location: " + location);
    }

    public void setPortraitSeed(String seed) {
        portraitPanel.setSeed(seed);
        statusLabel.setText("Status: Interacting with " + seed);
    }

    public void setHudText(String text) {
        hudArea.setText(text);
    }

    public void appendToStory(String text) {
        storyArea.append(text);
        storyArea.setCaretPosition(storyArea.getDocument().getLength());
    }

    private void configureWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1320, 840));
        setSize(1440, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel top = buildTopPanel();
        JPanel center = buildCenterPanel();
        JPanel right = buildRightPanel();
        JPanel bottom = buildBottomPanel();

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 10));

        locationLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(50, 90, 50));

        JPanel leftBlock = new JPanel();
        leftBlock.setLayout(new BoxLayout(leftBlock, BoxLayout.Y_AXIS));
        leftBlock.add(locationLabel);
        leftBlock.add(Box.createRigidArea(new Dimension(0, 4)));
        leftBlock.add(statusLabel);

        JPanel quickActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        quickActions.add(helpButton);
        quickActions.add(clearLogButton);

        panel.add(leftBlock, BorderLayout.WEST);
        panel.add(quickActions, BorderLayout.EAST);
        panel.add(new JSeparator(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JScrollPane storyScroll = new JScrollPane(storyArea);
        storyScroll.setBorder(BorderFactory.createTitledBorder("Mission Log"));

        JPanel portraitContainer = new JPanel(new BorderLayout());
        portraitContainer.setBorder(BorderFactory.createTitledBorder("Dialogue Camera"));
        portraitContainer.add(portraitPanel, BorderLayout.CENTER);

        panel.add(storyScroll);
        panel.add(portraitContainer);
        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(380, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JScrollPane hudScroll = new JScrollPane(hudArea);
        hudScroll.setBorder(BorderFactory.createTitledBorder("Pip-Boy Status"));
        panel.add(hudScroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(travelButton);
        actions.add(talkButton);
        actions.add(battleButton);
        actions.add(scanButton);
        actions.add(cutsceneButton);
        actions.add(restButton);
        actions.add(questButton);

        JLabel hint = new JLabel("Tip: talk to NPCs for speech checks, then search hidden places for rewards.");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 12));
        hint.setForeground(new Color(70, 70, 70));

        panel.add(actions, BorderLayout.NORTH);
        panel.add(hint, BorderLayout.SOUTH);
        return panel;
    }

    private JTextArea buildStoryArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Serif", Font.PLAIN, 17));
        area.setBackground(new Color(250, 248, 240));
        return area;
    }

    private JTextArea buildHudArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setBackground(new Color(238, 255, 238));
        return area;
    }

    private void bindActions() {
        travelButton.addActionListener(e -> handleTravel());
        talkButton.addActionListener(e -> {
            controller.talkToFirstNpc();
            statusLabel.setText("Status: Dialogue attempt complete");
        });
        battleButton.addActionListener(e -> {
            controller.fightFirstEnemy();
            statusLabel.setText("Status: Combat report added");
        });
        scanButton.addActionListener(e -> {
            controller.scanForHiddenPlaces();
            statusLabel.setText("Status: Hidden scan complete");
        });
        cutsceneButton.addActionListener(e -> {
            controller.playCutscene();
            statusLabel.setText("Status: Cutscene playback logged");
        });
        restButton.addActionListener(e -> {
            controller.rest();
            statusLabel.setText("Status: Rest cycle complete");
        });
        questButton.addActionListener(e -> {
            controller.showQuestLog();
            statusLabel.setText("Status: Quest log opened in mission feed");
        });

        clearLogButton.addActionListener(e -> {
            storyArea.setText("");
            storyArea.append(controller.buildSceneHeader());
            statusLabel.setText("Status: Mission log reset");
        });

        helpButton.addActionListener(e -> showHelpDialog());
    }

    private void handleTravel() {
        java.util.List<String> destinations = controller.availableDestinations();
        if (destinations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No valid travel routes from this location.");
            return;
        }

        JComboBox<String> combo = new JComboBox<>(destinations.toArray(String[]::new));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Choose destination:"), BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Travel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String selected = (String) combo.getSelectedItem();
            controller.travelTo(selected);
            statusLabel.setText("Status: Travelled to " + selected);
        }
    }

    private void showHelpDialog() {
        String text = "Fallout: New Russia - Controls\n\n"
                + "Travel: Move between connected locations.\n"
                + "Talk: Speak with the primary NPC in current area.\n"
                + "Battle: Fight the primary hostile in current area.\n"
                + "Search Hidden: Attempt to discover hidden places and loot.\n"
                + "Play Cutscene: Log location-specific cinematic text.\n"
                + "Rest: Fully heal and trigger possible nightly events.\n"
                + "Quest Log: Print all quest states and objectives to the mission log.\n\n"
                + "HUD panel on the right shows S.P.E.C.I.A.L, perks, inventory, progression, and quest counters.\n";
                + "Rest: Fully heal and trigger possible nightly events.\n\n"
                + "HUD panel on the right shows S.P.E.C.I.A.L, perks, inventory, and progression.\n";

        JOptionPane.showMessageDialog(this, text, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
}
