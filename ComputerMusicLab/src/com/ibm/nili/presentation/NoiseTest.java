package com.ibm.nili.presentation;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.jsyn.SynthException;

public class NoiseTest {
    public static void playSound(UnitOscillator osc, double second) {
        LineOut lineOut;
        try
        {
            Synthesizer synth = JSyn.createSynthesizer();
            synth.add(lineOut = new LineOut());
            // Create some unit generators.
            //            RedNoise osc = new RedNoise();
            osc.amplitude.set(0.8);
            synth.add(osc);
            // Connect oscillator to both left and right channels of output.
            osc.output.connect(0, lineOut.input, 0);
            osc.output.connect(0, lineOut.input, 1);

            // Start the unit generators so they make sound.
            synth.start();
            lineOut.start();
            osc.start();
            // Sleep for awhile so we can hear the sound.
            synth.sleepFor(second);
            // Stop units and delete them to reclaim their resources.
            osc.stop();
            lineOut.stop();
            // Stop JSyn synthesizer.
            synth.stop();
        } catch (SynthException e) {
            System.out.println("Caught " + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playWhiteNoise(double second) {
        LineOut lineOut;
        try
        {
            Synthesizer synth = JSyn.createSynthesizer();
            synth.add(lineOut = new LineOut());
            // Create some unit generators.
            WhiteNoise osc = new WhiteNoise();
            osc.amplitude.set(0.8);
            synth.add(osc);
            // Connect oscillator to both left and right channels of output.
            osc.output.connect(0, lineOut.input, 0);
            osc.output.connect(0, lineOut.input, 1);

            // Start the unit generators so they make sound.
            synth.start();
            lineOut.start();
            osc.start();
            // Sleep for awhile so we can hear the sound.
            synth.sleepFor(second);
            // Stop units and delete them to reclaim their resources.
            osc.stop();
            lineOut.stop();
            // Stop JSyn synthesizer.
            synth.stop();
        } catch (SynthException e) {
            System.out.println("Caught " + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testRedNoise(){
        playSound(new RedNoise(), 3);
    }
    public static void testPinkNoise(){
        playSound(new PinkNoise(), 3);
    }
    public static void testWhiteNoise(){
        playWhiteNoise(3);
    }
    /**
     * @param args
     */
    public static void main(String [] args) {
//       testWhiteNoise();
//       testPinkNoise();
       testRedNoise();

    }

}
