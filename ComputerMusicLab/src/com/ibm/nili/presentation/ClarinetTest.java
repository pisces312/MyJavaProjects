package com.ibm.nili.presentation;

import java.util.ArrayList;
import java.util.List;

import com.ibm.nili.common.Tool;
import com.ibm.nili.music.Pitch;
import com.ibm.nili.music.Pitch.PitchName;
import com.ibm.nili.signalgenerator.WaveClip;

public class ClarinetTest {
    static int       sampleSizeInBits = 16;
    //    static WaveClip  clip             = new WaveClip(0.5f, 44100, sampleSizeInBits);
    static double    maxVol           = WaveClip.getMaxValue(sampleSizeInBits);
    static double [] vols             = {0.5, 0.25, 0.15, 0.12, 0.08};

    public static WaveClip generateClarinetSound(double hz) {
        WaveClip clip = new WaveClip(0.5f, 44100, sampleSizeInBits);
        //      clip.resetData();
        double d = hz * 2;
        for (int i = 0; i < vols.length; ++i, hz += d) {
            clip.addSineSignalByFreq(hz, maxVol * vols[i]);
        }
        return clip;
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        List<WaveClip> list = new ArrayList<WaveClip>();
        int octave = 4;
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.C)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.D)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.E)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.F)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.G)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.A)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave, PitchName.B)));
        list.add(generateClarinetSound(Pitch.getFreqOfPitch(octave+1, PitchName.C)));
        Tool.playData(list);
    }

}
