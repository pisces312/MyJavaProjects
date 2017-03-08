package com.ibm.nili.spectrum;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

import com.ibm.nili.common.Tool;
import com.ibm.nili.dsp.DigitalSignalProcessor;
import com.ibm.nili.dsp.fft.AbstractFFT;
import com.ibm.nili.dsp.fft.FFT;

public class SerialAudioConsumer {

    //    private static final int DEFAULT_SAMPLE_SIZE     = 2048;

    public static final int      SAMPLE_TYPE_EIGHT_BIT   = 1;
    public static final int      SAMPLE_TYPE_SIXTEEN_BIT = 2;
    public static final int      CHANNEL_MODE_MONO       = 1;
    public static final int      CHANNEL_MODE_STEREO     = 2;
    //
    public static final float    MAX_VALUE_8BIT          = 128.0f;
    public static final float    MAX_VALUE_16BIT         = 32767.0f;
    //
    AudioInputStream             ais;
    AudioFormat                  baseFormat;
    //    private Object           readWriteLock           = new Object();

    private SourceDataLine       sourceDataLine;

    //by default, it means the sample number extracted from audio data each time

    private int                  bufferSize;
    //    int sampleRate;
    //    int frameSize;

    private byte []              audioDataBuffer;

    //    private int []           leftFreq;
    //    private int []           rightFreq;
    private float []             left;
    private float []             right;
    private float []             combined;

    //    private int              position;
    //    private long             offset;

    //    private StaticAnalyzier                     signalProcessor;

    //    private ArrayList<KJDigitalSignalProcessor> dsps                    = new ArrayList<KJDigitalSignalProcessor>();
    private int                  sampleSize;
    private int                  sampleType;
    private int                  channelMode;

    AbstractFFT                  fft;

    //set DSP
    List<DigitalSignalProcessor> dsps;

    public static SerialAudioConsumer createInstance(SourceDataLine pSdl, AudioInputStream ais, AbstractFFT fft) {
        if (pSdl == null || ais == null || fft == null) {
            return null;
        }
        return new SerialAudioConsumer(pSdl, ais, fft);

    }

    /**
     * @param pSampleSize The sample size to extract from audio data sent to the
     *            SourceDataLine.
     * @param pFramePerSecond The desired refresh rate per second of registered
     *            DSP's.
     * @param pSampleType The sample type SAMPLE_TYPE_EIGHT_BIT or
     *            SAMPLE_TYPE_SIXTEEN_BIT.
     * @param pFramePerSecond The channel mode CHANNEL_MODE_MONO or
     *            CHANNEL_MODE_STEREO.
     */
    private SerialAudioConsumer(SourceDataLine pSdl, AudioInputStream ais, AbstractFFT fft) {
        this.fft = fft;
        sourceDataLine = pSdl;
        this.ais = ais;
        baseFormat = pSdl.getFormat();
        //the buffer size should be the multiple of frame size
        bufferSize = sourceDataLine.getBufferSize();
        sampleSize = sourceDataLine.getBufferSize() / sourceDataLine.getFormat().getFrameSize();

        // -- Allocate double the memory than the SDL to prevent
        //    buffer overlapping.
        //        audioDataBuffer = new byte[sourceDataLine.getBufferSize() << 1];
        audioDataBuffer = new byte[bufferSize];
        System.out.println("---ss:" + sampleSize);
        //
        //
        left = new float[sampleSize];
        right = new float[sampleSize];
        combined = new float[sampleSize];
        //        leftFreq = new int[sampleSize];
        //        rightFreq = new int[sampleSize];
        //
        //set channel        
        channelMode = sourceDataLine.getFormat().getChannels();

        //////////////////////////////////////////////
        //16bit        
        //                    int expFftSize = baseFormat.getSampleSizeInBits();
        int expFftSize = sourceDataLine.getFormat().getSampleSizeInBits();

        sampleType = SAMPLE_TYPE_SIXTEEN_BIT;
        switch (expFftSize) {
            case 8:
                sampleType = SAMPLE_TYPE_EIGHT_BIT;
                break;
            case 16:
                sampleType = SAMPLE_TYPE_SIXTEEN_BIT;
                break;
        }
        System.out.println("sampleType:" + sampleType);
        //
        //
        //        fft = new KJFFT(sampleSize);
        //        fft=new FFT();

        dsps = new LinkedList<DigitalSignalProcessor>();

    }

    public void addDSP(DigitalSignalProcessor dsp) {
        dsps.add(dsp);

    }

