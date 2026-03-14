package com.newrussia.game;

import java.awt.Toolkit;

public final class VoiceEngine {
    public void playVoice(String tag) {
        // Placeholder voice cue system: short beep patterns mapped to voice profiles.
        int count = switch (tag) {
            case "female_gravel" -> 2;
            case "male_smooth" -> 3;
            default -> 1;
        };
        for (int i = 0; i < count; i++) {
            Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep(90L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
