package com.newrussia.game.systems;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public final class MusicEngine {
    private Sequencer sequencer;
    private String currentTrack = "Wasteland Ambient";

    public void start(String trackName) {
        currentTrack = trackName;
        try {
            stop();
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = buildTrack(trackName);
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(72f);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (Exception ignored) {
            // graceful fallback
        }
    }

    private Sequence buildTrack(String trackName) throws Exception {
        int[] notes = switch (trackName) {
            case "Kremlin Depth Pulse" -> new int[]{38, 45, 43, 40, 47, 42, 50, 45};
            case "Volga Ice Wind" -> new int[]{52, 55, 57, 59, 55, 54, 52, 50};
            case "Red Square Ashes" -> new int[]{48, 50, 51, 53, 50, 48, 46, 45};
            default -> new int[]{46, 50, 53, 55, 53, 50, 48, 46};
        };

        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        add(track, ShortMessage.PROGRAM_CHANGE, 0, 0, 91, 0);

        for (int i = 0; i < notes.length; i++) {
            int tick = i * 8;
            add(track, ShortMessage.NOTE_ON, tick, 0, notes[i], 62);
            add(track, ShortMessage.NOTE_OFF, tick + 6, 0, notes[i], 0);
        }
        return sequence;
    }

    private void add(Track track, int command, int tick, int channel, int data1, int data2) throws Exception {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, data1, data2);
        track.add(new MidiEvent(message, tick));
    }

    public void stop() {
        if (sequencer != null) {
            sequencer.stop();
            sequencer.close();
            sequencer = null;
        }
    }

    public String currentTrack() {
        return currentTrack;
    }
}
