# Fallout: New Russia (Maven + IntelliJ)

This project is structured so IntelliJ IDEA can automatically detect and run the main class.

## IntelliJ-ready structure

- `src/main/java/main/Main.java`: runnable entrypoint (auto-detected by IntelliJ).
- `src/main/java/module-info.java`: JPMS descriptor.
- `src/main/java/com/newrussia/game/core`: game orchestration.
- `src/main/java/com/newrussia/game/core/modules`: gameplay modules (exploration, dialogue, combat, quest).
- `src/main/java/com/newrussia/game/ui`: Swing UI and portrait animation.
- `src/main/java/com/newrussia/game/model`: stats, entities, state, and quest models.
- `src/main/java/com/newrussia/game/content`: world/location bootstrap.
- `src/main/java/com/newrussia/game/systems`: combat, music, and voice systems.

## Run with Maven

```bash
mvn -q compile
mvn -q exec:java
```

## Run in IntelliJ

1. Open/import project as Maven.
2. IntelliJ will detect `main.Main` in `src/main/java` automatically.
3. Click Run on `Main.main(...)` or create an Application run config for `main.Main`.


## Java version requirement

This codebase targets **Java 25** (Project SDK + language level + Maven importer JDK).

If IntelliJ shows errors like `records are not supported in -source 8`, set:

- **Project SDK**: 25
- **Project language level**: 25
- **Maven importer JDK**: 25

Then reimport the Maven project.

## Gameplay

- Turn-based S.P.E.C.I.A.L combat.
- Multi-zone world exploration and hidden places.
- Dialogue + speech checks.
- Quest progression with active/completed states.
- Animated talking head and ambient MIDI placeholders.
