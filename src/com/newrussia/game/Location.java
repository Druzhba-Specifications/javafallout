package com.newrussia.game;

import java.util.List;

public record Location(
        String id,
        String title,
        String description,
        String textureHint,
        List<String> neighbors,
        List<Npc> npcs,
        List<Enemy> enemies,
        List<String> hiddenPlaces,
        String cutscene
) {
}
