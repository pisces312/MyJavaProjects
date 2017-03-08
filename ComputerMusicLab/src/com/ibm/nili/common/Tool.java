package com.ibm.nili.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ibm.nili.signalgenerator.WaveClip;

public class Tool {

    public static void writeAudioFile(float [] sampleData, String fileName, int channels,
            int sampleRate, int sampleSizeInBits) {
        //        this.sampleData = sampleData;
        int duration = sampleData.length;
        int sampleSize = sampleSizeInBits / 8;
        AudioFileFormat.Type fileType = null;
        boolean bigEndian;
        // choose file type from file name
        if (fileName.endsWith(".au")) {
            fileType = AudioFileFormat.Type.AU;
            bigEndian = true;
        } else if (fileName.endsWith(".wav")) {
            fileType = AudioFileFormat.Type.WAVE;
            bigEndian = false;
        } else if (fileName.endsWith(".aif") || fileName.endsWith(".aiff")) {
            fileType = AudioFileFormat.Type.AIFF;
            bigEndian = true;
        } else { // default
            fileName = fileName + ".au";
            bigEndian = true;
        }
        // set up
        File file = new File(fileName);
        // convert floats to bytes
        byte [] tmp = new byte[sampleData.length * sampleSize];
        for (int i = 0; i < sampleData.length; i++) {
            int ival = -1;
            switch (sampleSize) {
                case 1: // 8 bit
                    tmp[i] = new Float(sampleData[i]).byteValue();
                    break;
                case 2: // 16 bit
                    short sval = new Float(sampleData[i]).shortValue();
                    if (bigEndian) {
                        tmp[i * 2] = (byte)((sval & 0x0000ff00) >> 8);
                        tmp[i * 2 + 1] = (byte)(sval & 0x000000ff);
                    } else {
                        tmp[i * 2] = (byte)(sval & 0x000000ff);
                        tmp[i * 2 + 1] = (byte)((sval & 0x0000ff00) >> 8);
                    }
                    break;
                case 3: // 24 bit
                    ival = new Float(sampleData[i]).intValue();
                    if (bigEndian) {
                        tmp[i * 3] = (byte)((ival & 0x00ff0000) >> (8 * 2));
                        tmp[i * 3 + 1] = (byte)((ival & 0x0000ff00) >> 8);
                        tmp[i * 3 + 2] = (byte)(ival & 0x000000ff);
                    } else {
                        tmp[i * 3] = (byte)(ival & 0x000000ff);
                        tmp[i * 3 + 1] = (byte)((ival & 0x0000ff00) >> 8);
                        tmp[i * 3 + 2] = (byte)((ival & 0x00ff0000) >> (8 * 2));
                    }
                    break;
                case 4: // 32 bit
                    ival = new Float(sampleData[i]).intValue();
                    if (bigEndian) {
                        tmp[i * 4] = (byte)((ival & 0xff000000) >> (8 * 3));
                        tmp[i * 4 + 1] = (byte)((ival & 0x00ff0000) >> (8 * 2));
                        tmp[i * 4 + 2] = (byte)((ival & 0x0000ff00) >> 8);
                        tmp[i * 4 + 3] = (byte)(ival & 0x000000ff);
                    } else {
                        tmp[i * 4] = (byte)(ival & 0x000000ff);
                        tmp[i * 4 + 1] = (byte)((ival & 0x0000ff00) >> 8);
                        tmp[i * 4 + 2] = (byte)((ival & 0x00ff0000) >> (8 * 2));
                        tmp[i * 4 + 3] = (byte)((ival & 0xff000000) >> (8 * 3));
                    }
                    break;
                default:
                    System.err.println("jMusic AudioFileOut error: " +
                            sampleSizeInBits +
                            " bit audio output file format not supported, sorry :(");
                    return;
            }
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(tmp);
        // specify file format
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, true, bigEndian);
        AudioInputStream ais = new AudioInputStream(bis, format, duration / channels);
        // writing
        try {
            AudioSystem.write(ais, fileType, file);
        } catch (IOException ioe) {
            System.out.println("error writing audio file.");
        }
    }

