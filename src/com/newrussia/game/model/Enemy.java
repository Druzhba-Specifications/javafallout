package com.newrussia.game.model;

public record Enemy(String name, int hp, int armor, int attackMin, int attackMax, int xpReward, String behavior) {
}
