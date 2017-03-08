package com.ibm.nili.presentation;

import com.ibm.nili.common.Tool;
import com.ibm.nili.signalgenerator.Dial;

public class DTMFGenerator {

    /**
     * @param args
     */
    public static void main(String [] args) {
        int sampleRate = 8000;
        int bit = 8;
        String phone = "4573894";
//        String phone = "60928854";
//        String phone = "15021854962";
        Dial dial = new Dial(0.2f, sampleRate, bit);
        dial.dial(phone);
        float [] data = dial.getDialArray(phone, 0.1);
        Tool.writeAudioFile(data, "SavedAudio8.wav", 1,
                sampleRate, bit);
    }

}
