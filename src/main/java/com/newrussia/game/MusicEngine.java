package com.newrussia.game;

import javax.sound.midi.*;

public final class MusicEngine {
    private Sequencer sequencer;

    public void startAmbientLoop() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            add(track, ShortMessage.PROGRAM_CHANGE, 0, 0, 91, 0);
            int[] notes = {48, 55, 53, 46, 50, 57, 41, 45};
            for (int i = 0; i < notes.length; i++) {
                int tick = i * 8;
                add(track, ShortMessage.NOTE_ON, tick, 0, notes[i], 55);
                add(track, ShortMessage.NOTE_OFF, tick + 6, 0, notes[i], 0);
            }
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(72);
            sequencer.setSequence(sequence);
            sequencer.start();
        } catch (Exception ignored) {
            // Audio is optional in restricted environments.
        }
    }

    public void stop() {
        if (sequencer != null) {
            sequencer.stop();
            sequencer.close();
        }
    }

    private void add(Track track, int command, int tick, int channel, int data1, int data2) throws Exception {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, data1, data2);
        track.add(new MidiEvent(message, tick));
    }
}
