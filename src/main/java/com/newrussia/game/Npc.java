package com.newrussia.game;

import java.util.List;

public record Npc(
        String name,
        String role,
        String portraitSeed,
        List<DialogueNode> dialogue,
        int speechDifficulty,
        String voiceTag
) {
}
