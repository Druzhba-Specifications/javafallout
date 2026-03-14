package com.newrussia.game.core.modules;

import com.newrussia.game.core.GameController;
import com.newrussia.game.model.Npc;

public final class DialogueModule implements GameModule {
    @Override
    public String id() {
        return "dialogue";
    }

    @Override
    public void initialize(GameController controller) {
        controller.append("[Module] Dialogue system loaded.\n");
    }

    public void onDialogueStart(GameController controller, Npc npc) {
        controller.append("[Dialogue] Initiating channel with " + npc.name() + ".\n");
    }

    public void onDialogueResult(GameController controller, Npc npc, boolean success) {
        controller.append("[Dialogue] " + npc.name() + " negotiation result: " + (success ? "success" : "failed") + "\n");
    }
}
