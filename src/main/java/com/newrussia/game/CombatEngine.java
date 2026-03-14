package com.newrussia.game;

import java.util.Random;

public final class CombatEngine {
    private final Random random = new Random();

    public String fight(Player player, Enemy enemy) {
        int enemyHp = enemy.hp();
        int playerHpSnapshot = player.hp();
        int ap = player.special().actionPoints();
        StringBuilder log = new StringBuilder("Turn-based encounter vs ").append(enemy.name()).append("\n");

        while (enemyHp > 0 && playerHpSnapshot > 0) {
            int attacks = Math.max(1, ap / 3);
            for (int i = 0; i < attacks && enemyHp > 0; i++) {
                int dmg = Math.max(1, player.attackPower() + random.nextInt(7) - enemy.armor());
                enemyHp -= dmg;
                log.append("You fire for ").append(dmg).append(" damage. Enemy HP: ").append(Math.max(0, enemyHp)).append("\n");
            }
            if (enemyHp <= 0) {
                break;
            }
            int enemyDmg = Math.max(1, enemy.attack() + random.nextInt(5) - player.special().endurance() / 2);
            playerHpSnapshot -= enemyDmg;
            log.append(enemy.name()).append(" hits you for ").append(enemyDmg).append(". Your HP: ")
                    .append(Math.max(0, playerHpSnapshot)).append("\n");
        }

        player.damage(player.hp() - Math.max(0, playerHpSnapshot));
        if (playerHpSnapshot > 0) {
            player.gainXp(enemy.xpReward());
            log.append("Victory. +").append(enemy.xpReward()).append(" XP");
        } else {
            log.append("You are downed. Irina drags you to safety and patches you up.");
            player.healToFull();
        }
        return log.toString();
    }
}
