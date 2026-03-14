# Fallout: New Russia (Java Prototype)

A playable Java desktop prototype inspired by classic Fallout-style structure.

## What's included

- Turn-based combat loop using S.P.E.C.I.A.L-derived AP and damage calculations.
- Multiple scenes/locations with travel links, hidden places, enemies, and cutscene text.
- NPC dialogue interactions with speech checks and animated talking-head panel.
- Background ambient MIDI music engine and per-NPC voice cue hooks.
- Central app bootstrap/controller flow through `Main` + `GameController` so startup is organized and easy to extend.

## Run

```bash
mvn -q compile
mvn -q exec:java -Dexec.mainClass=com.newrussia.game.Main
```

If your environment blocks sound devices, the game still runs (music/voice degrade gracefully).

## Project structure

- `Main`: single entrypoint.
- `GameController`: central wiring and lifecycle management.
- `GameFrame`: gameplay UI and interactions.
- `DemoContent`: locations, NPCs, enemies, and story setup.
- `MusicEngine`, `VoiceEngine`, `CombatEngine`: replaceable systems.
