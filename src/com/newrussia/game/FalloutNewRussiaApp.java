package com.newrussia.game;

/**
 * Backward-compatible launcher that delegates to the standalone main.Main entrypoint.
 */
public final class FalloutNewRussiaApp {
    private FalloutNewRussiaApp() {
    }

    public static void main(String[] args) {
        main.Main.main(args);
    }
}