    //
    //    public StaticDSPAudioDataConsumer(SourceDataLine pSdl, AudioInputStream ais) {
    //        this(pSdl, ais, pSdl.getBufferSize() / pSdl.getFormat().getFrameSize());
    //    }

    //    public void start() {
    //        start(sampleSize);
    //    }
    public void start(int readSampleSizeEachTime) {
        start(readSampleSizeEachTime, 0.0f, Float.MAX_VALUE);

    }

    /**
     * Start monitoring the specified SourceDataLine.
     * 
     * @param pSdl A SourceDataLine.
     */
    public void start(int readSampleSizeEachTime, float timeBegin, float timeEnd) {
        if (sourceDataLine == null) {
            return;
        }
        //Control the accuracy
        if (readSampleSizeEachTime * baseFormat.getFrameSize() > bufferSize || readSampleSizeEachTime <= 0) {
            readSampleSizeEachTime = bufferSize / baseFormat.getFrameSize();
        }

        //        position = 0;
        //        offset = 0;

        //won't play
        //        sourceDataLine.start();
        //logic control
        boolean stopped = false;
        boolean threadExit = false;
        int inBytes = 0;
        int totalBytes = 0;
        int totalSample = 0;
        //        float totalTime = 0;
        float time;

        int tmp = 0;
        int max = 6;
        int readBufferSize = baseFormat.getFrameSize() * readSampleSizeEachTime;

        float freqRatio = baseFormat.getSampleRate() / (float)fft.getSampleSizeForFFT();
        //        float saBands[] = new float[10];
        while ((inBytes != -1) && (!stopped) && (!threadExit)) {
            try {

                //use buffer size here, not sample size
                inBytes = ais.read(audioDataBuffer, 0, readBufferSize);
                //                System.out.println(Arrays.toString(audioDataBuffer));
                //                int c=0;
                //                for(byte b:audioDataBuffer){
                //                    if(b>0){
                //                        ++c;
                //                    }
                //                }
                //                System.out.println(c);
                //                System.out.println("inBytes:" + inBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (inBytes >= 0) {
                totalBytes += inBytes;
                totalSample += (inBytes / baseFormat.getFrameSize());
                time = totalSample / baseFormat.getSampleRate();
                //                System.out.println("time:" + time);
                if (time < timeBegin) {
                    continue;
                }
                if (time > timeEnd) {
                    break;
                }
                //
                //

                //use normalized and caclulate db
                processSamples(true);
                float [] fftResults = fft.calculateMagnitude(left);
                //
                //
                //                processSamples(false);
                //                float [] fftResults = fft.calculateWithRealOutput(left);

                //                                System.out.println(Arrays.toString(left));

                if (fftResults != null) {

                    //                    System.out.println(fftResults.length);
                    //                    System.out.println("fft result:" + Arrays.toString(fftResults));
                    for (DigitalSignalProcessor dsp : dsps) {
                        dsp.process(fftResults, fftResults, time, freqRatio);
                    }

                    //                    //the freq will be a const if all values are equals
                    //                    Tool.sortFreqForFFT(fftResults, indexResult);

                    //                    System.out.println("sorted fft result:" + Arrays.toString(fftResults));
                    //                    System.out.println("sorted freq result:"
                    //                            + Tool.intArrayToStringReverse(indexResult));

                }
                //                System.out.println();
                //TODO for test
                //                tmp++;
                //                if (tmp > max)
                //                    break;

            }
        }
        System.out.println("total bytes:" + totalBytes);
        System.out.println("total sample:" + totalSample);
        System.out.println("total time:" + totalSample / baseFormat.getSampleRate());
        for (DigitalSignalProcessor dsp : dsps) {
            dsp.afterAllProccesed();
        }

    }

    public static float [] stereoMerge(float [] pLeft, float [] pRight) {

        for (int a = 0; a < pLeft.length; a++) {
            pLeft[a] = (pLeft[a] + pRight[a]) / 2.0f;
        }

        return pLeft;

    }

    public static void parseSampleData(float [] result, int actualResultSize, byte [] audioDataBuffer, int channelMode,
            int sampleType) {
        int c = 0;
        if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

            for (int a = 0; a < actualResultSize; a++) {
                result[a] = audioDataBuffer[a];
                result[a] /= MAX_VALUE_8BIT;
            }

        } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

