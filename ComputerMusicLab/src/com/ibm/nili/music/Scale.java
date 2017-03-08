package com.ibm.nili.music;

import com.ibm.nili.common.Tool;
import com.ibm.nili.music.Pitch.PitchName;
import com.ibm.nili.music.mode.DistonicMajorMode;
import com.ibm.nili.music.mode.Mode;
import com.ibm.nili.signalgenerator.WaveClip;

/**
 * 音阶
 * 
 * @author pisces312
 * 
 */
public class Scale {
    Mode         mode;
    PitchName [] pitchNames;

    public Scale(Mode mode, PitchName primePitch) {
        this.mode = mode;
        pitchNames = new PitchName[mode.getPitchNumber()];
        setScale(primePitch);
    }

    public void setScale(PitchName primePitch) {
        pitchNames[0] = primePitch;
        for (int i = 0, len = mode.intervals.length - 1; i < len; ++i) {
            pitchNames[i + 1] = pitchNames[i].getNextPitchName(mode.intervals[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PitchName i : pitchNames) {
            sb.append(i);
            sb.append(" ");
        }
        return sb.toString();
    }

    public void playScale(int oct) {
        int sampleSizeInBits = 16;
        WaveClip clip = new WaveClip(0.5, 44100, sampleSizeInBits);
        double maxVol = WaveClip.getMaxValue(sampleSizeInBits);
        for (PitchName pitch : pitchNames) {
            System.out.print(Pitch.pitchMap[oct][pitch.getOrderInOctave()] + " ");
            clip.setSignalByFreq(Pitch.pitchMap[oct][pitch.getOrderInOctave()].freq, maxVol * 0.6);
            Tool.playData(clip);
        }
        System.out.println();

    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        Pitch.configToneEqualTemperament(440, 10, 5);
//        Pitch.configGlobal(440, 10, 12);
        new Scale(DistonicMajorMode.getInstance(), PitchName.C).playScale(4);
//        Pitch.configGlobal(880, 10, 12);
//        new Scale(DistonicMajorMode.getInstance(), PitchName.C).playScale(4);
        //        System.out.println(new Scale(DistonicMajorMode.getInstance(), PitchName.C));
        //        System.out.println(new Scale(DistonicMajorMode.getInstance(), PitchName.C_SHARP));
        //        System.out.println(new Scale(DistonicMinorMode.getInstance(), PitchName.A));
    }

}
