package com.ibm.nili.music.mode;

/**
 * 调式
 * 一个调式可以有多个音阶
 * 
 * @author pisces312
 * 
 */
public abstract class Mode {
    //    int pitchNumber;
    public int [] intervals;

    public int getPitchNumber() {
        return intervals.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : intervals) {
            sb.append(i);
            sb.append(" ");
        }
        return sb.toString();
    }
}
