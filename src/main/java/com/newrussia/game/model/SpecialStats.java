package com.newrussia.game.model;

public final class SpecialStats {
    private final int strength;
    private final int perception;
    private final int endurance;
    private final int charisma;
    private final int intelligence;
    private final int agility;
    private final int luck;

    public SpecialStats(int strength, int perception, int endurance, int charisma, int intelligence, int agility, int luck) {
        this.strength = strength;
        this.perception = perception;
        this.endurance = endurance;
        this.charisma = charisma;
        this.intelligence = intelligence;
        this.agility = agility;
        this.luck = luck;
    }

    public int strength() { return strength; }
    public int perception() { return perception; }
    public int endurance() { return endurance; }
    public int charisma() { return charisma; }
    public int intelligence() { return intelligence; }
    public int agility() { return agility; }
    public int luck() { return luck; }

    public int maxHp() {
        return 60 + endurance * 7;
    }

    public int actionPoints() {
        return 5 + agility / 2;
    }

    public int initiative() {
        return perception * 2 + agility;
    }

    public int speechPower() {
        return charisma * 8 + intelligence * 2 + luck;
    }

    public int critChance() {
        return 2 + luck * 2;
    }
}
