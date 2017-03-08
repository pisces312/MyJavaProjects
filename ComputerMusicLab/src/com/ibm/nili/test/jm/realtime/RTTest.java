package com.ibm.nili.test.jm.realtime;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.audio.inst.PluckInst;
import jm.music.rt.RTLine;

public class RTTest implements JMC {
    public static void main(String [] args) {
        int sampleRate = 44100;
        Instrument inst = new PluckInst(sampleRate);
        Instrument [] insts = new Instrument[] {inst};
        RTLine [] rtlines = {new RealtimeMelody(insts)};
        final RTMixer rtm = new RTMixer(rtlines);
        rtm.begin();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        rtm.stop();
    }
}
