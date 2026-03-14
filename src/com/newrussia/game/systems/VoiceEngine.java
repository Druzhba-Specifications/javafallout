package com.newrussia.game.systems;

import java.awt.Toolkit;

public final class VoiceEngine {
    public void playNpcVoiceCue(String voiceTag) {
        int beeps = switch (voiceTag) {
            case "female_gravel" -> 2;
            case "male_smooth" -> 3;
            case "military_harsh" -> 4;
            default -> 1;
        };

        for (int i = 0; i < beeps; i++) {
            Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep(80L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
