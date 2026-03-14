package com.newrussia.game.model;

import java.util.List;

public final class Location {
    private final String id;
    private final String title;
    private final String region;
    private final String description;
    private final String textureDirection;
    private final String cutscene;
    private final List<String> neighbors;
    private final List<Npc> npcs;
    private final List<Enemy> enemies;
    private final List<String> hiddenPlaces;
    private final String ambientTrackName;

    public Location(String id, String title, String region, String description, String textureDirection, String cutscene,
                    List<String> neighbors, List<Npc> npcs, List<Enemy> enemies, List<String> hiddenPlaces,
                    String ambientTrackName) {
        this.id = id;
        this.title = title;
        this.region = region;
        this.description = description;
        this.textureDirection = textureDirection;
        this.cutscene = cutscene;
        this.neighbors = List.copyOf(neighbors);
        this.npcs = List.copyOf(npcs);
        this.enemies = List.copyOf(enemies);
        this.hiddenPlaces = List.copyOf(hiddenPlaces);
        this.ambientTrackName = ambientTrackName;
    }

    public String id() { return id; }
    public String title() { return title; }
    public String region() { return region; }
    public String description() { return description; }
    public String textureDirection() { return textureDirection; }
    public String cutscene() { return cutscene; }
    public List<String> neighbors() { return neighbors; }
    public List<Npc> npcs() { return npcs; }
    public List<Enemy> enemies() { return enemies; }
    public List<String> hiddenPlaces() { return hiddenPlaces; }
    public String ambientTrackName() { return ambientTrackName; }
}
