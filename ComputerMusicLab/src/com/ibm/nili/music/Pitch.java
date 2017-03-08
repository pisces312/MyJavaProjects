package com.ibm.nili.music;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.ibm.nili.common.Tool;
import com.ibm.nili.music.mode.DistonicMajorMode;
import com.ibm.nili.signalgenerator.WaveClip;

/**
 * 音高
 * 
 * 乐音
 * tone
 * musical sound
 * musical tone
 * 
 * @author nili66china
 * 
 */
public class Pitch implements Comparable<Pitch> {
    ///////////////////////////////////////
    //member
    public double            freq;
    public int               octave;
    public PitchName         name;
    //middle C is 60
    public int               noteOrder;

    /////////////////////////////////////////
    //标准音
    public static double     A4;
    public static int        OCTAVE_NUM;
    public static int        PITCH_NUM_PER_OCTAVE;
    public static int        CENT_NUM_PER_OCTAVE;  ;
    /////////////////////////////////////////////////////////
    //    public static double     A0;
    //    public static double     ROOT_OF_12;
    //音分 十二平均律半音的音程距离相差2开12次方倍，即1.0594630941倍。将半音音程的距离再分成100个单位，即100音分。
    public static double     CENT;
    public static Pitch [][] pitchMap;
    //    private static ArrayList<Note> sortedList;
    private static Pitch []  pitchArray;

    /**
     * 初始化十二平均律
     * 
     * @param standardPitch
     * @param octaveNum
     */
    public static void initTwelveToneEqualTemperament(double standardPitch, int octaveNum) {
        configToneEqualTemperament(standardPitch, octaveNum, 12);
    }

    public static void configToneEqualTemperament(double standardPitch, int octaveNum, int pitchPerOctave) {
        A4 = standardPitch;
        OCTAVE_NUM = octaveNum;
        PITCH_NUM_PER_OCTAVE = pitchPerOctave;
        CENT_NUM_PER_OCTAVE = PITCH_NUM_PER_OCTAVE * 100;
        //update extra pitchname
        if (pitchPerOctave > PitchName.getPitchNumber()) {
            int c = PitchName.getPitchNumber() + 1;
            while (c <= pitchPerOctave) {
                new PitchName(c, "(UNK" + c + ")");
                ++c;
            }
        }

        double ROOT_OF_12 = Math.pow(2, 1 / (double)PITCH_NUM_PER_OCTAVE);
        CENT = Math.pow(2, 1 / (double)(CENT_NUM_PER_OCTAVE));

        if (pitchMap != null) {
            pitchMap = null;
        }
        pitchMap = new Pitch[OCTAVE_NUM][PITCH_NUM_PER_OCTAVE];
        //////////////////////////////////////////////////////
        //
        double freqA = A4 / 16;
        for (int i = 0; i < OCTAVE_NUM; ++i, freqA *= 2) {
            int j = PitchName.A.pitchOrder;
            if (j < PITCH_NUM_PER_OCTAVE) {
                //                System.out.println("A");
                pitchMap[i][j] = new Pitch(freqA, i, PitchName.A);
            }
            double freq = freqA;
            for (j = 8; j >= 0; --j) {
                freq /= ROOT_OF_12;
                //for any pitch number per octave support
                if (j < PITCH_NUM_PER_OCTAVE) {
                    pitchMap[i][j] = new Pitch(freq, i, PitchName.getPitchNameByOrder(j));
                }
            }
            freq = freqA;
            for (j = 10; j < PITCH_NUM_PER_OCTAVE; ++j) {
                freq *= ROOT_OF_12;
                pitchMap[i][j] = new Pitch(freq, i, PitchName.getPitchNameByOrder(j));

            }
        }
        //////////////////////////////////////////////////////

        if (pitchArray != null) {
            pitchArray = null;
        }
        //sort
        pitchArray = new Pitch[OCTAVE_NUM * PITCH_NUM_PER_OCTAVE];
        int c = 0;
        for (int i = 0; i < OCTAVE_NUM; ++i) {
            for (int j = 0; j < PITCH_NUM_PER_OCTAVE; ++j) {
                pitchArray[c] = pitchMap[i][j];
                ++c;
            }
        }

    }

    static {
        initTwelveToneEqualTemperament(440, 10);
    }

    static class PitchFreqComparator implements Comparator<Pitch> {
        double low;
        double high;

        public PitchFreqComparator(double low, double high) {
            this.low = low;
            this.high = high;
        }

        @Override
        public int compare(Pitch o1, Pitch o2) {
            if (o1.freq <= high && o1.freq >= low && o2.freq <= high && o2.freq >= low) {
                return 0;
            }
            return Double.compare(o1.freq, o2.freq);
        }

    }

    /**
     * TODO for pitch color
     * C,D,bE,F,G,A,bB
     */
    public final static int C4           = 60;
    public final static int C0           = 12;
    public static final int COLOR_RED    = 0;
    public static final int COLOR_ORANGE = 1;
    public static final int COLOR_YELLOW = 2;
    public static final int COLOR_GREEN  = 3;
    public static final int COLOR_CYAN   = 4;
    public static final int COLOR_BLUE   = 5;
    public static final int COLOR_PURPLE = 6;

