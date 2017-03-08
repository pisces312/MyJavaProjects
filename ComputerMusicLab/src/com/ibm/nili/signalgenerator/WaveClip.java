package com.ibm.nili.signalgenerator;

import java.util.Arrays;

import com.ibm.nili.common.Tool;

/**
 * CD激光唱盘采样频率为44KHz,可记录的最高音频为22KHz
 * 
 * @author pisces312
 * 
 */
public class WaveClip {
    public static final int CD_SAMPLERATE    = 44100;
    public static final int DVD_SAMPLERATE   = 48000;

    boolean                 isBigIndian      = false;
    public int              sampleSizeInBits = 8;
    public double           maxValue;
    public double           minValue;
    //4bytes for each signal value
    private float []        sampleBuffer;
    //xxx for signal merge
    //    private double []       volumns;
    public int              offset;
    /////////////////////////////////
    //use int sampeRate here
    public int              sampleRate;
    public int              sampleSize;
    //        public double    maxA;
    //calc by sampleSize/sampleRate, unit: second
    public double           duration;

    public byte []          byteData;
    public int              waveDataByteSize;

    //may have multiple frequence
    //        public double    hz;

    //    public WaveClip(double time, float sampleRate) {
    //        this(time, sampleRate, 8, null, -1);
    //    }

    public WaveClip(double time, int sampleRate, int sampleSizeInBits) {
        this(time, sampleRate, sampleSizeInBits, null, -1);
    }

    //    public WaveClip(int sampleSize, float sampleRate) {
    //        this(sampleSize, sampleRate, 8, null, -1);
    //    }

    public WaveClip(int sampleSize, int sampleRate, int sampleSizeInBits) {
        this(sampleSize, sampleRate, sampleSizeInBits, null, -1);
    }

    public WaveClip(double time, int sampleRate, int sampleSizeInBits, byte [] results, int offset) {
        sampleSize = getSampleSize(time, sampleRate);
        init(time, sampleSize, sampleRate, sampleSizeInBits, results, offset);
    }

    public WaveClip(int sampleSize, int sampleRate, int sampleSizeInBits, byte [] results, int offset) {

        init(getDuration(sampleSize, sampleRate), sampleSize, sampleRate, sampleSizeInBits, results, offset);
    }

    public void revese() {
        Tool.reverse(sampleBuffer);
        switch (sampleSizeInBits) {
            case 8:
                Tool.reverse(byteData);
                break;
            case 16:
                for (int i = offset, len = waveDataByteSize + offset, j = 0; i < len; i += 2, ++j) {
                    byteData[i] = (byte)(((int)sampleBuffer[j]) & 0xff);
                    byteData[i + 1] = (byte)(((int)sampleBuffer[j]) >> 8);
                }
                break;
        }
    }

    public void resetData() {
        Arrays.fill(byteData, offset, sampleSize, (byte)0);
        Arrays.fill(sampleBuffer, 0);
        //        Arrays.fill(volumns, 0);
    }

    public static double getDuration(int sampleSize, float sampleRate) {
        return sampleSize / (double)sampleRate;
    }

    public static int getSampleSize(double time, float sampleRate) {
        return (int)(time * sampleRate);
    }

