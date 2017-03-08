package com.ibm.nili.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ibm.nili.common.Tool;
import com.ibm.nili.dsp.fft.AbstractFFT;
import com.ibm.nili.dsp.fft.FFT;
import com.ibm.nili.music.TonePlayer;
import com.ibm.nili.spectrum.SerialAudioConsumer;

public class Test {
    public static void testLogic() {
        //        String fileToPlay = "canon in d 2chn 16 bit.wav";
        String fileToPlay = "canon in d.wav";
        //        String fileToPlay = "Windows Ding.wav";
        //        String fileToPlay = "C:\\WINDOWS\\Media\\Windows Notify.wav";
        //        String fileToPlay = "C:\\WINDOWS\\Media\\Windows XP Startup.wav";
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(new File(fileToPlay));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ais == null) {
            return;
        }
        //date
        AudioFormat baseFormat = ais.getFormat();
        //must <=8192
        int sampleSizeWithPadding = 4096;
        System.out.println(Tool.getTimeBySampleSize(sampleSizeWithPadding, baseFormat.getSampleRate()));

        SourceDataLine sourceDataLine = Tool.getLineWithSampleSize(baseFormat, sampleSizeWithPadding);
        Tool.printSourceDataLine(sourceDataLine);
        int exp = Tool.getExpOf2(sampleSizeWithPadding);
        long start = System.currentTimeMillis();
        //use miny to calc faster
        AbstractFFT fft = new FFT(exp);
        //        IFFT fft = new KJFFT(exp);
        SerialAudioConsumer consumer = SerialAudioConsumer.createInstance(sourceDataLine, ais, fft);
        //process 512
        int actualSampleSize = 512;
        //        consumer.start(actualSampleSize);
        System.out.println("accuracy:" + baseFormat.getSampleRate() / actualSampleSize + "hz");
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void test() {
        double start = 0.0;
        double end = 1.0;
        //        double d = 0.25;
        //        double d = 0.2;
        double d = 0.15;
        //        double d = 0.3;

        int n = Tool.calcScaleNumber(d, end - start);
        System.out.println(n);
        System.out.println(Tool.getFitScaleNumber(n));
        double scale[] = Tool.calcScale(d, start, end);
        System.out.println(Arrays.toString(scale));

        //        System.out.println(calcScaleNumber(2, 3));
        //        System.out.println(calcScaleNumber(2, 1));
        //        System.out.println(calcScaleNumber(7.5, 30));
        //        System.out.println(calcScaleNumber(12, 13));
        //        System.out.println(calcScaleNumber(2, 33));
    }

    public static void testMiDi() {
        Synthesizer synthesizer = null;
        try {

            synthesizer = MidiSystem.getSynthesizer();
            //must open first
            synthesizer.open();
            MidiDevice.Info midiInfo = synthesizer.getDeviceInfo();
            //            synthesizer.getAvailableInstruments()            
            System.out.println(midiInfo);
            MidiDevice midiDevice = MidiSystem.getMidiDevice(midiInfo);
            System.out.println(midiDevice);
            MidiChannel channels[] = synthesizer.getChannels();
            //
            //
            //            int num = 5;
            int [] notes = new int[] {60, 64};
            int [] velocities = new int[] {93, 93};
            int [] durations = new int[] {1000, 1000};
            MidiChannel pianoChannel = null;
            for (MidiChannel channel : channels) {
                pianoChannel = channel;
                //                channel.
                System.out.println(channel.getPitchBend());
                System.out.println(channel.getProgram());
                //                System.out.println(channel.);
                //                playChannel(channel, notes, velocities, durations);
                break;
            }
            TonePlayer.playMultipleNote(pianoChannel, notes, 90, 500, 500);
            TonePlayer.playSingleNote(pianoChannel, 60, 90, 500, 500);
            //            playNoteSequence(pianoChannel, notes, velocities, durations);
            //
            //

            //
            //
            Soundbank sb = synthesizer.getDefaultSoundbank();
            if (sb != null) {
                Instrument [] instruments = sb.getInstruments();
                for (Instrument ins : instruments) {
                    System.out.println(ins);
                }
            }
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (synthesizer != null) {
                synthesizer.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        testMiDi();
        //        MidiSystem.get
        //        Sequence sequence= MidiSystem.getSequence(new File());

    }

}
