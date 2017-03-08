package jm.audio.inst;

import jm.audio.io.SampleOut;
import jm.audio.synth.Envelope;
import jm.audio.synth.Oscillator;

/**
 * A basic wavtebable synthesis instrument implementation
 * which implements envelope and volume control
 * 
 * @author Andrew Sorensen
 */

public final class SimpleSineInst extends jm.audio.Instrument {
    //----------------------------------------------
    // Attributes
    //----------------------------------------------
    /** the sample rate passed to the instrument */
    private int sampleRate;
    /** the sample rate passed to the instrument */
    private int channels;

    //----------------------------------------------
    // Constructor
    //----------------------------------------------
    /**
     * Basic default constructor to set an initial
     * sampling rate.
     * 
     * @param sampleRate
     */
    public SimpleSineInst(int sampleRate) {
        this.sampleRate = sampleRate;
        this.channels = 1;
    }

    //----------------------------------------------
    // Methods 
    //----------------------------------------------

    /**
     * Initialisation method used to build the objects that
     * this instrument will use.
     * Declares the primary audio object array and the
     * audio object(s) in that array. (One array element per channel)
     */
    public void createChain() {
        Oscillator osc = new Oscillator(this, Oscillator.SINE_WAVE,
                this.sampleRate, this.channels);
        Envelope env = new Envelope(osc,
                new double[] {0.0, 0.0, 0.1, 1.0, 1.0, 0.0});
        if (output == RENDER)
            sout = new SampleOut(env);
    }
}
