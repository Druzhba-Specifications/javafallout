package com.newrussia.game;

public record SpecialStats(int strength, int perception, int endurance, int charisma, int intelligence, int agility,
                           int luck) {
    public int maxHp() {
        return 55 + endurance * 6;
    }

    public int actionPoints() {
        return 5 + agility / 2;
    }

    public int speechPower() {
        return charisma * 8 + intelligence * 3 + luck;
    }
}
