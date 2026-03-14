# Fallout: New Russia (Maven + IntelliJ)

The project is back on Maven, remains IntelliJ-friendly, and now uses Java modules (JPMS + gameplay module components).

## Structure

- `src/module-info.java`: JPMS module declaration (`com.newrussia.game`).
- `src/main/Main.java`: runnable entrypoint.
- `src/com/newrussia/game/core`: game orchestration.
- `src/com/newrussia/game/core/modules`: gameplay modules (exploration, dialogue, combat, quest).
- `src/com/newrussia/game/ui`: Swing UI and portrait animation.
- `src/com/newrussia/game/model`: stats, entities, state, and quest models.
- `src/com/newrussia/game/content`: world/location bootstrap.
- `src/com/newrussia/game/systems`: combat, music, voice systems.

## Run with Maven

```bash
mvn -q compile
mvn -q exec:java
```

## Run in IntelliJ

1. Import as Maven project.
2. Use run target: `main.Main`.
3. Or run Maven goal: `exec:java`.

## Added gameplay systems

- Turn-based S.P.E.C.I.A.L-based combat.
- Expanded world graph with multiple regions and hidden places.
- Dialogue with speech checks, rewards, and voice cues.
- Quest system with active/completed states and XP payouts.
- Animated talking-head panel and ambient MIDI tracks.
