package com.newrussia.game.model;

public final class Perk {
    private final String name;
    private final String description;

    public Perk(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String name() { return name; }
    public String description() { return description; }
}
