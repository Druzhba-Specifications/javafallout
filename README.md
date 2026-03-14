# Fallout: New Russia (Plain Java Prototype)

A playable Java desktop prototype inspired by classic Fallout-style structure.

## What's included

- Turn-based combat loop using S.P.E.C.I.A.L-derived AP and damage calculations.
- Multiple scenes/locations with travel links, hidden places, enemies, and cutscene text.
- NPC dialogue interactions with speech checks and animated talking-head panel.
- Background ambient MIDI music engine and per-NPC voice cue hooks.
- Central app bootstrap/controller flow through `Main` + `GameController` so startup is organized and easy to extend.
- Detailed world setup for Novaya Metro, Red Square Ruins, Kremlin Depths, and Volga Shore Fortress.

## Run

```bash
mvn -q compile
mvn -q exec:java -Dexec.mainClass=com.newrussia.game.Main
mvn -q exec:java -Dexec.mainClass=com.newrussia.game.FalloutNewRussiaApp
```

If your environment blocks sound devices, the game still runs (music/voice degrade gracefully).

## Project structure

- `Main`: single entrypoint.
- `GameController`: central wiring and lifecycle management.
- `GameFrame`: gameplay UI and interactions.
- `DemoContent`: locations, NPCs, enemies, and story setup.
- `MusicEngine`, `VoiceEngine`, `CombatEngine`: replaceable systems.
## Notes on assets

This project includes generated/procedural placeholders for portraits and audio cues (beeps + MIDI), so it is immediately runnable without external files. The architecture is separated into engines (`MusicEngine`, `VoiceEngine`, `CombatEngine`) and content (`DemoContent`) to allow replacing placeholders with full textures, voice acting, and cutscenes.
