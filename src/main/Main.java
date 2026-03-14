package main;

import com.newrussia.game.core.GameController;

/**
 * Fallout: New Russia
 *
 * Plain Java entrypoint designed for IntelliJ IDEA execution.
import com.newrussia.game.GameController;

/**
 * Standalone entrypoint for Fallout: New Russia (plain Java).
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.boot();
        GameController.launchUi();
    }
}
