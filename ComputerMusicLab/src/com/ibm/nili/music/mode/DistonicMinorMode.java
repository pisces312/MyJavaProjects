package com.ibm.nili.music.mode;

public class DistonicMinorMode extends Mode {
    public static final DistonicMinorMode mode = new DistonicMinorMode();

    private DistonicMinorMode() {
        intervals = new int[7];
        intervals[0] = 2;
        intervals[1] = 1;
        intervals[2] = 2;
        intervals[3] = 2;
        intervals[4] = 1;
        intervals[5] = 2;
        intervals[6] = 2;
    }

    public static Mode getInstance() {
        return mode;
    }
}
