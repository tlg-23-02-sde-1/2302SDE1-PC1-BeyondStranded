package com.beyondstranded.app;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

/** Plays a midi file provided on command line */

public class MidiPlayer {

    public static void main(String[] args) {
//        MidiPlayer player = new MidiPlayer();
//        player.playIntro();
    }

    public void playIntro() {
        String soundPath = "midiFiles/intro.mid";
        playSound(soundPath);
    }

    public void playGamePlay(){
        String soundPath = "midiFiles/Sanctuary.mid";
        playSound(soundPath);
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
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

            // Set the sequence in the sequencer and start playback
            sequencer.setSequence(sequence);
            sequencer.start();

            // Wait for the sequencer to finish playing
            while (sequencer.isRunning()) {
                Thread.sleep(100);
            }

            // Cleanup: Stop the sequencer, close the synthesizer
            sequencer.stop();
            sequencer.close();
            synthesizer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}