    /**
     * only use one notation
     * e.g. use #C only, not bD
     * 
     * @author pisces312
     * 
     */
    //音名（pitch name）
    public static class PitchName {
        public static List<PitchName> values = new LinkedList<PitchName>();
        String                        name;
        public static final PitchName C;
        public static final PitchName C_SHARP;
        public static final PitchName D;
        public static final PitchName E_FLAT;
        public static final PitchName E;
        public static final PitchName F;
        public static final PitchName F_SHARP;
        public static final PitchName G;
        public static final PitchName G_SHARP;
        public static final PitchName A;
        public static final PitchName B_FLAT;
        public static final PitchName B;
        static {
            C = new PitchName(0, "C");
            C_SHARP = new PitchName(1, "#C");
            D = new PitchName(2, "D");
            E_FLAT = new PitchName(3, "bE");
            E = new PitchName(4, "E");
            F = new PitchName(5, "F");
            F_SHARP = new PitchName(6, "#F");
            G = new PitchName(7, "G");
            G_SHARP = new PitchName(8, "#G");
            A = new PitchName(9, "A");
            B_FLAT = new PitchName(10, "bB");
            B = new PitchName(11, "B");
        }

        public PitchName(int n) {
            this(n, "");
        }

        public PitchName(int n, String name) {
            pitchOrder = n;
            this.name = name;
            values.add(this);
        }

        public static int getPitchNumber() {
            return values.size();
        }

        @Override
        public String toString() {
            return name;
        }

        public int getOrderInOctave() {
            return pitchOrder;
        }

        public static PitchName getPitchNameByOrder(int order) {
            return values.get(order);
        }

        public PitchName getNextPitchName(int interval) {
            return values.get((pitchOrder + interval) % PITCH_NUM_PER_OCTAVE);
        }

        public static List<PitchName> values() {
            return values;
        }

        private int pitchOrder;
    }

    //    public static enum PitchName {
    //        C(0), C_SHARP(1), D(2), E_FLAT(3), E(4), F(5), F_SHARP(6), G(7), G_SHARP(8), A(9), B_FLAT(10), B(11);
    //        //        C(0), C_SHARP(1), D_FLAT(1), D(2), D_SHARP(3), E_FLAT(3), E(4), F(5), F_SHARP(6), G_FLAT(6), G(7), G_SHARP(8), A_FLAT(
    //        //                8), A(9), A_SHARP(10), B_FLAT(10), B(11);
    //        PitchName(int n) {
    //            pitchOrder = n;
    //        }
    //
    //        public int getOrderInOctave() {
    //            return pitchOrder;
    //        }
    //
    //        public PitchName getNextPitchName(int interval) {
    //            return values()[(pitchOrder + interval) % PITCH_NUM_PER_OCTAVE];
    //        }
    //
    //        private int pitchOrder;
    //    }

    private Pitch(double freq, int octave, PitchName name) {
        this.freq = freq;
        this.octave = octave;
        this.name = name;
        noteOrder = C0 + octave * PITCH_NUM_PER_OCTAVE + name.pitchOrder;
    }

    //only for search!!
    private Pitch(double freq) {
        this.freq = freq;
    }

    public static Pitch constructPitch(double freq) {
        return new Pitch(freq);
    }

    @Override
    public int hashCode() {
        return noteOrder;
        //        return name.pitchOrder * 10 + octave;
    }

    @Override
    public int compareTo(Pitch o) {
        return Double.compare(freq, o.freq);
        //        return 0;
    }

    /**
     * only check note order
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Pitch)) {
            return false;
        }

        Pitch note = (Pitch)arg0;
        return noteOrder == note.noteOrder;
        //        if (octave != note.octave) {
        //            return false;
        //        }
        //        return name.equals(note.name);
    }

    @Override
    public String toString() {
        return name.toString() + octave + "(" + freq + ")(" + noteOrder + ")";
        //        return octave + "(" + freq + ")";
        //        return super.toString();
    }

    public static void print() {
        for (PitchName n : PitchName.values()) {
            System.out.println(n);
        }
        //        for(Note n:noteArray){
        //            System.out.println(n);
        //        }
        for (int i = 0; i < OCTAVE_NUM; ++i) {
            for (int j = 0; j < PITCH_NUM_PER_OCTAVE; ++j) {
                if (pitchMap[i][j] != null)
                    System.out.print(pitchMap[i][j] + " ");
            }
            System.out.println();

        }

    }

    public static Pitch getPitch(int oct, PitchName name) {
        return pitchMap[oct][name.pitchOrder];
    }

    public static double getFreqOfPitch(int octave, PitchName name) {
        return getPitch(octave, name).freq;
    }

    public static Pitch getPitchFromFreq(double freq, int centNumber) {
        //        double dt = centNumber * CENT;

        double dt = Math.pow(CENT, centNumber);
        double high = freq * dt;
        double low = freq / dt;

        int idx = Arrays.binarySearch(pitchArray, constructPitch(freq), new PitchFreqComparator(low, high));
        if (idx >= 0) {
            return pitchArray[idx];
        }
        return null;

    }

    /**
     * 音分
     * 
     * @param freq
     * @return
     */
    public static Pitch getPitchFromFreq(double freq) {
        return getPitchFromFreq(freq, 10);
    }