    public static void reverse(float [] array) {
        float tmp = 0;
        int c = array.length >> 1;
        for (int i = 0; i < c; ++i) {
            tmp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tmp;
        }
    }

    public static void reverse(byte [] array) {
        byte tmp = 0;
        int c = array.length >> 1;
        for (int i = 0; i < c; ++i) {
            tmp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tmp;
        }
    }

    public static class FreqRange {
        public double freq1;
        public double freq2;

        public FreqRange(double freq1, double freq2) {
            this.freq1 = freq1;
            this.freq2 = freq2;
        }
    }

    /**
     * 如果谐波不重合但是距离很近，它们就会干涉形成低频率的拍（beat），这种低频拍音嗡嗡作响，非常难听。
     * 两个频率距离多近才会形成不好听的拍？人们一般把这个临界距离叫做临界频宽（critical
     * bandwidth），处于临界频宽之内的两个频率就会互相干涉。这个临界频宽本身是频率的函数，频率越高，临界频宽带也就越宽
     */
    public static FreqRange getCriticalBandwidthRange(double f) {
        return new FreqRange(f - 100, f * 7 / 6);
    }

    public static WaveClip playData(double hz) {
        return playData(hz, 1);
    }

    public static WaveClip playData(double hz, double time) {

        WaveClip clip = new WaveClip(time, WaveClip.DVD_SAMPLERATE, 16);
        //        WaveClip clip = new WaveClip(time, 44100, 16);
        clip.setSignalByFreq(hz);
        playData(clip.sampleRate, clip.sampleSizeInBits, clip.byteData);
        return clip;
    }

    public static void playData(WaveClip clip) {
        playData(clip.sampleRate, clip.sampleSizeInBits, clip.byteData);
    }

    public static void playData(List<WaveClip> clips) {
        if (clips == null || clips.isEmpty()) {
            return;
        }
        AudioFormat audioFormat = new AudioFormat(clips.get(0).sampleRate, clips.get(0).sampleSizeInBits, 1, true,
                false);

        DataLine.Info out_dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = null;
        long start = 0;
        long end = 0;
        try {
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(out_dataLine_info);
            sourceDataLine.open(audioFormat);
            //doesn't suppor volume control
            sourceDataLine.start();
            for (WaveClip clip : clips) {
                start = System.currentTimeMillis();
                sourceDataLine.write(clip.byteData, 0, clip.byteData.length);
                //don't do flush here
                //            sourceDataLine.flush();

                end = System.currentTimeMillis();
            }
            //blocks in another thread until all the data has been played
            sourceDataLine.drain();
            System.out.println((end - start) / 1000.0f);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (sourceDataLine != null) {
                //place stop at last!!!
                sourceDataLine.stop();
                sourceDataLine.close();
                sourceDataLine = null;
            }
        }
    }

