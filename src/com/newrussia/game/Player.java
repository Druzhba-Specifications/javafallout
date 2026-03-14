package com.newrussia.game;

import java.util.ArrayList;
import java.util.List;

public final class Player {
    private final String name;
    private final SpecialStats special;
    private final List<Perk> perks;
    private int hp;
    private int xp;

    public Player(String name, SpecialStats special, List<Perk> perks) {
        this.name = name;
        this.special = special;
        this.perks = new ArrayList<>(perks);
        this.hp = special.maxHp();
    }

    public String name() { return name; }
    public SpecialStats special() { return special; }
    public List<Perk> perks() { return perks; }
    public int hp() { return hp; }
    public int xp() { return xp; }

    public void healToFull() {
        this.hp = special.maxHp();
    }

    public void gainXp(int amount) {
        this.xp += amount;
    }

    public void damage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public int attackPower() {
        return 4 + special.strength() + special.luck() / 2;
    }
}
