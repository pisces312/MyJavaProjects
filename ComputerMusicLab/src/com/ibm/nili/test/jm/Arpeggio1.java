package com.ibm.nili.test.jm;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.Mod;
import jm.util.View;

/**
 * This class turns a series of pitches into a repeating arpeggio
 * 
 * @author Andrew Brown
 */

public class Arpeggio1 implements JMC {

    public static void main(String [] args) {
        new Arpeggio1();
    }

    public Arpeggio1() {
        int [] pitches = {C4, G4, F4, C5, C4, G4, A4, G4};
        //        int [] pitches = {C4, F4, BF4};
        // turn pitches into a phrase
        Phrase arpPhrase = new Phrase();
        for (int i = 0; i < pitches.length; i++) {
            Note n = new Note(pitches[i], SEMI_QUAVER);
            arpPhrase.addNote(n);
        }

        // reverse the pitches
        Mod.palindrome(arpPhrase); // optional boolean second argument
        // repeat the arpeggio a few times
        Mod.repeat(arpPhrase, 3);
        View.notate(arpPhrase);
        // save it as a file
        //        Write.midi(arpPhrase, "Arpeggio1.mid");

    }
}
