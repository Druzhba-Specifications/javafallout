module com.newrussia.game {
    requires java.desktop;
    requires java.logging;

    exports main;
    exports com.newrussia.game.core;
    exports com.newrussia.game.core.modules;
    exports com.newrussia.game.content;
    exports com.newrussia.game.model;
    exports com.newrussia.game.systems;
    exports com.newrussia.game.ui;
}
