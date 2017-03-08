package com.ibm.nili.test.jm.realtime;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.audio.inst.SimpleFMInst;
import jm.music.data.Note;
import jm.music.rt.RTLine;

public final class MyLine extends RTLine implements JMC {

    public static void main(String [] args) {
        Instrument inst = new SimpleFMInst(44100, 800, 34.4);
        new MyLine(new Instrument[] {inst});
    }

    public MyLine(Instrument [] insts) {
        super(insts);
        RTMixer mixer = new RTMixer(new RTLine[] {this});
        mixer.begin();
    }

    public Note getNextNote() {
        Note n = new Note((int)(Math.random() * 12 + 60), QN);
        return n;
    }
}