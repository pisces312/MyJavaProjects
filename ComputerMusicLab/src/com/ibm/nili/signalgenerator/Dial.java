package com.ibm.nili.signalgenerator;

import java.util.Arrays;

import com.ibm.nili.common.Tool;

/**
 * according to DTMF standard
 * 
 * the difference should be less 1.5%
 * 
 * the duration should be more than 40ms
 * 
 * @author pisces312
 * 
 */
public class Dial {
    public enum DTMFTone {

        DTMF_1(697, 1209, '1'),
        DTMF_2(697, 1336, '2'),
        DTMF_3(697, 1477, '3'),
        DTMF_A(697, 1633, 'A'),
        DTMF_4(770, 1209, '4'),
        DTMF_5(770, 1336, '5'),
        DTMF_6(770, 1477, '6'),
        DTMF_B(770, 1633, 'B'),
        DTMF_7(852, 1209, '7'),
        DTMF_8(852, 1336, '8'),
        DTMF_9(852, 1477, '9'),
        DTMF_C(852, 1633, 'C'),
        DTMF_STAR(941, 1209, '*'),
        DTMF_0(941, 1336, '0'),
        DTMF_POUND(941, 1477, '#'),
        DTMF_D(941, 1633, 'D');

        //        private static final DTMFTone [] tones = {DTMF_1, DTMF_2, DTMF_3, DTMF_A,
        //                                                       DTMF_4, DTMF_5, DTMF_6, DTMF_B,
        //                                                       DTMF_7, DTMF_8, DTMF_9, DTMF_C,
        //                                                       DTMF_STAR, DTMF_0, DTMF_POUND, DTMF_D
        //
        //                                               };
        private int [] freqs = new int[2];
        private char   number;

        private DTMFTone(int f1, int f2) {
            freqs[0] = f1;
            freqs[1] = f2;
            number = ' ';
        }

        private DTMFTone(int f1, int f2, char num) {
            freqs[0] = f1;
            freqs[1] = f2;
            number = num;
        }

        public static DTMFTone getDTMF(int f1, int f2) {
            for (DTMFTone tone : values()) {

                if (f1 == tone.freqs[0] && f2 == tone.freqs[1]) {
                    return tone;
                }
                if (f1 == tone.freqs[1] && f2 == tone.freqs[0]) {
                    return tone;
                }
            }
            return null;
        }

        //        @Override
        //        public boolean equals(Object obj) {
        //            if (!(obj instanceof DTMFTone)) {
        //                return false;
        //            }
        //            DTMFTone tone = (DTMFTone)obj;
        //            if (freqs[0] == tone.freqs[0] && freqs[1] == tone.freqs[1]) {
        //                return true;
        //            }
        //            if (freqs[0] == tone.freqs[1] && freqs[1] == tone.freqs[0]) {
        //                return true;
        //            }
        //            return false;
        //        }

        @Override
        public String toString() {
            return Character.toString(number);
        }
    }

    public static final int [] low             = {697, 770, 852, 941};
    public static final int [] high            = {1209, 1336, 1477, 1633};
    WaveClip []                dials;

    public static final int    INVALID_DIAL_ID = -1;
    public static final int    DIAL_0          = 13;
    public static final int    DIAL_1          = 0;
    public static final int    DIAL_2          = 1;
    public static final int    DIAL_3          = 2;
    public static final int    DIAL_4          = 4;
    public static final int    DIAL_5          = 5;
    public static final int    DIAL_6          = 6;
    public static final int    DIAL_7          = 8;
    public static final int    DIAL_8          = 9;
    public static final int    DIAL_9          = 10;
    public static final int    DIAL_STAR       = 12;
    public static final int    DIAL_POUND      = 14;

    private int                sampleRate      = 8000;
    private int                bits            = 8;

    public static int getDialId(int num) {
        if (num < 0 || num > 9) {
            return INVALID_DIAL_ID;
        }
        switch (num) {
            case 1:
            case 2:
            case 3:
                return num - 1;
            case 4:
            case 5:
            case 6:
                return num;
            case 7:
            case 8:
            case 9:
                return num + 1;
            case 0:
                return 13;
        }
        return INVALID_DIAL_ID;
    }

    public Dial() {
        this(0.2, 8000, 8);
    }

    public Dial(double duration, int rate, int bit) {
        sampleRate = rate;
        bits = bit;
        dials = new WaveClip[low.length * high.length];
        int k = 0;
        for (int i = 0; i < low.length; ++i) {
            for (int j = 0; j < high.length; ++j) {
                //                dials[k] = new WaveClip(duration, 8000, 8);
                dials[k] = new WaveClip(duration, sampleRate, bits);
                WaveClip.addSignalByFreq(dials[k], new double[] {low[i], high[j]}, dials[k].maxValue * 0.6);
                ++k;
            }
        }

    }

    public void dial(int [] numbers) {
        for (int n : numbers) {
            int id = getDialId(n);
            if (id != INVALID_DIAL_ID) {
                Tool.playData(dials[id]);
            }
        }
    }

    public void dial(String number) {
        for (int i = 0, len = number.length(); i < len; ++i) {
            char c = number.charAt(i);
            int id = getDialId(c - '0');
            if (id != INVALID_DIAL_ID) {
                Tool.playData(dials[id]);
            }
        }
    }

    public float [] getDialArray(String number, double intervalTime) {
        int n = 0;
        int blank = (int)(intervalTime * sampleRate);
        for (int i = 0, len = number.length(); i < len; ++i) {
            char c = number.charAt(i);
            int id = getDialId(c - '0');
            if (id != INVALID_DIAL_ID) {
                n += (dials[id].getSampleSize() + blank);
            }
        }
        float [] data = new float[n];
        //        Arrays.fill(data, 0);
        int offset = 0;
        for (int i = 0, len = number.length(); i < len; ++i) {
            char c = number.charAt(i);
            int id = getDialId(c - '0');
            if (id != INVALID_DIAL_ID) {
                WaveClip wav = dials[id];
                System.arraycopy(wav.getRawData(), 0, data, offset, wav.getSampleSize());
                offset += wav.getSampleSize();
                Arrays.fill(data, offset, offset + blank, 0);
                offset += blank;
            }
        }
        return data;
    }
    //
    //    /**
    //     * @param args
    //     */
    //    public static void main(String [] args) {
    //        //        Dial dial = new Dial(0.5);
    //        Dial dial = new Dial();
    //        dial.dial("15021854962");
    //        //        dial.dial("13608970779");
    //        //        dial.dial("015021854962");
    //        //        dial.dial("3804269");
    //        //        dial.dial("3.14159265358979328346264338327950288");
    //        //        dial.dial("119");
    //
    //    }

}
