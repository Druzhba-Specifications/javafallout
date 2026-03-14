package com.newrussia.game.core.modules;

import com.newrussia.game.core.GameController;
import com.newrussia.game.model.Enemy;

public final class CombatModule implements GameModule {
    @Override
    public String id() {
        return "combat";
    }

    @Override
    public void initialize(GameController controller) {
        controller.append("[Module] Combat system loaded.\n");
    }

    public void onCombatStart(GameController controller, Enemy enemy) {
        controller.append("[Combat] Tactical profile engaged for target: " + enemy.name() + "\n");
    }

    public void onCombatEnd(GameController controller, Enemy enemy) {
        controller.append("[Combat] Engagement ended against " + enemy.name() + "\n");
    }
}
