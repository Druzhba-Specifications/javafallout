package com.newrussia.game;

/**
 * Backward-compatible launcher. Use Main as the canonical entrypoint.
 */
public final class FalloutNewRussiaApp {
    private FalloutNewRussiaApp() {
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
