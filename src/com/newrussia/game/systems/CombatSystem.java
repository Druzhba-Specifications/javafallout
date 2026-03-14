package com.newrussia.game.systems;

import com.newrussia.game.model.Enemy;
import com.newrussia.game.model.Player;

import java.util.Random;

public final class CombatSystem {
    private final Random random = new Random();

    public String runFight(Player player, Enemy enemy) {
        int enemyHp = enemy.hp();
        int playerHp = player.hp();
        int round = 1;
        StringBuilder log = new StringBuilder();
        log.append("Encounter started: ").append(enemy.name()).append(" [").append(enemy.behavior()).append("]\n");

        while (enemyHp > 0 && playerHp > 0 && round <= 30) {
            log.append("-- Round ").append(round).append(" --\n");
            int playerActions = Math.max(1, player.special().actionPoints() / 3);

            for (int i = 0; i < playerActions && enemyHp > 0; i++) {
                boolean crit = random.nextInt(100) < player.special().critChance();
                int base = roll(player.attackMin(), player.attackMax());
                int dealt = Math.max(1, base - enemy.armor());
                if (crit) {
                    dealt += Math.max(2, dealt / 2);
                }
                enemyHp = Math.max(0, enemyHp - dealt);
                log.append("You attack for ").append(dealt);
                if (crit) {
                    log.append(" (CRIT)");
                }
                log.append(". Enemy HP: ").append(enemyHp).append("\n");
            }

            if (enemyHp <= 0) {
                break;
            }

            int enemyDamage = Math.max(1, roll(enemy.attackMin(), enemy.attackMax()) - (player.special().endurance() / 2));
            playerHp = Math.max(0, playerHp - enemyDamage);
            log.append(enemy.name()).append(" hits you for ").append(enemyDamage).append(". Your HP: ").append(playerHp).append("\n");
            round++;
        }

        int diff = player.hp() - playerHp;
        if (diff > 0) {
            player.takeDamage(diff);
        }

        if (playerHp > 0 && enemyHp <= 0) {
            player.gainXp(enemy.xpReward());
            log.append("Victory! +").append(enemy.xpReward()).append(" XP\n");
        } else if (playerHp <= 0) {
            player.fullRest();
            log.append("You were knocked out. Allies recovered you. HP fully restored.\n");
        } else {
            log.append("The fight ended without a clear winner.\n");
        }

        return log.toString();
    }

    private int roll(int min, int max) {
        if (max <= min) {
            return min;
        }
        return min + random.nextInt(max - min + 1);
    }
}
