package com.newrussia.game;

import javax.swing.*;

/**
 * Central application controller that wires all systems and starts/stops the game.
 */
public final class GameController {
    private final GameState state;
    private final MusicEngine musicEngine;
    private final VoiceEngine voiceEngine;
    private final CombatEngine combatEngine;

    private GameFrame frame;

    public GameController(GameState state, MusicEngine musicEngine, VoiceEngine voiceEngine, CombatEngine combatEngine) {
        this.state = state;
        this.musicEngine = musicEngine;
        this.voiceEngine = voiceEngine;
        this.combatEngine = combatEngine;
    }

    public void start() {
        frame = new GameFrame(state, musicEngine, voiceEngine, combatEngine);
        frame.setVisible(true);
        musicEngine.startAmbientLoop();
    }

    public void shutdown() {
        musicEngine.stop();
        if (frame != null) {
            frame.dispose();
        }
    }

    public static GameController createDefault() {
        return new GameController(
                DemoContent.createInitialState(),
                new MusicEngine(),
                new VoiceEngine(),
                new CombatEngine());
    }

    public static void launchUi() {
        SwingUtilities.invokeLater(() -> createDefault().start());
    }
}
