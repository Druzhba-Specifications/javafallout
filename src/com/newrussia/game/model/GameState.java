package com.newrussia.game.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class GameState {
    private final Player player;
    private final Map<String, Location> world;
    private final Set<String> discoveredHidden;
    private final Map<String, Quest> quests;

    private String currentLocationId;

    public GameState(Player player, Map<String, Location> world, String currentLocationId, Collection<Quest> quests) {

    private String currentLocationId;

    public GameState(Player player, Map<String, Location> world, String currentLocationId) {
        this.player = player;
        this.world = new LinkedHashMap<>(world);
        this.currentLocationId = currentLocationId;
        this.discoveredHidden = new HashSet<>();
        this.quests = new LinkedHashMap<>();
        for (Quest quest : quests) {
            this.quests.put(quest.id(), quest);
        }
    }

    public Player player() { return player; }
    public Map<String, Location> world() { return world; }
    public String currentLocationId() { return currentLocationId; }
    public Location currentLocation() { return world.get(currentLocationId); }
    public Set<String> discoveredHidden() { return discoveredHidden; }
    public Map<String, Quest> quests() { return quests; }

    public boolean travelTo(String destinationId) {
        if (!world.containsKey(destinationId)) {
            return false;
        }
        Location current = currentLocation();
        if (!current.neighbors().contains(destinationId)) {
            return false;
        }
        currentLocationId = destinationId;
        return true;
    }

    public void markHiddenDiscovered(String hidden) {
        discoveredHidden.add(hidden);
    }
}
