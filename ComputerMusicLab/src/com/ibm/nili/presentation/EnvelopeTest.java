package com.ibm.nili.presentation;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;

public class EnvelopeTest {
    private static Synthesizer            synth;
    private static UnitOscillator         osc;
    private static LineOut                lineOut;
    private static SegmentedEnvelope      envelope;
    private static VariableRateDataReader envelopePlayer;

    public static void makeASound(SegmentedEnvelope envelope) {
        System.out.println("envelope frame size:" + envelope.getNumFrames());
        // Create a context for the synthesizer.
        synth = JSyn.createSynthesizer();
        // Add a tone generator.
        synth.add(osc = new SawtoothOscillatorBL());
        // Add an envelope player.
        synth.add(envelopePlayer = new VariableRateMonoReader());

        // Add an output mixer.
        synth.add(lineOut = new LineOut());
        //!Use envelope to process amplitude
        envelopePlayer.output.connect(osc.amplitude);
        // Connect the oscillator to the output.
        osc.output.connect(0, lineOut.input, 0);
        osc.output.connect(0, lineOut.input, 1);

        // Start synthesizer using default stereo output at 44100 Hz.
        synth.start();
        // We only need to start the LineOut. It will pull data from the other
        // units.
        lineOut.start();
        try {
            // Queue the entire envelope to play once.
            envelopePlayer.dataQueue.queue(envelope);
            synth.sleepFor(2.0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Stop everything.
            synth.stop();
        }
    }

    /**
     * 3 segments
     * Attack 0.1: 1.0
     * Sustain 0.2: 0.5
     * Release 0.6: 0.0
     */
    public static void testEnvelop1() {

        double [] pairs = {
                //
                0.1, 1.0,
                //
                0.2, 0.5,
                //
                0.6, 0.0};
        envelope = new SegmentedEnvelope(pairs);
        makeASound(envelope);
    }

    /**
     * 4 segments
     * Attack 0.1: 1.0
     * Decay 0.2: 0.5
     * Sustain 0.6: 0.5
     * Release 1.0: 0
     */
    public static void testEnvelop2() {
        double [] pairs = {
                //
                0.1, 1.0,
                //
                0.2, 0.5,
                //
                0.6, 0.5,
                //
                1.0, 0};
        envelope = new SegmentedEnvelope(pairs);
        makeASound(envelope);
    }

    public static void main(String [] args) {
//        testEnvelop1();
                testEnvelop2();
    }
}
