package com.newrussia.game.model;

import java.util.List;

public record Npc(String name,
                  String role,
                  String portraitSeed,
                  String voiceTag,
                  int speechDifficulty,
                  List<DialogueLine> lines,
                  String successReward,
                  String failReaction) {
}
