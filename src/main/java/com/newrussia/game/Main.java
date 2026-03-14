package com.newrussia.game;

/**
 * Single entrypoint that loads all game systems and starts Fallout: New Russia.
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        GameController.launchUi();
    }
}
