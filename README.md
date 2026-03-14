# Fallout: New Russia (Plain Java, IntelliJ-friendly)

This project is a full restart of the prototype with a clean structure:

- `src/main/Main.java` is the standalone entrypoint.
- All other classes live under `src/com/newrussia/game/...` packages.
- No Maven/Gradle required.

## Run in IntelliJ

1. Open project folder.
2. Mark `src` as sources root if needed.
3. Run `main.Main`.

## Run in terminal

```bash
javac -d out $(rg --files src | tr '\n' ' ')
java -cp out main.Main
```

## Features

- Turn-based combat with S.P.E.C.I.A.L-influenced stats.
- Multi-location exploration with travel graph.
- NPC dialogue with speech checks.
- Hidden location scanning and rewards.
- Animated talking-head portrait panel.
- Procedural MIDI ambient music and voice cues.
