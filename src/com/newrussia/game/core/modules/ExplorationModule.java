package com.newrussia.game.core.modules;

import com.newrussia.game.core.GameController;

public final class ExplorationModule implements GameModule {
    @Override
    public String id() {
        return "exploration";
    }

    @Override
    public void initialize(GameController controller) {
        controller.append("[Module] Exploration system loaded.\n");
    }

    public void onTravel(GameController controller, String destinationId) {
        controller.append("[Exploration] Route locked: " + destinationId + "\n");
    }

    public void onScan(GameController controller, String locationId) {
        controller.append("[Exploration] Scan profile updated for zone: " + locationId + "\n");
    }
}
