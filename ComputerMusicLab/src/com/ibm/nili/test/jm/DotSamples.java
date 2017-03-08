package com.ibm.nili.test.jm;
import jm.JMC;
import jm.audio.Instrument;
import jm.audio.inst.SawtoothInst;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;

/**
 * Musical notes are often talked about as 'dots' and when displayed on a time
 * line they can appear as 'dashes' which indicate their length.
 * 
 * This program is very simple. It creates one jMusic note and displays it as
 * common practice notation.
 * 
 * 
 * @author pisces312
 * 
 */
//JMC is short for J-Music-Constants
public class DotSamples implements JMC {
    public static void testNote() {
        //四分音符的C4
        //C4 value is also the MIDI C4 value
        //一个note只含有一个音，dynamic表示强度
        //pan 表示左右声道的平衡
        Note n = new Note(C4, QUARTER_NOTE, MF, 0.5);

        /**
         * A jMusic Phrase can contain any number of notes and they are stored,
         * displayed and played in the order in which they are added, one after
         * the other.
         * 
         * 从时间0.0开始的小节
         */
        Phrase phr = new Phrase(0.0);
        phr.addNote(n);
        Mod.repeat(phr, 15);

        //同样从0.0开始，可以实现同一时间多个音
        Phrase phr2 = new Phrase(0.0);
        //REST为休止符
        Note r = new Note(REST, EIGHTH_NOTE);
        phr2.addNote(r);
        Note n2 = new Note(E4, EIGHTH_NOTE);
        phr2.addNote(n2);
        Note r2 = new Note(REST, QUARTER_NOTE);
        phr2.addNote(r2);
        Mod.repeat(phr2, 7);

        Part p = new Part();
        p.addPhrase(phr);
        p.addPhrase(phr2);

        //显示Part
        View.show(p);
        View.notate(phr);
        View.notate(p);

    }

    public static void test3() {
        Note n = new Note(C4, QUARTER_NOTE);
        Phrase phr = new Phrase(0.0);
        phr.addNote(n);
        Mod.repeat(phr, 15);

        Phrase phr2 = new Phrase(0.0);
        //每两个一组
        double [] phrase2data = {REST, EIGHTH_NOTE, E4, EIGHTH_NOTE, REST, QUARTER_NOTE};
        phr2.addNoteList(phrase2data);
        Mod.repeat(phr2, 7);

        Phrase phr3 = new Phrase(0.0);
        double [] phrase3data = {REST, QUARTER_NOTE, F4, EIGHTH_NOTE, REST, EIGHTH_NOTE};
        phr3.addNoteList(phrase3data);
        Mod.repeat(phr3, 7);

        Part p = new Part();
        Phrase [] phrases = {phr, phr2, phr3};
        p.addPhraseList(phrases);

        View.sketch(p);
    }

    public static void testWriteMidiFile() {
        /**
         * The Score class contains all the notes for a piece of music (or at
         * least a section of a piece of music) and as a result it does not have
         * many unique methods.
         */
        Score aScore = new Score("Composition 101");
        // musical material
        double [] phraseOneData = {C4, QUARTER_NOTE};
        double [] phraseTwoData = {REST, EIGHTH_NOTE,
                E4, EIGHTH_NOTE, REST, QUARTER_NOTE};
        double [] phraseThreeData = {REST, QUARTER_NOTE,
                F4, EIGHTH_NOTE, REST, EIGHTH_NOTE};
        double [] phraseFourData = {FS2, SIXTEENTH_NOTE};

        // arrangement and orchestration
        /**
         * The channel number is mainly required for MIDI and QuickTime playback
         * as only one instrument can be allocated to each channel.
         */
        aScore.addPart(notesToPart(phraseOneData, 0.0, 48, CLARINET, 0));
        aScore.addPart(notesToPart(phraseTwoData, 4.0, 9, FLUTE, 1));
        aScore.addPart(notesToPart(phraseThreeData, 8.0, 7, NYLON_GUITAR, 2));
        aScore.addPart(notesToPart(phraseTwoData, 30.0, 7, FLUTE, 3));
        aScore.addPart(notesToPart(phraseThreeData, 32.0, 7, HORN, 4));
        // add the hihats
        aScore.addPart(notesToPart(phraseFourData, 2.0, 190, 0, 9));

        // add accents
        Mod.accents(aScore, 2.0);

        // specify the tempo
        aScore.setTempo(140.0);

        // view the score
        //        View.sketch(aScore);

        // play the score with QuickTime
//        QTPlayer.display(aScore);

        // save the score as a MIDI file
        //        Write.midi(aScore, "Dot07.mid");
    }

    //XXX
    public static Part notesToPart(double [] notes,
            double startTime, int repeats, int instrument, int channel) {
        // create a new phrase from the notes and loop it
        Phrase aPhrase = new Phrase(startTime);
        aPhrase.addNoteList(notes);
        Mod.repeat(aPhrase, repeats);
        // create a new part and add the phrase to it
        Part aPart = new Part("Part " + channel,
                instrument, channel);
        aPart.addPhrase(aPhrase);
        // keep track of how many parts have been created
        //    partCounter++;
        return aPart;

    }

    public static void testPlayANote() {
        Play.midi(new Note(C4, QN));
        Play.closeAll();
    }

    public static void testWriteAudioFile() {
        Score score = new Score(new Part(new Phrase(new Note(C4, MINIM))));
//        Write.midi(score);
        Instrument inst = new SawtoothInst(44100);
        Write.au(score, inst);
    }

    public static void main(String [] args) {
//        testPlayANote();
//        testWriteAudioFile();
        //        testWriteMidiFile();
        //        test4();
        //        test3();
        //        test2();
        //        Note n = new Note();
        //        n.setPitch(G2);
        //        n.setRhythmValue(QUAVER);
        //        Note note = new Note(C4, CROTCHET, MF);
        //        n.setPan(0.2);
        //        n.setDynamic(120);
        //        Phrase phr = new Phrase();
        //        phr.addNote(n);
        //        View.notate(phr);

    }
}
