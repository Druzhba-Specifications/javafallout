package com.newrussia.game.model;

import java.util.ArrayList;
import java.util.List;

public final class Player {
    private final String name;
    private final SpecialStats special;
    private final List<Perk> perks;
    private final List<String> inventory;

    private int hp;
    private int xp;
    private int level;

    public Player(String name, SpecialStats special, List<Perk> perks, List<String> starterInventory) {
        this.name = name;
        this.special = special;
        this.perks = new ArrayList<>(perks);
        this.inventory = new ArrayList<>(starterInventory);
        this.hp = special.maxHp();
        this.level = 1;
    }

    public String name() { return name; }
    public SpecialStats special() { return special; }
    public List<Perk> perks() { return perks; }
    public List<String> inventory() { return inventory; }
    public int hp() { return hp; }
    public int xp() { return xp; }
    public int level() { return level; }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }

    public void heal(int amount) {
        hp = Math.min(special.maxHp(), hp + Math.max(0, amount));
    }

    public void fullRest() {
        hp = special.maxHp();
    }

    public int attackMin() {
        return 5 + special.strength();
    }

    public int attackMax() {
        return 10 + special.strength() * 2 + special.luck();
    }

    public void gainXp(int amount) {
        xp += Math.max(0, amount);
        while (xp >= xpNeededForNextLevel()) {
            xp -= xpNeededForNextLevel();
            level++;
            hp = special.maxHp();
        }
    }

    public int xpNeededForNextLevel() {
        return 100 + (level - 1) * 60;
    }
}
