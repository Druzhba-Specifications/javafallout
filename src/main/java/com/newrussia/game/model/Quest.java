package com.newrussia.game.model;

public final class Quest {
    public enum Status { AVAILABLE, ACTIVE, COMPLETED }

    private final String id;
    private final String title;
    private final String description;
    private final String objectiveHint;
    private final int xpReward;
    private Status status;

    public Quest(String id, String title, String description, String objectiveHint, int xpReward) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.objectiveHint = objectiveHint;
        this.xpReward = xpReward;
        this.status = Status.AVAILABLE;
    }

    public String id() { return id; }
    public String title() { return title; }
    public String description() { return description; }
    public String objectiveHint() { return objectiveHint; }
    public int xpReward() { return xpReward; }
    public Status status() { return status; }

    public void activate() {
        if (status == Status.AVAILABLE) {
            status = Status.ACTIVE;
        }
    }

    public boolean complete() {
        if (status == Status.ACTIVE) {
            status = Status.COMPLETED;
            return true;
        }
        return false;
    }
}
