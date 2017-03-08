package com.ibm.nili.music.instrument;

import com.ibm.nili.music.Pitch;
import com.ibm.nili.music.Pitch.PitchName;
import com.ibm.nili.music.Timbre;

public class Piano {
    Timbre timbre;
    //钢琴最低27.5A0，最高4186C8
    public static final Pitch [] pitches;
    static {
        pitches = new Pitch[88];
        int k = 0;
        for (int j = PitchName.A.getOrderInOctave(); j < Pitch.PITCH_NUM_PER_OCTAVE; ++j) {
            pitches[k] = Pitch.pitchMap[0][j];
            ++k;
        }
        for (int i = 1; i < 8; ++i) {
            for (int j = 0; j < Pitch.PITCH_NUM_PER_OCTAVE; ++j) {
                pitches[k] = Pitch.pitchMap[i][j];
                ++k;
            }
        }
        pitches[pitches.length - 1] = Pitch.pitchMap[8][0];

    }

    public void print() {
        for (Pitch pitch : pitches) {
            System.out.println(pitch);
        }
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        new Piano().print();

    }

}
