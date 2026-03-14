package main;

import com.newrussia.game.GameController;

/**
 * Standalone entrypoint for Fallout: New Russia (plain Java).
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        GameController.launchUi();
    }
}
