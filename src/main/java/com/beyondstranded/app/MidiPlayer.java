package com.beyondstranded.app;

import javax.sound.midi.*;

/** Plays a midi file provided on command line */

public class MidiPlayer {

    private Sequencer sequencer;

    public void playIntro() {
        String soundPath = "midiFiles/intro.mid";
        playSound(soundPath);
    }

    public void playGamePlay(){
        String soundPath = "midiFiles/Sanctuary.mid";
        playSound(soundPath);
    }

    public void stopMusic() {
        if (sequencer != null && sequencer.isRunning()) {
            sequencer.stop();
        }
    }

    private void playSound(String soundFilePath) {
        try {
            ClassLoader classLoader = MidiPlayer.class.getClassLoader();
            @SuppressWarnings("ConstantConditions")
            Sequence sequence = MidiSystem.getSequence(classLoader.getResourceAsStream(soundFilePath));

            // Open a MIDI device
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            // Create a sequencer and connect it to the synthesizer
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

            // Set the sequence in the sequencer and start playback
            sequencer.setSequence(sequence);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}