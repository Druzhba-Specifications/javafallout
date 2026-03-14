package com.newrussia.game.model;

public record SpecialStats(int strength, int perception, int endurance, int charisma, int intelligence, int agility, int luck) {
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
