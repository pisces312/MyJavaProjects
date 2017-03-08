package com.ibm.nili.presentation;

import java.util.ArrayList;
import java.util.List;

import com.ibm.nili.common.Tool;
import com.ibm.nili.music.Pitch;
import com.ibm.nili.music.Pitch.PitchName;
import com.ibm.nili.signalgenerator.WaveClip;

public class SaxophoneTest {
    static int       sampleSizeInBits = 16;
//    static WaveClip  clip             = new WaveClip(1.0f, 44100, sampleSizeInBits);
    static double    maxVol           = WaveClip.getMaxValue(sampleSizeInBits);
    static double [] vols             = {0.32, 0.22, 0.07, 0.17, 0.05, 0.07, 0.05, 0.05};
    public static WaveClip generateSaxophoneSound(double hz) {
        WaveClip clip = new WaveClip(0.5f, 44100, sampleSizeInBits);
        double d = hz;
        for (int i = 0; i < vols.length; ++i, hz += d) {
            clip.addSineSignalByFreq(hz, maxVol * vols[i]);
        }
        return clip;
    }

    //    public static void playSaxophone(double hz) {
    //        clip.resetData();
    //
    //        double [] vols = {0.38, 0.25, 0.19, 0.09, 0.04, 0.03};
    //        clip.addSineSignalByFreq(hz, maxVol * vols[0]);
    //        double d = hz * 2;
    //        hz = d;
    //        for (int i = 1; i < vols.length; ++i, hz += d) {
    //            clip.addSineSignalByFreq(hz, maxVol * vols[i]);
    //        }
    //
    //        Tool.playData(clip);
    //    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        List<WaveClip> list = new ArrayList<WaveClip>();
        int octave = 4;
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.C)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.D)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.E)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.F)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.G)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.A)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave, PitchName.B)));
        list.add(generateSaxophoneSound(Pitch.getFreqOfPitch(octave+1, PitchName.C)));
        Tool.playData(list);
    }

}
