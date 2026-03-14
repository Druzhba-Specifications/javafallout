package com.newrussia.game;

import javax.swing.SwingUtilities;

public final class FalloutNewRussiaApp {
    private FalloutNewRussiaApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameState state = DemoContent.createInitialState();
            GameFrame frame = new GameFrame(state);
            frame.setVisible(true);
        });
    }
}
