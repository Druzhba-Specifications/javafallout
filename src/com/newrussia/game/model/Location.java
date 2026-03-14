package com.newrussia.game.model;

import java.util.List;

public record Location(String id,
                       String title,
                       String region,
                       String description,
                       String textureDirection,
                       String cutscene,
                       List<String> neighbors,
                       List<Npc> npcs,
                       List<Enemy> enemies,
                       List<String> hiddenPlaces,
                       String ambientTrackName) {
}
