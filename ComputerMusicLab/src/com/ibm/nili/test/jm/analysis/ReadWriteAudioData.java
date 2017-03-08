package com.ibm.nili.test.jm.analysis;

import jm.util.Read;
import jm.util.Write;

public class ReadWriteAudioData {

    public static void testRead(String file) {
        float [] data = Read.audio(file);
        if (data != null) {
            System.out.println(data.length);
        }
    }

    public static void testWrite(String [] args) {
        float [] data = Read.audio("Welcome.au");
        Write.audio(data, "SavedAudio.aif");
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        testRead("canon in d.wav");
//        testRead("D:/!music_lab/ComputerMusicLab/canon in d.wav");

    }

}
