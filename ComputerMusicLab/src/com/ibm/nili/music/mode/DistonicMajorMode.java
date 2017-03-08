package com.ibm.nili.music.mode;

public final class DistonicMajorMode extends Mode {
    public static final DistonicMajorMode mode = new DistonicMajorMode();

    private DistonicMajorMode() {
        intervals = new int[7];
        intervals[0] = 2;
        intervals[1] = 2;
        intervals[2] = 1;
        intervals[3] = 2;
        intervals[4] = 2;
        intervals[5] = 2;
        intervals[6] = 1;
    }

    public static Mode getInstance() {
        return mode;
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        System.out.println(DistonicMajorMode.getInstance());
    }

}
