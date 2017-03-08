package com.ibm.nili.test.jm.drum;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.inst.SimpleSampleInst;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;

public class AudioDrums implements JMC {
    public static void main(String [] args) {
        Score s = new Score("title");
        Part kickPart = new Part("Kick", 0, 9);
        Part snarePart = new Part("Snare", 1, 9);
        Part hatsPart = new Part("Hats", 2, 9);
        Part openHatsPart = new Part("OpenHats", 3, 9);
        Phrase kickPhrase = new Phrase();
        Phrase snarePhrase = new Phrase();
        Phrase hatsPhrase = new Phrase();
        Phrase openHatsPhrase = new Phrase();

        SimpleSampleInst kickInst = new SimpleSampleInst("Kick.au", FRQ[36]);
        SimpleSampleInst snareInst = new SimpleSampleInst("Snare.au", FRQ[38]);
        SimpleSampleInst hatsInst = new SimpleSampleInst("Hats.au", FRQ[42]);
        SimpleSampleInst openHatsInst = new SimpleSampleInst("HHOpen.au", FRQ[46]);
        Instrument [] drumKit = {kickInst, snareInst, hatsInst, openHatsInst};

        // kick 
        for (int i = 0; i < 2; i++) {
            Note n = new Note(36, 2.0);
            kickPhrase.addNote(n);
        }

        //snare
        for (int i = 0; i < 2; i++) {
            Note r = new Note(REST, 1.0);
            snarePhrase.addNote(r);
            Note n = new Note(38, 1.0);
            n.setDuration(n.getRhythmValue());
            snarePhrase.addNote(n);
        }

        //hats
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 1 && Math.random() < 0.1) {
                Note n = new Note(46, 0.25, (int)(Math.random() * 127));
                n.setDuration(n.getRhythmValue());
                openHatsPhrase.addNote(n);
                Note r = new Note(REST, 0.25);
                hatsPhrase.addNote(r);
            } else {
                Note n = new Note(42, 0.25, (int)(Math.random() * 127));
                n.setDuration(n.getRhythmValue());
                hatsPhrase.addNote(n);
                Note r = new Note(REST, 0.25);
                openHatsPhrase.addNote(r);
            }
        }

        //pack
        kickPart.addPhrase(kickPhrase);
        snarePart.addPhrase(snarePhrase);
        hatsPart.addPhrase(hatsPhrase);
        openHatsPart.addPhrase(openHatsPhrase);

        s.addPart(kickPart);
        s.addPart(snarePart);
        s.addPart(hatsPart);
        s.addPart(openHatsPart);

        //write midi
        //		Write.midi(s, "AudioDrums.mid");

        //write au
        Write.au(s, "AudioDrums.au", drumKit);
        Play.midi(s);
        Play.closeAll();
    }
}
