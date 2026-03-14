package com.newrussia.game.model;

public final class Enemy {
    private final String name;
    private final int hp;
    private final int armor;
    private final int attackMin;
    private final int attackMax;
    private final int xpReward;
    private final String behavior;

    public Enemy(String name, int hp, int armor, int attackMin, int attackMax, int xpReward, String behavior) {
        this.name = name;
        this.hp = hp;
        this.armor = armor;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.xpReward = xpReward;
        this.behavior = behavior;
    }

    public String name() { return name; }
    public int hp() { return hp; }
    public int armor() { return armor; }
    public int attackMin() { return attackMin; }
    public int attackMax() { return attackMax; }
    public int xpReward() { return xpReward; }
    public String behavior() { return behavior; }
}
