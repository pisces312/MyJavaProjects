package com.ibm.nili.test.jsyn;

import com.softsynth.jsyn.AddUnit;
import com.softsynth.jsyn.LineOut;
import com.softsynth.jsyn.SawtoothOscillatorBL;
import com.softsynth.jsyn.SineOscillator;
import com.softsynth.jsyn.Synth;
import com.softsynth.jsyn.SynthException;

public class JSynTest {
    public static void testMixingSoundsTogether() {
        Synth.startEngine(0);
        /* Create unit generators. */
        /**
         * Sounds can be mixed together by adding together the numbers that
         * represent the sound. In order to control their relative loudness, we
         * can change the amplitude of the individual sounds. In this Applet, we
         * mix two SineOscillators using an AddUnit.
         */
        AddUnit mixer = new AddUnit();
        SineOscillator sineOsc1 = new SineOscillator();
        SineOscillator sineOsc2 = new SineOscillator();
        LineOut lineOut = new LineOut();

        /* Feed both oscillators to the mixer to be added together. */
        //Now let's connect the oscillators to the AddUnit's two inputs:
        sineOsc1.output.connect(0, mixer.inputA, 0);
        sineOsc2.output.connect(mixer.inputB); // the lazy way

        /* Connect oscillator to LineOut so we can hear it. */
        mixer.output.connect(0, lineOut.input, 0);
        mixer.output.connect(0, lineOut.input, 1);

        /**
         * To prevent clipping, set the amplitudes of your sounds so that they
         * cannot add up over 1.0. For example, if you have N oscillators, set
         * the amplitude to (1.0/N).
         */
        /**
         * 1.Set the amplitude of each oscillator to about 0.4.
         * 2.Change the frequencies of each oscillator so that you can hear each
         * of them distinctly.
         * 3.For safety, turn down the volume of your stereo speakers until it
         * is
         * at a soft level.
         * 4.Set the amplitudes of each oscillator to about 0.6. See and hear
         * the
         * clipping that results.
         * 5.The scope shows the color coded output of both oscillators and the
         * mixer. Convince yourself that the mixer output is the sum of the two
         * oscillators. (It may appear to be very slightly delayed.)
         */
        sineOsc1.amplitude.set(0.4);
        sineOsc2.amplitude.set(0.4);

        sineOsc1.frequency.set(440);
        sineOsc2.frequency.set(261.63);

        /* Start units. */
        lineOut.start();
        mixer.start();
        sineOsc1.start();
        sineOsc2.start();

        //
        Synth.sleepForTicks(2000);
        //
        Synth.stopEngine();

    }

    public static void testPlaySawtoothOscillator() {
        // create sawtooth oscillator that looks like /|/|/|/
        SawtoothOscillatorBL osc;
        LineOut lineOut;
        try
        {
            // Start JSyn synthesizer.
            Synth.startEngine(0);

            // Create some unit generators.
            osc = new SawtoothOscillatorBL();
            lineOut = new LineOut();

            // Connect oscillator to both left and right channels of output.
            osc.output.connect(0, lineOut.input, 0);
            osc.output.connect(0, lineOut.input, 1);

            // Start the unit generators so they make sound.
            osc.start();
            lineOut.start();

            // Set the frequency of the oscillator to 200 Hz.
            osc.frequency.set(200.0);
            osc.amplitude.set(0.8);

            // Sleep for awhile so we can hear the sound.
            Synth.sleepForTicks(400);

            // Change the frequency of the oscillator.
            osc.frequency.set(300.0);
            Synth.sleepForTicks(400);

            // Stop units and delete them to reclaim their resources.
            osc.stop();
            lineOut.stop();
            osc.delete();
            lineOut.delete();

            // Stop JSyn synthesizer.
            Synth.stopEngine();

        } catch (SynthException e)
        {
            System.out.println("Caught " + e);
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        //        testPlaySawtoothOscillator();
        testMixingSoundsTogether();

    }

}
