package com.beyondstranded.app;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

/** Plays a midi file provided on command line */

public class MidiPlayer {

    public static void main(String[] args) {

    }

    public void playIntro(){
        String soundPath = "midifiles/ky1_14.mid";
        playSound(soundPath);
    }
    private void playSound(String soundFilePath){
        try {
            // Load the MIDI file
//            Sequence sequence = MidiSystem.getSequence(new File("resources/midifiles/Boss.mid"));
            // Load the MIDI file from resources
            ClassLoader classLoader = MidiPlayer.class.getClassLoader();
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
//    public static void main(String args[]) {
//
//        String soundFilePath = "midifiles/Boss.mid";
//        File soundFile = new File(soundFilePath);
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//
//        clip.start();

//        // Argument check
//        if(args.length == 0) {
//            helpAndExit();
//        }
//        String file = args[0];
//        if(!file.endsWith(".mid")) {
//            helpAndExit();
//        }
//        File midiFile = new File(file);
//        if(!midiFile.exists() || midiFile.isDirectory() || !midiFile.canRead()) {
//            helpAndExit();
//        }
//        // Play once
//        try {
//            Sequencer sequencer = MidiSystem.getSequencer();
//            sequencer.setSequence(MidiSystem.getSequence(midiFile));
//            sequencer.open();
//            sequencer.start();
//            while(true) {
//                if(sequencer.isRunning()) {
//                    try {
//                        Thread.sleep(1000); // Check every second
//                    } catch(InterruptedException ignore) {
//                        break;
//                    }
//                } else {
//                    break;
//                }
//            }
//            // Close the MidiDevice & free resources
//            sequencer.stop();
//            sequencer.close();
//        } catch(MidiUnavailableException mue) {
//            System.out.println("Midi device unavailable!");
//        } catch(InvalidMidiDataException imde) {
//            System.out.println("Invalid Midi data!");
//        } catch(IOException ioe) {
//            System.out.println("I/O Error!");
//        }
//
//    }
//
//    /** Provides help message and exits the program */
//    private static void helpAndExit() {
//        System.out.println("Usage: java MidiPlayer midifile.mid");
//        System.exit(1);
//    }
}