            for (int a = 0; a < actualResultSize; a++) {
                c = a << 1;
                result[a] = (float)((((int)audioDataBuffer[c]) >> 1) + (((int)audioDataBuffer[c + 1]) >> 1));
                result[a] /= MAX_VALUE_8BIT;
            }

        } else if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

            for (int a = 0; c < actualResultSize; a++, c += 2) {
                result[a] = (float)((((int)audioDataBuffer[c + 1]) << 8) + audioDataBuffer[c]);
                result[a] /= MAX_VALUE_16BIT;
            }

        } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

            for (int a = 0; a < actualResultSize; a++) {
                //two bytes for one channel,32767 is the max value of 16bit format
                c = a << 2;
                result[a] = (((((int)audioDataBuffer[c + 1]) << 8) | (((int)audioDataBuffer[c]) & 0xff))
                        + (((int)audioDataBuffer[c + 3]) << 8) | (((int)audioDataBuffer[c + 2]) & 0xff)) >> 1;
                result[a] /= MAX_VALUE_16BIT;
            }
            //            System.out.println();

        }

    }

    /**
     * original one
     */

    private void processSamples(boolean isNormalized) {

        int c = 0;
        if (isNormalized) {
            if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

                for (int a = 0; a < sampleSize; a++, c++) {

                    left[a] = (float)((int)audioDataBuffer[c] / MAX_VALUE_8BIT);
                    right[a] = left[a];

                }

            } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 2) {

                    left[a] = (float)((int)audioDataBuffer[c] / MAX_VALUE_8BIT);
                    right[a] = (float)((int)audioDataBuffer[c + 1] / MAX_VALUE_8BIT);

                }

            } else if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 2) {

                    left[a] = (float)(((int)audioDataBuffer[c + 1] << 8) + audioDataBuffer[c]) / MAX_VALUE_16BIT;
                    right[a] = left[a];

                }

            } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 4) {

                    left[a] = (float)(((int)audioDataBuffer[c + 1] << 8) + audioDataBuffer[c]) / MAX_VALUE_16BIT;
                    right[a] = (float)(((int)audioDataBuffer[c + 3] << 8) + audioDataBuffer[c + 2]) / MAX_VALUE_16BIT;

                }

            }
        } else {
            if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

                for (int a = 0; a < sampleSize; a++, c++) {

                    left[a] = (float)((int)audioDataBuffer[c]);
                    right[a] = left[a];

                }

            } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_EIGHT_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 2) {

                    left[a] = (float)((int)audioDataBuffer[c]);
                    right[a] = (float)((int)audioDataBuffer[c + 1]);

                }

            } else if (channelMode == CHANNEL_MODE_MONO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 2) {

                    left[a] = (float)(((int)audioDataBuffer[c + 1] << 8) + audioDataBuffer[c]);
                    right[a] = left[a];

                }

            } else if (channelMode == CHANNEL_MODE_STEREO && sampleType == SAMPLE_TYPE_SIXTEEN_BIT) {

                for (int a = 0; a < sampleSize; a++, c += 4) {

                    left[a] = (float)(((int)audioDataBuffer[c + 1] << 8) + audioDataBuffer[c]);
                    right[a] = (float)(((int)audioDataBuffer[c + 3] << 8) + audioDataBuffer[c + 2]);

                }

            }
        }

    }

    public static void spectrumAnalyze(AudioInputStream ais,
            int actualSampleSize, float timeBegin, float timeEnd, Collection<DigitalSignalProcessor> dsps) {
        AudioFormat baseFormat = ais.getFormat();
        int sampleRate = (int)baseFormat.getSampleRate();
        //////////////////////////////////////////
        //max sample size value
        int sampleSizeWithPadding = sampleRate;
        System.out.println(Tool.getTimeBySampleSize(sampleSizeWithPadding, sampleRate));
        int exp = Tool.getExpOf2(sampleSizeWithPadding);

        SourceDataLine sourceDataLine = Tool.getLineWithSampleSize(baseFormat, sampleSizeWithPadding);
        Tool.printSourceDataLine(sourceDataLine);

        long start = System.currentTimeMillis();
        //use miny to calc faster
        FFT fft1 = new FFT(exp);
        //        KJFFT fft1 = new KJFFT(exp);
        //        fft1.setRealOutput(true);
        AbstractFFT fft = fft1;
        fft.setValve(0);
        SerialAudioConsumer consumer = SerialAudioConsumer.createInstance(sourceDataLine, ais, fft);
        for (DigitalSignalProcessor dsp : dsps) {
            consumer.addDSP(dsp);
        }
        if (actualSampleSize > baseFormat.getSampleRate()) {
            actualSampleSize = (int)baseFormat.getSampleRate();
        }
        consumer.start(actualSampleSize, timeBegin, timeEnd);
        System.out.println("accuracy:" + baseFormat.getSampleRate() / actualSampleSize + "hz");
        System.out.println(System.currentTimeMillis() - start);
        //        frame.invalidate();
    }
}
