package com.newrussia.game.model;

import java.util.List;

public final class Npc {
    private final String name;
    private final String role;
    private final String portraitSeed;
    private final String voiceTag;
    private final int speechDifficulty;
    private final List<DialogueLine> lines;
    private final String successReward;
    private final String failReaction;

    public Npc(String name, String role, String portraitSeed, String voiceTag, int speechDifficulty,
               List<DialogueLine> lines, String successReward, String failReaction) {
        this.name = name;
        this.role = role;
        this.portraitSeed = portraitSeed;
        this.voiceTag = voiceTag;
        this.speechDifficulty = speechDifficulty;
        this.lines = List.copyOf(lines);
        this.successReward = successReward;
        this.failReaction = failReaction;
    }

    public String name() { return name; }
    public String role() { return role; }
    public String portraitSeed() { return portraitSeed; }
    public String voiceTag() { return voiceTag; }
    public int speechDifficulty() { return speechDifficulty; }
    public List<DialogueLine> lines() { return lines; }
    public String successReward() { return successReward; }
    public String failReaction() { return failReaction; }
}
