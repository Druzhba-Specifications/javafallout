package com.newrussia.game.core.modules;

import com.newrussia.game.core.GameController;

/**
 * Base contract for pluggable gameplay modules.
 */
public interface GameModule {
    String id();
    void initialize(GameController controller);
}