    /**
     * 
     * 小二度 1 minor second
     * 大二度 2 major second
     * 小三度 3 minor third
     * 大三度 4 major third
     * 纯四度 5 perfect forth
     * 增四度 6 Augmented fourth
     * 纯五度 7 perfect fifth
     * 小六度 8 minor sixth
     * 大六度 9 major sixth
     * 小七度 10 minor seventh
     * 大七度 11 major seventh
     * 八度 12 octave
     * 
     * @author pisces312
     * 
     */
    public static enum Interval {
        UNISON(0),
        MINOR_SECOND(1),
        MAJOR_SECOND(2),
        MINOR_THIRD(3),
        MAJOR_THIRD(4),
        PERFECT_FORTH(5),
        AUGMENTED_FORTH(6),
        PERFECT_FIFTH(7),
        MINOR_SIXTH(8), MAJOR_SIXTH(9), MINOR_SEVENTH(10), MAJOR_SEVENTH(11), OCTAVE(12);

        private int semitoneNum;

        Interval(int n) {
            semitoneNum = n;
        }

        public int getSemitoneNum() {
            return semitoneNum;
        }

    }

    /**
     * result is the number of semitone
     * 
     * @param p1
     * @param p2
     * @return
     */
    public static Interval getInterval(Pitch p1, Pitch p2) throws Exception {
        int d = p1.noteOrder - p2.noteOrder;
        if (d >= 0) {
            return getInterval(d);
        }
        return getInterval(-d);
    }

    public static Interval getInterval(int num) throws Exception {
        if (num > Interval.values().length) {
            throw new Exception();
        }
        return Interval.values()[num];
    }

    /**
     * 1200*log2(f2/f1)=1200*(log10(f2/f1)/log10(2)
     * 
     * @param p1
     * @param p2
     * @return
     */
    public static int getCentNumber(double freq1, double freq2) {
        if (freq1 == 0 || freq2 == 0) {
            return -1;
        }
        if (freq1 > freq2) {
            return (int)(CENT_NUM_PER_OCTAVE * Math.log10(freq1 / freq2) / Math.log10(2));
        }
        return (int)(CENT_NUM_PER_OCTAVE * Math.log10(freq2 / freq1) / Math.log10(2));

    }

    public static int getCentNumber(Pitch p1, Pitch p2) {
        return 100 * Math.abs(p1.noteOrder - p2.noteOrder);
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        int cent = 25;
        //TODO generate all freq by 五度相生律
        double a = 261.63;//C4
        a = 3 * a / 2;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 2;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 2;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        //
        //
        //        System.out.println("not accuracy");
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 2;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 2;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        a = 3 * a / 4;
        System.out.println(a + " " + Pitch.getPitchFromFreq(a, cent));
        //        System.out.println(Pitch.getCentNumber(369.99, 372.52));
        //        System.out.println(Pitch.getCentNumber(Pitch.getPitchFromFreq(440, 10).freq,
        //                Pitch.getPitchFromFreq(261.63, 10).freq));
        //
        //        System.out.println(Pitch.getCentNumber(Pitch.getPitchFromFreq(440, 10),
        //                Pitch.getPitchFromFreq(261.63, 10)));
        //        double hz = 400;
        //        double high = hz * Math.pow(Pitch.CENT, 10);
        //        double low = hz / Math.pow(Pitch.CENT, 10);
        //        System.out.println(low);
        //        System.out.println(high);
        //        //        System.out.println(Pitch.getPitchFromFreq(440, 10));
        //        System.out.println(Pitch.getPitchFromFreq(hz, 10));
        //        System.out.println(ROOT_OF_12);
        //        System.out.println(440 / ROOT_OF_12);
        //        print();
        //        for (int i = 0; i < 13; ++i) {
        //            System.out.println(Pitch.getInterval(i).getSemitoneNum());
        //            System.out.println(Pitch.getInterval(i));
        //        }

        //        System.out.println(getPitchFromFreq(440, 10));

        double [] freqs = {261.63, 294.33375, 331.12546875, 353.5993164825439, 392.445, 441.500625, 496.688203125};
        int octnum = 10;
        //        Pitch.configGlobal(440, octnum, 22);
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(0.5, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        int oct = octnum / 2 - 1;
        for (double freq : freqs) {
            clip.setSignalByFreq(freq, maxVol * 0.6);
            Tool.playData(clip);
        }
        new Scale(DistonicMajorMode.getInstance(), PitchName.C).playScale(4);

    }

}
