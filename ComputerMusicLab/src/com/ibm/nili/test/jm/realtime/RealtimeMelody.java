package com.ibm.nili.test.jm.realtime;

import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.rt.RTLine;

public class RealtimeMelody extends RTLine {

    public RealtimeMelody(Instrument [] inst) {
        super(inst);
    }

    // required method to override
    public Note getNextNote() {
        return new Note((Math.random() * 4000) + 500, 0.25, (int)(Math.random() * 24 + 60));
    }

    public void externalAction(Object ob, int i) {
        // do filter change here

        // ob will be slider - get value

        // set filter value in instrument to slider value

        // in the instrumnet implement setController over ride method

        // in filter use the setCutOff method to change coefficients
    }
}
