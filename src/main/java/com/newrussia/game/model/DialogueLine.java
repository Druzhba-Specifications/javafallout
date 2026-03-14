package com.newrussia.game.model;

public final class DialogueLine {
    private final String speaker;
    private final String text;

    public DialogueLine(String speaker, String text) {
        this.speaker = speaker;
        this.text = text;
    }

    public String speaker() { return speaker; }
    public String text() { return text; }
}