    public float [] getRawData() {
        //        switch (sampleSizeInBits) {
        //            case 8:
        //                return sampleBuffer;
        //            case 16:{
        //                
        //            }
        //                break;
        return sampleBuffer;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    /**
     * 16bit sound card accuracy
     * 
     * @param time
     * @param sampleSize
     * @param sampleRate
     * @param sampleSizeInBits
     * @param results
     * @param offset
     */
    private void init(double time, int sampleSize, int sampleRate, int sampleSizeInBits, byte [] results, int offset) {
        this.duration = time;
        this.sampleRate = sampleRate;
        this.sampleSize = sampleSize;
        this.sampleSizeInBits = sampleSizeInBits;
        minValue = getMinValue(sampleSizeInBits);
        maxValue = getMaxValue(sampleSizeInBits);
        waveDataByteSize = sampleSize * (sampleSizeInBits >> 3);
        if (results == null || offset < 0 || (results != null && offset > results.length - 1)) {
            this.byteData = new byte[waveDataByteSize];
            this.offset = 0;
        } else {
            this.offset = offset;
            this.byteData = results;
        }
        sampleBuffer = new float[sampleSize];
        //        volumns = new double[sampleSize];
        //at last initial data
        resetData();
    }

    public static double getMaxValue(int sampleSizeInBits) {
        return getMaxA(sampleSizeInBits);
    }

    public static int getMaxA(int sampleSizeInBits) {
        return (1 << (sampleSizeInBits - 1)) - 1;
    }

    public static double getMinValue(int sampleSizeInBits) {
        return getMinA(sampleSizeInBits);
    }

    public static int getMinA(int sampleSizeInBits) {
        return -(getMaxA(sampleSizeInBits) + 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sampleRate:" + sampleRate + "; ");
        sb.append("sampleSize:" + sampleSize + "; ");
        sb.append("maxValue:" + maxValue + "; ");
        sb.append("minValue:" + minValue + "; ");
        sb.append("offset:" + offset + "; ");
        sb.append("duration:" + duration + "; ");
        sb.append("waveDataSize:" + waveDataByteSize + "; ");
        sb.append("volume dynamic range:" + getVolumeDynamicRange(sampleSizeInBits) + "; ");

        //            sb.append("accuracy:" + sampleRate/(double)sampleSize + "; ");            
        return sb.toString();
    }

    //        public double[] getData(){
    //            
    //        }
    //        public void setData(double[] results, int offset){
    //            this.results=results;
    //            this.offset=offset;
    //        }
    /**
     * w=2pi/T
     * k=1/T
     * 
     * @param clip
     * @param hz
     */

    //    y = A*sin ( 2ft) + D
    public static void addSineSignalByFreq(WaveClip clip, double hz, double maxA) {
        clip.generateSineSignal(hz, maxA, true);
    }

    /**
     * not accurracy
     * 
     * @param sampleRate
     * @param hz
     * @param phase
     * @return
     */
    public static double [] createSinTable(int sampleRate, double hz, double phase) {
        double [] sinTable = new double[sampleRate];
        double c = 2 * Math.PI * hz / (double)sampleRate;
        //        double dt = 1 / (double)sampleRate;
        for (int i = 0; i < sinTable.length; ++i) {
            sinTable[i] = Math.sin(c * i + phase);
        }
        return sinTable;
    }

    /**
     * 使用三角函数表
     * 
     * TODO 信号合成的音量?
     * 
     * @param hz
     * @param maxVol
     * @param isMerge
     */
    public void generateSineSignal(double hz, double maxVol, boolean isMerge) {
        if (maxVol <= 0 || maxVol > maxValue) {
            return;
        }
        ////not use sin table
        double w = 2 * Math.PI * hz / (double)sampleRate;
        switch (sampleSizeInBits) {
            case 8:
                for (int i = offset, len = waveDataByteSize + offset; i < len; ++i) {
                    sampleBuffer[i] += (float)(maxVol * Math.sin(w * i));
                    byteData[i] += (byte)(sampleBuffer[i]);

                }
                break;
            case 16:
                //TODO
                for (int i = offset, len = waveDataByteSize + offset, j = 0; i < len; i += 2, ++j) {
                    sampleBuffer[j] += (float)(maxVol * Math.sin(w * j));
                    byteData[i] = (byte)(((int)sampleBuffer[j]) & 0xff);
                    byteData[i + 1] = (byte)(((int)sampleBuffer[j]) >> 8);
                }
                break;
        }
        //
        //
        //        double [] sinTable = createSinTable(sampleRate, hz, 0);
        //        if (isMerge) {
        //            switch (sampleSizeInBits) {
        //                case 8:
        //                    //i stands for byte size iterator
        //                    for (int i = offset, len = waveDataByteSize + offset; i < len; ++i) {
        //                        signalBuffer[i] += (float)(maxVol * sinTable[i % sampleRate]);
        //                        //                        signalBuffer[i] = (float)Math.sqrt(signalBuffer[i] * signalBuffer[i] + maxVol
        //                        //                                * sinTable[i % sampleRate] * maxVol * sinTable[i % sampleRate]);
        //                        //                    if (signalBuffer[i] > maxVol) {
        //                        //                        signalBuffer[i] = (float)maxVol;
        //                        //                    } else if (signalBuffer[i] <= -maxVol) {
        //                        //                        signalBuffer[i] = (float)-maxVol;
        //                        //                    }
        //                        waveData[i] = (byte)(signalBuffer[i]);
        //
        //                    }
        //                    break;
        //                case 16:
        //                    //left[a] = (float)(((int)audioDataBuffer[c + 1] << 8) + audioDataBuffer[c]);
        //                    for (int i = offset, len = waveDataByteSize + offset, j = 0; i < len; i += 2, ++j) {
        //                        signalBuffer[j] += (float)(maxVol * sinTable[j % sampleRate]);
        //                        //                        signalBuffer[j] = (float)Math.sqrt(signalBuffer[j] * signalBuffer[j] + maxVol
        //                        //                                * sinTable[j % sampleRate] * maxVol * sinTable[j % sampleRate]);
        //                        //                    if (signalBuffer[i] > maxVol) {
        //                        //                        signalBuffer[i] = (float)maxVol;
        //                        //                    } else if (signalBuffer[i] <= -maxVol) {
        //                        //                        signalBuffer[i] = (float)-maxVol;
        //                        //                    }
        //                        waveData[i] = (byte)(((int)signalBuffer[j]) & 0xff);
        //                        waveData[i + 1] = (byte)(((int)signalBuffer[j]) >> 8);
        //                    }
        //                    break;
        //            }
        //        } else {
        //            switch (sampleSizeInBits) {
        //                case 8:
        //                    for (int i = offset, len = waveDataByteSize + offset; i < len; ++i) {
        //                        signalBuffer[i] = (float)(maxVol * sinTable[i % sampleRate]);
        //                        waveData[i] = (byte)(signalBuffer[i]);
        //                    }
        //                    break;
        //                case 16:
        //                    for (int i = offset, len = waveDataByteSize + offset, j = 0; i < len; i += 2, ++j) {
        //                        signalBuffer[j] = (float)(maxVol * sinTable[j % sampleRate]);
        //                        waveData[i] = (byte)(((int)signalBuffer[j]) & 0xff);
        //                        waveData[i + 1] = (byte)(((int)signalBuffer[j]) >> 8);
        //                    }
        //                    break;
        //            }
        //        }
    }

    public void addSineSignalByFreq(double hz) {
        generateSineSignal(hz, maxValue, true);
    }

    public void addSineSignalByFreq(double hz, double maxValue) {
        generateSineSignal(hz, maxValue, true);
    }

    public static void setSignalByFreq(WaveClip clip, double [] hz, double maxVol) {
        clip.resetData();
        addSignalByFreq(clip, hz, maxVol);
    }

    public void setSignalByFreq(double hz) {
        resetData();
        generateSineSignal(hz, maxValue, false);
    }

    public void setSignalByFreq(double hz, double maxVol) {
        resetData();
        generateSineSignal(hz, maxVol, false);
    }

    /**
     * for merge multiple signals averagely, use average vol for each signal
     * 
     * @param hz
     */
    //    public static void addSignalByFreq(WaveClip clip, double [] hz) {
    //        addSignalByFreq(clip, hz, clip.maxValue);
    //    }

    public static void addSignalByFreq(WaveClip clip, double [] hz, double maxVol) {
        double vol = maxVol / hz.length;
        for (double k : hz) {
            addSineSignalByFreq(clip, k, vol);
        }
    }

    //    public void mergeSignalByFreq(double hz) {
    //        addSignalByFreq(this, hz, this.maxValue);
    //    }

    public static double [] getModuleOfFFT(double [] real, double [] imag) {
        if (real == null || imag == null) {
            return null;
        }
        if (real.length != imag.length) {
            return null;
        }
        double MINY = 0;
        double sinv = MINY;
        double cosv = -MINY;
        int halfSize = real.length >> 1;
        double [] results = new double[halfSize];
        for (int i = halfSize; i != 0; i--) {
            double tmpr = real[i];
            double tmpi = imag[i];
            if (tmpr > cosv && tmpr < sinv && tmpi > cosv && tmpi < sinv)
                results[i - 1] = 0;
            else
                results[i - 1] = tmpr * tmpr + tmpi * tmpi;
        }
        return results;
    }
    //TODO
    public void appendSineSignal(double hz){
        
    }

    /**
     * TODO
     * 常用音量来描述音强，以分贝（dB=20log）为单位
     */
    /**
     * TODO
     * 绝对强度可以放大，但其相对强度更有意义，一般用动态范围定义： 动态范围=20×log（信号的最大强度 / 信号的最小强度） （dB）
     */
    public static double getVolumeDynamicRange(int bit) {
        return 20 * Math.log10(1 << bit);
    }

    /**
     * 一般乐器发出的音都不是纯频率的音，而是由好多谐波（harmonic）组成的；其中频率最低的那个通常最强，叫做基音。
     * 
     * 
     * 比如小提琴发出音高 A4 的音，指的就是其基音是 440 Hz，而声波频谱里面同时有二次谐波 880 Hz、三次谐波 1320
     * Hz、四次谐波……等等。不同乐器发出的声音，其谐波强度分布往往完全不同，因此音色（timbre）也就不同（比如高谐波强的话听起来就亮一些）。
     * 
     * 
     * 临界频宽在低频区是 100 Hz 左右；高频区大约是本身频率的 1/6
     * 
     * @param args
     */
    public void generateHarmonic1(double hz) {
        //TODO
        /**
         * n×ωO 称为ωO 的高次谐波分量，也称为泛音。音色是由混入基音的泛音所决定的，高次谐波越丰富,音色就越有明亮感和穿透力。
         */
        double lowestF = 27.5;

        //        double[] fs=new double[]

        //        addSinSignalByFreq(hz, maxVol);

    }
}
