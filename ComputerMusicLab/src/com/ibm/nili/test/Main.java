package com.ibm.nili.test;

/*
 * Copyright (c) 2004 David Flanagan. All rights reserved. This code is from the book Java Examples
 * in a Nutshell, 3nd Edition. It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, and modify it for any non-commercial purpose, including teaching and
 * use in open-source projects. You may distribute it non-commercially as long as you retain this
 * notice. For a commercial use license, or to purchase the book, please visit
 * http://www.davidflanagan.com/javaexamples3.
 */

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;

/**
 * This program the MIDI percussion channel with a Swing window. It monitors keystrokes and mouse
 * motion in the window and uses them to create music. Keycodes between 35 and 81, inclusive,
 * generate different percussive sounds. See the VK_ constants in java.awt.event.KeyEvent, or just
 * experiment. Mouse position controls volume: move the mouse to the right of the window to increase
 * the volume.
 */
public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    MidiChannel channel; // The channel we play on: 10 is for percussion

    int velocity = 64; // Default volume is 50%

    public static void main(String[] args) throws MidiUnavailableException {
        // We don't need a Sequencer in this example, since we send MIDI
        // events directly to the Synthesizer instead.
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        JFrame frame = new Main(synthesizer);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(50, 128); // We use window width as volume control
        frame.setVisible(true);
    }

    public Main(Synthesizer synth) {
        super("Drums");

        // Channel 10 is the GeneralMidi percussion channel. In Java code, we
        // number channels from 0 and use channel 9 instead.
        channel = synth.getChannels()[9];

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key >= 35 && key <= 81) {
                    channel.noteOn(key, velocity);
                }
            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key >= 35 && key <= 81)
                    channel.noteOff(key);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                velocity = e.getX();
            }
        });
    }
}
