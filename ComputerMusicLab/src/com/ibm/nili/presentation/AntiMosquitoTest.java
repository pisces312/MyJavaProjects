package com.ibm.nili.presentation;

import com.ibm.nili.common.Tool;
import com.ibm.nili.signalgenerator.WaveClip;

public class AntiMosquitoTest {
    static int      sampleSizeInBits = 16;
    static WaveClip clip             = new WaveClip(2.0f, 44100, sampleSizeInBits);
    static double   maxVol           = WaveClip.getMaxValue(sampleSizeInBits);

    public static void play(double hz) {
        clip.resetData();
        clip.addSineSignalByFreq(hz, maxVol * 0.8);
        Tool.playData(clip);
    }

    public static void low() {
        play(500);
    }

    public static void high() {
        play(10000);
        play(12000);
        play(14000);
        play(16000);
        play(18000);
        play(20000);
    }

    public static void ultrasonic() {
        play(40000);
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
//                low();
//        high();
                ultrasonic();

    }

}
