package com.ibm.nili.spectrum;

import java.awt.Component;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.ibm.nili.common.Tool;
import com.ibm.nili.dsp.DigitalSignalProcessor;
import com.ibm.nili.spectrum.ui.SpectrumFixedCanvas;

public class SpectrumAnaylzer {

    public static JFrame loadMainFrame(int frameW, int frameH, Component canvas) {

        final JFrame frame = new JFrame("Spectrum Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setResizable(false);
        //        frame.setResizable(true);
        JScrollPane scrollPane = new JScrollPane(canvas);
        //        scrollPane.setIgnoreRepaint(true);
        //        scrollPane.setDoubleBuffered(false);
        //        scrollPane.setDoubleBuffered(true);
        //        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);
        frame.setVisible(true);
        return frame;
    }

    public static void testFixedCanvas() {
        //        String fileToPlay = "D:\\!音频处理\\piano0.wav";
        //        String fileToPlay = "D:\\120905\\sample.wav";
        //
        //
        //        String fileToPlay = "D:\\temp\\12 - canon in d.mp3";
        String fileToPlay = "canon in d 2chn 16 bit.wav";
        //        String fileToPlay = "canon in d 44100 16.wav";
//                String fileToPlay = "canon in d.wav";
        //        String fileToPlay = "D:\\temp\\canon in d all.wav";
        //        timeBegin = 18f;
        //        timeEnd = 40f;
        //        timeP = (timeEnd - timeBegin) / 10;
        //        valve = 0.007f;
        //GUI
        //        float timeBegin = 120.0f;
        //        float timeEnd = 160f;
        float timeBegin = 0f;
        //        float timeEnd = 5f;
        //        float timeEnd = 10000f;
        float timeEnd = 18f;

        //        timeBegin = 0.5f;
        //        timeEnd = 5f;

        //        float valve = 0.1f;
        //        float valve = 0.006f;

        float valve = (float)Tool.getNormalizedDataFromDB(-35);
        //        float valve = 20f;
        //        float valve = 130f;
        //        float valve = 120f;
        int freqBegin = 50;
        int freqEnd = 2000;
        //parameters
        //1/4 sampe rate??
        //the larger, the delta time longer
        //        int actualSampleSize = 512;
        //        int actualSampleSize = 2048;
        int actualSampleSize = 4096;
        //        int actualSampleSize = 8192;
        //        int actualSampleSize = 22050;
        //        int actualSampleSize = 44100;
        //        int maxNoteCount = 3;
        //        float correctness = 0.6f;

        int frameW = 800;
        int frameH = 600;
        //canvas size can larger than frame size
        int canvasW = 4300;
        int canvasH = frameH - 50;
        boolean isShowNote = true;
        boolean isOnlyShowNoteFreq = true;
        ///////////////////////////////////////////////////////////////////////
        AudioInputStream ais = Tool.getAudisoInputStream(fileToPlay);
        if (ais == null) {
            return;
        }
        try {
            AudioFormat format = ais.getFormat();
            float maxTimeEnd = Tool.getMusicTimeinSecond(ais);
            if (timeEnd > maxTimeEnd) {
                timeEnd = maxTimeEnd;
            }
            System.out.println("timeEnd " + timeEnd);

            float timeP = (timeEnd - timeBegin) / 10;
            //            timeP=0.1f;
            //GUI

            Collection<DigitalSignalProcessor> dsps = new LinkedList<DigitalSignalProcessor>();
            //
            //
            //            SpectrumMusicConverter converter = new SpectrumMusicConverter(freqBegin, freqEnd, valve, actualSampleSize,
            //                    (int)format.getSampleRate());
            //            converter.setConvertParameters(maxNoteCount, correctness);
            //            dsps.add(converter);

            //            int canvasH = frameH - 20;
            final SpectrumFixedCanvas canvas = SpectrumFixedCanvas.createInstance(canvasW, canvasH, actualSampleSize,
                    (int)format.getSampleRate(), timeBegin, timeEnd, timeP, freqBegin, freqEnd, valve);
            canvas.setShowNoteFlag(isShowNote);
            canvas.setOnlyShowNoteFreqFlag(isOnlyShowNoteFreq);
            loadMainFrame(frameW, frameH, canvas);
            //data process
            dsps.add(canvas);
            SerialAudioConsumer.spectrumAnalyze(ais, actualSampleSize, timeBegin, timeEnd, dsps);
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * To get the real frequency we need to double the fft idx!!
     * 
     * @param args
     */
    public static void main(String [] args) {
        testFixedCanvas();
    }

}
