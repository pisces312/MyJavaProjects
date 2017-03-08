package com.ibm.nili.spectrum;

import java.util.Iterator;
import java.util.LinkedList;

import com.ibm.nili.dsp.DigitalSignalProcessor;
import com.ibm.nili.music.PolyNote;
import com.ibm.nili.music.TonePlayer;

public class SpectrumMusicConverter implements DigitalSignalProcessor {

    TonePlayer           midi;
    int                  freqRanageStart;
    int                  freqRanageEnd;
    float                valve;
    //
    LinkedList<PolyNote> notes;
    int                  dtMilisecond;
    int                  maxNoteCount;
    float                correctness;

    public SpectrumMusicConverter(int freqRanageStart, int freqRanageEnd, float valve, int sampleSize, int sampleRate) {
        midi = new TonePlayer();
        this.freqRanageEnd = freqRanageEnd;
        this.freqRanageStart = freqRanageStart;
        this.valve = valve;
        notes = new LinkedList<PolyNote>();
        dtMilisecond = (int)(1000 * sampleSize / (float)sampleRate);
        System.out.println("dtMilisecond:" + dtMilisecond);
    }

    public void setConvertParameters(int maxNoteCount, float correctness) {
        this.maxNoteCount = maxNoteCount;
        this.correctness = correctness;
    }

    PolyNote lastUnchangedNote;

    //TODO
    @Override
    public void process(float [] fft, float [] pRightChannel, float timeStamp, float ratio) {

        //        System.out.println("-----processing " + timeStamp);
        //        PolyNote note = new PolyNote(maxNoteCount);
        //        int freqT = 0;
        //        for (int i = 0; i < fft.length; ++i) {
        //            freqT = (int)(i * ratio);
        //            if (freqT > freqRanageEnd || freqT < freqRanageStart) {
        //                continue;
        //            }
        //            if (fft[i] > valve) {
        //                Pitch n = Pitch.getPitchFromFreq(freqT);
        //                if (n != null) {
        //                    float db = (float)Tool.getDBFromNormalizedData(fft[i]);
        //                    note.addTone(n, db);
        //                }
        //
        //            }
        //        }
        //        //convert
        //        convert2(note);

    }

    //    private void convert(PolyNote note) {
    //        //        System.out.println("----current note:" + note);
    //        //
    //        //        float correctness = 0.6f;
    //        if (notes.isEmpty()) {
    //            notes.addLast(note);
    //        } else {
    //            PolyNote existedNote = notes.getLast();
    //            //check if last unchanged one is identical to current one
    //            if (PolyNote.isIdentical(lastUnchangedNote, note, correctness)
    //                    || PolyNote.isIdentical(existedNote, note, correctness)) {
    //
    //                existedNote.updateExistedNoteWithIdenticalOne();
    //            } else {
    //                notes.addLast(note);
    //            }
    //
    //        }
    //        lastUnchangedNote = note;
    //        //        System.out.println(note);
    //
    //    }

    //    private void convert2(PolyNote note) {
    //        //        System.out.println("----current note:" + note);
    //        //
    //        //        float correctness = 0.6f;
    //        if (notes.isEmpty()) {
    //            notes.addLast(note);
    //        } else {
    //            PolyNote existedNote = notes.getLast();
    //            PolyNote remainedNote = existedNote.updateExistedNote(note);
    //            if (remainedNote != null) {
    //                notes.addLast(note);
    //            }
    //
    //        }
    //
    //    }

    @Override
    public void afterAllProccesed() {
        //        int dtMilisecond = 186;
        //        int dtMilisecond = 186;
        //remove the prefix empty notes
        Iterator<PolyNote> itr = notes.iterator();
        while (itr.hasNext()) {
            PolyNote n = itr.next();
            if (n.getAllNotes().isEmpty()) {
                itr.remove();
            } else {
                break;
            }
        }
        //        System.out.println("===================================");
        for (PolyNote n : notes) {
            //            System.out.println(n);
            //convert2
            //            midi.playerNote(n, dtMilisecond, 50);
            //convert1
            //            midi.playerNoteInOneTime(n, dtMilisecond, 50);
        }

    }

}
