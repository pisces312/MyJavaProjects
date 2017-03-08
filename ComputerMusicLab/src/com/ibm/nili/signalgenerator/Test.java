package com.ibm.nili.signalgenerator;

import javax.sound.sampled.AudioFormat;

import com.ibm.nili.common.Tool;
import com.ibm.nili.music.Pitch;

public class Test {
    public static final int MAX_MAN_HEARD_FREQ = 20000;

    public static void test1() {

        int sampleSize = 8192;
        //  int sampleSize = 4096;
        float sampleRate = 8000;
        double maxA = 127;
        double hz = 440;
        int sampleSizeInBits = 4;

        //        Wave wave = new Wave("audio_work/");
        //        WaveFileWriter wfw = new WaveFileWriter();
        //        WaveHeader hw = new WaveHeader();
        //        //        hw.s
        //        WaveFileManager waveFileManager = new WaveFileManager(wave);

        //        waveFileManager.saveWaveAsFile(outFolder+"/out.wav");
        //        wave.

    }

    //    public static byte [] generateAudioData() {
    //        //      int sampleSize = 0;
    //        //      double []
    //        int sampleSize = 16384;
    //        //      int sampleSize = 8192;
    //        //        int sampleSize = 4096;
    //        float sampleRate = 8000;
    //        double maxA = 127;
    //        double hz = 440;
    //        int sampleSizeInBits = 8;
    //        WaveClip clip = new WaveClip(sampleSize, sampleRate);
    //        System.out.println(clip);
    //        clip.addSignalByFreq(hz);
    //        System.out.println(Arrays.toString(clip.waveData));
    //        return clip.waveData;
    //    }
    public static void test2() {
        //        AudioSystem.get

        //        int sampleSize = 16384;
        int sampleSize = 4001;
        //    int sampleSize = 16000;
        //        int sampleSize = 12000;
        //        int sampleSize = 8000;
        int sampleRate = 8000;
        int sampleSizeInBits = 8;
        double maxValue = WaveClip.getMaxValue(sampleSizeInBits);
        double minValue = WaveClip.getMinValue(sampleSizeInBits);
        //        int sampleSizeInBits = 24;
        //        int sampleSizeInBits = 16;
        AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, 1, true, false);
        Tool.printAudioFormat(audioFormat);
        WaveClip c4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip d4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip e4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip f4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip g4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip a4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        WaveClip b4 = new WaveClip(sampleSize, sampleRate, sampleSizeInBits);
        //        c4.addSignalByFreq(261.63);
        //        d4.addSignalByFreq(293.66);
        //        e4.addSignalByFreq(329.63);
        //        f4.addSignalByFreq(349.23);
        //        g4.addSignalByFreq(392);
        //        a4.addSignalByFreq(440);
        //        b4.addSignalByFreq(493.88);
        //        System.out.println(c4);
        //        WaveClip.mergeSignalByFreq(c4, 697, maxValue*0.2);
        //        WaveClip.mergeSignalByFreq(c4, 1209, maxValue*0.2);
        //dial 1 
        WaveClip.setSignalByFreq(c4, new double[] {697, 1209}, maxValue * 0.6);
        //dial 2 
        WaveClip.setSignalByFreq(c4, new double[] {697, 1336}, maxValue * 0.6);
        //dial 3 
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1633}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {770, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);
        WaveClip.setSignalByFreq(c4, new double[] {697, 1477}, maxValue * 0.6);

        System.out.println(c4);
        Tool.playData(audioFormat, c4.byteData);
        //        playData(audioFormat, d4.waveData);
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, f4.waveData);
        //        playData(audioFormat, g4.waveData);
        //        playData(audioFormat, a4.waveData);
        //        playData(audioFormat, b4.waveData);
        //       
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, f4.waveData);
        //        playData(audioFormat, g4.waveData);
        //        playData(audioFormat, g4.waveData);
        //        playData(audioFormat, f4.waveData);
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, d4.waveData);
        //        playData(audioFormat, c4.waveData);
        //        playData(audioFormat, c4.waveData);
        //        playData(audioFormat, d4.waveData);
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, e4.waveData);
        //        playData(audioFormat, d4.waveData);
        //        playData(audioFormat, d4.waveData);
        //        clip.addSignalByFreq(hz, maxA);

        //        clip.mergeSignalByFreq(1209);
        //        clip.mergeSignalByFreq(697);

        //        System.out.println(Arrays.toString(data));
    }

    public static void test3() {
        double time = 1;
        int sampleRate = 44100;
        int sampleSizeInBits = 16;
        int maxVol = WaveClip.getMaxA(sampleSizeInBits);
        WaveClip c4 = new WaveClip(time, sampleRate, sampleSizeInBits);

        int baseHz = 440;
        int hz = baseHz;
        int n = 3;
        maxVol >>= 1;
        //n次谐波
        int maxHz = baseHz * n;
        while (hz <= maxHz) {
            c4.addSineSignalByFreq(hz, maxVol);
            hz += baseHz;
            //TODO
            maxVol -= (maxVol / 4);
            //        maxVol -= (maxVol / 2);
            //            maxVol *= 0.1;
            //        maxVol >>= 1;
            //            maxVol=(int)Math.sqrt(maxVol);
        }

        //        c4.addSignalByFreq(880, maxValue / 4);
        //        c4.addSignalByFreq(880, maxValue / 4);
        Tool.playData(c4);
    }

    //??

    public static void simulatePiano() {
        WaveClip clip = new WaveClip(2.0, 44100, 16);
        double maxVol = WaveClip.getMaxValue(16);
        clip.addSineSignalByFreq(1318.52, maxVol * 0.2);
        clip.addSineSignalByFreq(1046.52, maxVol * 0.1);
        clip.addSineSignalByFreq(783.99, maxVol * 0.2);
        clip.addSineSignalByFreq(659.26, maxVol * 0.1);
        clip.addSineSignalByFreq(523.25, maxVol * 0.2);
        Tool.playData(clip);
    }

    public static void play88keys() {
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(0.15, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        for (int i = 0; i < Pitch.OCTAVE_NUM; ++i) {
            for (int j = 0; j < Pitch.PITCH_NUM_PER_OCTAVE; ++j) {
                if (Pitch.pitchMap[i][j] != null) {
                    System.out.print(Pitch.pitchMap[i][j] + " ");
                    clip.setSignalByFreq(Pitch.pitchMap[i][j].freq, maxVol * 0.6);
                    Tool.playData(clip);
                }
            }
            System.out.println();

        }

    }

    public static void playScale() {
        int octnum = 10;
        Pitch.configToneEqualTemperament(440, octnum, 22);
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(0.25, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        int oct = octnum / 2 - 1;
        for (int j = 0; j < Pitch.PITCH_NUM_PER_OCTAVE; ++j) {
            if (Pitch.pitchMap[oct][j] != null) {
                System.out.print(Pitch.pitchMap[oct][j] + " ");
                clip.setSignalByFreq(Pitch.pitchMap[oct][j].freq, maxVol * 0.6);
                Tool.playData(clip);
            }
        }
        System.out.println();

    }

    public static void main(String [] args) {
        //        playScale();
        //        play88keys();
        //        simulatePiano();
        //               Tool.playData(29500,3);
        //       Tool.playData(29000,3);
        //       System.out.println(Tool.playData(7000,3));
        //               Tool.playData(7700,3);
        //               Tool.playData(17000,3);
        //               Tool.playData(16000,3);
        //      Tool.playData(4400,3);
        //               Tool.playData(64,10);
        //               Tool.playData(27.5,3);
        //                Tool.playData(220,2);
        //        Tool.playData(880, 2);
        //        int sampleSizeInBits = 16;
        int sampleSizeInBits = 16;
        //        WaveClip clip = new WaveClip(2.0, 8000, sampleSizeInBits);
        WaveClip clip = new WaveClip(1.0, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        //                clip.addSineSignalByFreq(329.63, maxVol * 0.3);        
        //                clip.addSineSignalByFreq(261.63, maxVol * 0.3);
        //                clip.addSineSignalByFreq(392, maxVol * 0.3);    

////        //单簧管
//        double baseHz = 440;
//        clip.addSineSignalByFreq(baseHz, maxVol * 0.5);
//        clip.addSineSignalByFreq(baseHz * 3, maxVol * 0.1);
//        clip.addSineSignalByFreq(baseHz * 5, maxVol * 0.1);
//        clip.addSineSignalByFreq(baseHz * 7, maxVol * 0.1);
        //
        //提琴
        //        double baseHz = 440;
        //        clip.addSineSignalByFreq(baseHz, maxVol * 0.4);
        //        clip.addSineSignalByFreq(baseHz * 2, maxVol * 0.2);
        //        clip.addSineSignalByFreq(baseHz * 5, maxVol * 0.2);
        //                clip.addSineSignalByFreq(261.63, maxVol * 0.3);
        //                clip.revese();
//      //军号
      double baseHz = 261.63;
      clip.addSineSignalByFreq(baseHz, maxVol * 0.5);
      clip.addSineSignalByFreq(baseHz * 8, maxVol * 0.1);
      clip.addSineSignalByFreq(baseHz * 12, maxVol * 0.1);
      clip.addSineSignalByFreq(baseHz * 14, maxVol * 0.1);
        //        clip.addSineSignalByFreq(3520, maxVol * 0.2);
        //        System.out.println(clip);
        Tool.playData(clip);
    }

}