    public static void playData(float sampleRate, int sampleSizeInBits, byte [] data) {
        //audioFormat = new AudioFormat(8000.0f,8,1,true,false);
        AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, 1, true, false);
        playData(audioFormat, data);
    }

    public static void playData(AudioFormat audioFormat, byte [] data) {
        DataLine.Info out_dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = null;
        long start = 0;
        long end = 0;
        try {
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(out_dataLine_info);
            sourceDataLine.open(audioFormat);
            //doesn't suppor volume control
            sourceDataLine.start();
            start = System.currentTimeMillis();
            sourceDataLine.write(data, 0, data.length);
            //don't do flush here
            //            sourceDataLine.flush();
            //blocks in another thread until all the data has been played
            sourceDataLine.drain();
            end = System.currentTimeMillis();
            System.out.println((end - start) / 1000.0f);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (sourceDataLine != null) {
                //place stop at last!!!
                sourceDataLine.stop();
                sourceDataLine.close();
                sourceDataLine = null;
            }
        }
    }

    public static float getMusicTimeinSecond(AudioInputStream ais) {
        if (ais == null) {
            return 0f;
        }
        AudioFormat format = ais.getFormat();
        return ais.getFrameLength() / format.getFrameRate();
    }

    public static double getNormalizedDataFromDB(double db) {
        return Math.pow(10, db / 10);
    }

    public static double getDBFromNormalizedData(float normalizedMagnitude) {
        return getDBFromAmplitude(normalizedMagnitude);

    }

    /**
     * TODO ??
     * 
     * @param A
     * @return
     */
    public static double getDBFromAmplitude(float A) {
        return 20 * Math.log10(A);

    }

    public static AudioInputStream getAudisoInputStream(String fileToPlay) {
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(new File(fileToPlay));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ais;
    }

    /**
     * 
     * 0 is error
     * 
     * the scale number may not be devided by 2 each time
     * 
     * @param d
     * @param len
     * @return
     */
    //
    public static int calcScaleNumber(double d, double len) {

        if (d == 0) {
            return 0;
        }
        if (d > len) {
            return 0;
        }
        int n1 = (int)Math.ceil(len / d);
        if (n1 == 2 || n1 == 1) {
            return 2;
        }
        //        if (n1 % 2 == 0) {
        //            return n1 + 1;
        //        }
        return n1 + 1;
    }

    /**
     * devided by 2 each time
     * 
     * @param n
     * @return
     */
    public static int getFitScaleNumber(int n) {
        int i = 1;
        int a = 2;
        while (n > a) {
            a += i;
            i <<= 1;
        }
        return a;
    }

    //calc scale
    public static double [] calcScale(double d, double start, double end) {
        double len = end - start;
        int n = calcScaleNumber(d, len);
        n = getFitScaleNumber(n);
        if (n == 0) {
            return null;
        }
        //n must be 2 or odd
        double [] scale = new double[n];
        scale[0] = start;
        scale[n - 1] = end;
        if (n == 2) {
            return scale;
        }
        //
        double h = len / 2;
        double v;
        int ti = n / 2;
        while (ti > 0) {
            v = h + start;
            for (int i = ti; i < scale.length; i += ti) {
                scale[i] = v;
                v += h;
            }
            ti = ti / 2;
            h = h / 2;

        }

        return scale;

    }

    public static SourceDataLine getLineWithSampleSize(AudioFormat audioFormat, int sampleSize) {
        if (audioFormat == null) {
            return null;
        }
        return getLine(audioFormat, audioFormat.getFrameSize() * sampleSize);
    }

    /**
     * The buffer size is specified in bytes, but must represent an integral
     * number of sample frames.
     * 
     * @param audioFormat
     * @param bufferSize
     * @return
     */
    public static SourceDataLine getLine(AudioFormat audioFormat, int bufferSize) {
        if (audioFormat == null) {
            return null;
        }
        if (bufferSize > audioFormat.getFrameSize() * audioFormat.getSampleRate()) {
            System.err.println("The buffer size must no larger than frameSize*sampleRate ("
                    + audioFormat.getFrameSize() * audioFormat.getSampleRate() + ")");
        }
        System.out.println("buffer size " + bufferSize + " to set");
        if (bufferSize % audioFormat.getFrameSize() != 0) {
            System.err.println("The buffer size is specified in bytes, but must represent an integral number of sample frames.");
            return null;
        }
        SourceDataLine res = null;
        try {
            res = AudioSystem.getSourceDataLine(audioFormat);
            res.open(audioFormat, bufferSize);
        } catch (Exception e) {}
        return res;

    }

    public static float getTimeBySampleSize(int sampleSize, float sampleRate) {
        return sampleSize / sampleRate;
    }

    //SampleRate * NumChannels * BitsPerSample/8
    public static int getByteRate(AudioFormat baseFormat) {
        return getBitRate(baseFormat) >> 3;
    }

    /**
     * such as 1411.2kBps
     * 
     * @param baseFormat
     * @return
     */
    public static int getBitRate(AudioFormat baseFormat) {
        return ((int)(baseFormat.getSampleRate() * baseFormat.getChannels() * baseFormat.getSampleSizeInBits()));
    }

    public static void printAudioFormat(AudioFormat baseFormat) {
        System.out.println("-------Audio Format Info------");
        System.out.println("Format:" + baseFormat);
        System.out.println("Channel:" + baseFormat.getChannels());
        System.out.println("Sample bit:" + baseFormat.getSampleSizeInBits());
        System.out.println("Sample rate:" + baseFormat.getSampleRate());
        System.out.println("Frame rate:" + baseFormat.getFrameRate());
        System.out.println("Frame size:" + baseFormat.getFrameSize());
        System.out.println("Bit rate:" + getBitRate(baseFormat) / 1000.0 + "kbps");
        System.out.println("-------Audio Format Properties------");

        Map<String, Object> props = baseFormat.properties();
        for (Entry<String, Object> prop : props.entrySet()) {
            System.out.println(prop.getKey() + ":" + prop.getValue());
        }
        //        if (props.containsKey("duration")){
        //            maxTimeEnd = (long) Math.round((((Long) props.get("duration"))
        //                    .longValue())/1000);
        //        }else{
        //            //from file size
        //
        //        }

    }

    public static void printSourceDataLine(SourceDataLine sourceDataLine) {
        AudioFormat baseFormat = sourceDataLine.getFormat();
        printAudioFormat(baseFormat);
        System.out.println("-------Line Info------");
        System.out.println("Buffer byte size:" + sourceDataLine.getBufferSize());
        //it's suggested the sampleSize larger than 1s and the accurate will be less than 1hz
        System.out.println("Sample size (BufferByteSize / FrameSize):" + sourceDataLine.getBufferSize()
                / +baseFormat.getFrameSize());

        System.out.println("-------Controls------");
        Control [] controls = sourceDataLine.getControls();
        for (Control con : controls) {
            System.out.println(con);
        }
    }

    public static int [] sortWithIndex(float [] x) {
        int result[] = new int[x.length];
        sortFreqForFFT(x, result);
        return result;

    }

    /**
     * if all values are equals, return the first index
     * 
     * @param x
     * @param result
     */
    public static void sortFreqForFFT(float [] x, int [] result) {
        if (x.length != result.length) {
            return;
        }
        //set 0hz node to 0??
        //        x[0] = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = i;

        }
        sortWithIndex(x, 0, x.length - 1, result);
        //find all equals

        for (int i = x.length - 1; i >= 0; --i) {
            if (x[i] == 0) {
                Arrays.fill(result, 0, i + 1, 0);
                break;
            }
        }

    }

    public static void sortWithIndex(float [] x, int l, int u, int [] result) {
        if (u - l < 0) {
            return;
        }
        float t = x[l];
        //
        int i = l;
        int j = u + 1;
        float temp;
        int itemp;
        for (;;) {
            do
                i++;
            while (i <= u && x[i] < t);
            do
                j--;
            while (x[j] > t);
            if (i > j)
                break;
            temp = x[i];
            x[i] = x[j];
            x[j] = temp;
            //
            itemp = result[i];
            result[i] = result[j];
            result[j] = itemp;
        }
        temp = x[l];
        x[l] = x[j];
        x[j] = temp;
        //
        itemp = result[l];
        result[l] = result[j];
        result[j] = itemp;
        sortWithIndex(x, l, j - 1, result);
        sortWithIndex(x, j + 1, u, result);
    }

    public static String intArrayToStringReverse(int [] orderedIdx) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = orderedIdx.length - 1; i > 0; --i) {
            sb.append(orderedIdx[i]);
            sb.append(",");
        }
        if (orderedIdx.length > 0)
            sb.append(orderedIdx[0]);
        sb.append("]");
        return sb.toString();
    }

    public static String floatArrayToStringByIdxReverse(int [] orderedIdx, float [] valueArray) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = orderedIdx.length - 1; i > 0; --i) {
            sb.append(valueArray[orderedIdx[i]]);
            sb.append(",");
        }
        if (orderedIdx.length > 0)
            sb.append(valueArray[orderedIdx[0]]);
        sb.append("]");
        return sb.toString();
    }

    public static int getExpOf2(int n) {
        if (n == 0) {
            return 1;
        }

        int c = 0;
        while ((n = (n >> 1)) != 0) {
            ++c;
        }
        return c;
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        //        float [] x = new float[] {22.953125f, 4.9576716f, 20.474455f, 4.050702f, 3.6974769f, 14.289971f};
        //        int result[] = sortWithIndex(x);
        //        System.out.println(Arrays.toString(x));
        //        System.out.println(Arrays.toString(result));
        System.out.println(Float.MAX_VALUE);
        System.out.println(Float.MIN_VALUE);

    }

}
