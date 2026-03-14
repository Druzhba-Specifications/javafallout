package com.newrussia.game.core.modules;

import com.newrussia.game.core.GameController;
import com.newrussia.game.model.Quest;

public final class QuestModule implements GameModule {
    @Override
    public String id() {
        return "quest";
    }

    @Override
    public void initialize(GameController controller) {
        controller.append("[Module] Quest system loaded.\n");
    }

    public void onQuestActivated(GameController controller, Quest quest) {
        controller.append("[Quest] Activated: " + quest.title() + "\n");
    }

    public void onQuestCompleted(GameController controller, Quest quest) {
        controller.append("[Quest] Completed: " + quest.title() + "\n");
    }
}
