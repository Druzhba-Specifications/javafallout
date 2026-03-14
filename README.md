# Fallout: New Russia (Plain Java Prototype)

A playable Java desktop prototype inspired by classic Fallout-style structure.

## What's included

- Turn-based combat loop using S.P.E.C.I.A.L-derived AP and damage calculations.
- Multiple scenes/locations with travel links, hidden places, enemies, and cutscene text.
- NPC dialogue interactions with speech checks and animated talking-head panel.
- Background ambient MIDI music engine and per-NPC voice cue hooks.
- Organized startup flow with standalone `src/main/Main.java` and all other game files under `src/com/newrussia/game`.

## Run (plain Java, no Maven)

```bash
javac -d out $(rg --files src | tr '\n' ' ')
java -cp out main.Main
```

If your environment blocks sound devices, the game still runs (music/voice degrade gracefully).

## Project structure

- `src/main/Main.java`: single entrypoint.
- `src/com/newrussia/game/GameController.java`: central wiring and lifecycle management.
- `src/com/newrussia/game/GameFrame.java`: gameplay UI and interactions.
- `src/com/newrussia/game/DemoContent.java`: locations, NPCs, enemies, and story setup.
- `src/com/newrussia/game/MusicEngine.java`, `VoiceEngine.java`, `CombatEngine.java`: replaceable systems.
