package com.ibm.nili.presentation;

import com.ibm.nili.common.Tool;
import com.ibm.nili.signalgenerator.WaveClip;

public class SineWaveTest {

    public static void play440hz() {
        float duration = 5.0f;
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(duration, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        clip.addSineSignalByFreq(440, maxVol * 0.8);
        Tool.playData(clip);
    }

    public static void compare() {
        float duration = 3.0f;
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(duration, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        clip.addSineSignalByFreq(220, maxVol * 0.8);
        Tool.playData(clip);
        clip.setSignalByFreq(440, maxVol * 0.8);
        Tool.playData(clip);
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        play440hz();
        //        double time=0.5;        
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.C),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.D),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.E),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.F),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.G),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.A),time);
        //        Tool.playData(Pitch.getFreqOfPitch(3, PitchName.B),time);
        //        Tool.playData(Pitch.getFreqOfPitch(4, PitchName.C),time);
        //        Tool.playData(Pitch.getFreqOfPitch(4, PitchName.D),time);
        //        Tool.playData(Pitch.getFreqOfPitch(4, PitchName.E),time);

        //        int sampleSizeInBits = 16;
        //        WaveClip clip = new WaveClip(time, 44100, sampleSizeInBits);
        //        clip.setSignalByFreq(Pitch.getFreqOfPitch(3, PitchName.C));
        ////        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        //        List<WaveClip> list=new ArrayList<WaveClip>();
        //        list.add(clip);
        //        Tool.playData(list);
        //        compare();

    }

}
