package com.ibm.nili.test.jm;

import jm.JMC;
import jm.audio.Instrument;
import jm.audio.inst.SimpleSineInst;
import jm.audio.inst.SineInst;
import jm.audio.inst.TriangleInst;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

public final class WaveTest implements JMC {
    public static void testSineManually() {
        Score s = new Score("JMDemo - Sine melody");
        Part p = new Part("Melody", MARIMBA, 0);
        Phrase phr = new Phrase();
        // the density is the resolution of notes over the sine wave function
        // a lower density produces a shorter melody - more course resolution.
        double density = 25.0;

        // create a phrase of randomly pitched quavers over the full MIDI range.
        for (int i = 0; i < (2 * 3.14 * density); i++) { /*3.14 is an approximate 
                                                           value for pi */
            int pitch = (int)(Math.sin(i / density) * 50 + 60);
            Note note = new Note(pitch, DSQ);
            phr.addNote(note);
        }

        // add the phrase to an instrument and that to a score
        p.addPhrase(phr);
        s.addPart(p);

        //display the output
        View.show(s);

        // create a MIDI file of the score
        //        Write.midi(s, "SineMelody.mid");
    }

    public static void testSineByAPI() {
        // make a jMusic score
        //        Note n = new Note(C4, 2.0);
        Note n = new Note(C4, MINIM);
        Score score = new Score(new Part(new Phrase(n)));

        // set up an audio instrument
        Instrument sineWave = new SimpleSineInst(44100);

        // for jMusic 1.1 or earlier put the instrument in an array
        // Instrument[] ensemble = {sineWave};

        // render audio file of the score
        Play.audio(score, sineWave);
        //        try {
        //            Thread.sleep(6000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        Play.closeAll();
        //        Write.au(score, "WaveformExample.au", sineWave);
        // for jMusic 1.1 or earlier substitute the line above with
        // Write.au(score, "WaveformExample.au", ensemble);
    }

    public static void testSineShapeMelody() {
        Score s = new Score();
        s.setTempo(60);
        int numbOfTones = 10;
        Instrument [] insts = new
                Instrument[numbOfTones];

        for (int i = 0; i < numbOfTones; i++) {
            Note n = new Note((int)(Math.random() * 40 + 60),
                    SEMIBREVE, (int)(Math.random() * 60 + 60));
            n.setPan(Math.random());
            Phrase phr = new Phrase(n, Math.random() * 10.0);
            Part p = new Part("Sine", i);
            p.addPhrase(phr);
            s.addPart(p);
            insts[i] = new SineInst(44100);
        }
        View.print(s);
        Play.audio(s, insts);
    }
    public static void testTriAPI() {
        //declare an instance of the trangle wave instrument
        TriangleInst tri = new TriangleInst(44100);
        tri.setOutput(Instrument.REALTIME);
//        tri.setOutput(Instrument.RENDER);
//        Instrument sineWave = new SimpleSineInst(44100);

        // The third argument to the Part constructor
        //specifies an element in the instrument array
        // 0 indicates the instrument array (ensemble) index to use for this part
        Phrase phr = new Phrase();
        for (short i = 0; i < 25; i++) {
            Note n = new Note(i + 60, Q, 127);
            phr.addNote(n);
        }

        Score s = new Score(new Part(phr));
        //write out the audio file
        //arguments are the file name and instrument array
        //        Write.au(s, "AudioScale.au", tri);
        //        Play.midi(s);
        Play.audio(s, tri);
    }
    public static void main(String [] args) {
//        testSineByAPI();
//        testSineManually();
        testTriAPI();
    }

}