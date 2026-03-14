package com.newrussia.game;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GameState {
    private final Player player;
    private final Map<String, Location> locations;
    private String currentLocationId;

    public GameState(Player player, Map<String, Location> locations, String currentLocationId) {
        this.player = player;
        this.locations = new LinkedHashMap<>(locations);
        this.currentLocationId = currentLocationId;
    }

    public Player player() { return player; }
    public Map<String, Location> locations() { return locations; }
    public Location currentLocation() { return locations.get(currentLocationId); }
    public String currentLocationId() { return currentLocationId; }

    public void moveTo(String id) {
        if (locations.containsKey(id)) {
            currentLocationId = id;
        }
    }
}
