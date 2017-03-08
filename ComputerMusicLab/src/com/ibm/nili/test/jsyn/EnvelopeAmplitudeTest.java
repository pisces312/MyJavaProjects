/**
 * Use an envelope to control the amplitude of a sine wave.
 * This version uses the obsolete but universal 1.0.2 AWT API for user interfaces.
 *
 * @author (C) 2000 Phil Burk, All Rights Reserved
 */

package com.ibm.nili.test.jsyn; /* Put resulting class into tutorial package. */

import com.softsynth.jsyn.EnvelopePlayer;
import com.softsynth.jsyn.LineOut;
import com.softsynth.jsyn.SineOscillator;
import com.softsynth.jsyn.Synth;
import com.softsynth.jsyn.SynthEnvelope;
import com.softsynth.jsyn.SynthException;

public class EnvelopeAmplitudeTest
{

    /* Can be run as either an application or as an applet. */
    public static void main(String args[])
    {
        /* DO: Declare Synthesis Objects here */
        SineOscillator sineOsc;
        SynthEnvelope envData;
        EnvelopePlayer envPlayer;
        LineOut lineOut;
        try
        {
            Synth.startEngine(0);
            // Create unit generators.
            sineOsc = new SineOscillator();
            envPlayer = new EnvelopePlayer(); // create an envelope player
            lineOut = new LineOut();

            // Connect oscillator to LineOut so we can hear it.
            sineOsc.output.connect(0, lineOut.input, 0);
            sineOsc.output.connect(0, lineOut.input, 1);

            //!!! control sine wave amplitude with envelope output
            //设置控制的是振幅
            envPlayer.output.connect(sineOsc.amplitude);

            // define shape of envelope as an array of doubles
            double [] data =
            {
                    0.02, 1.0, // The envelope moves quickly to 1.0 in 0.02 seconds.
                    0.30, 0.5, // Then it takes 0.3 seconds to drop to 0.5.
                    1.20, 0.0 // Then it takes 1.2 seconds to drop to 0.0.
            };
            envData = new SynthEnvelope(data);
            envPlayer.envelopePort.queue(envData);

            // Start units.
            lineOut.start();
            sineOsc.start();
            envPlayer.start();

            Synth.sleepForTicks(1500);

            envPlayer.envelopePort.clear();
        } catch (SynthException e) {
            //            SynthAlert.showError(this, e);
        } finally {
            Synth.stopEngine();
        }
    }

}