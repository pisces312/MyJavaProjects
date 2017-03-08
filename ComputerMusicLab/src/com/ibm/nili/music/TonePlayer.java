package com.ibm.nili.music;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class TonePlayer {

    Synthesizer synthesizer;
    MidiChannel defaultChannel;

    public TonePlayer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel channels[] = synthesizer.getChannels();
            defaultChannel = channels[0];
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class SingleNotePlayThread extends Thread {
        int note;
        int v;
        int onDur;
        int offDur;

        public SingleNotePlayThread(int note, int v, int onDur, int offDur) {
            this.note = note;
            this.v = v;
            this.onDur = onDur;
            this.offDur = offDur;
        }

        @Override
        public void run() {
            defaultChannel.noteOn(note, v);
            try {
                Thread.sleep(onDur);
            } catch (InterruptedException e) {}
            defaultChannel.noteOff(note, offDur);
            try {
                Thread.sleep(offDur);
            } catch (InterruptedException e) {}
        }
    }

    //TODO
    public void playerNote(PolyNote note, int dt, int velocities) {
        //        int size = note.getAllNotes().size();
        //        if (size == 0) {
        //            try {
        //                Thread.sleep(dt * note.timeCount);
        //            } catch (InterruptedException e) {}
        //            return;
        //        }
        //        //////////////////////////////////////////////////////
        //        int notes[] = new int[size];
        //        int [] onDurations = new int[size];
        //        int offDurations = dt;
        //        //    for(int i=0;i<size;++i){
        //        //        notes[i] = p.noteOrder;
        //        //    }
        //        int i = 0;
        //        SingleNotePlayThread [] threads = new SingleNotePlayThread[size];
        //        for (Note n : note.getAllNotes()) {
        //            notes[i] = n.pitch.noteOrder;
        //            onDurations[i] = n.timeCount * dt;
        //            threads[i] = new SingleNotePlayThread(notes[i], velocities, onDurations[i], offDurations);
        //            threads[i].start();
        //            ++i;
        //        }
        //        try {
        //            Thread.sleep(dt);
        //        } catch (InterruptedException e) {}
    }

    //TODO
    public void playerNoteInOneTime(PolyNote note, int dt, int velocities) {
        //        int size = note.getAllNotes().size();
        //        if (size == 0) {
        //            try {
        //                Thread.sleep(dt * note.timeCount);
        //            } catch (InterruptedException e) {}
        //            return;
        //        }
        //        //////////////////////////////////////////////////////
        //        int notes[] = new int[size];
        //        int onDurations = 0;
        //        int offDurations = dt;
        //        //    for(int i=0;i<size;++i){
        //        //        notes[i] = p.noteOrder;
        //        //    }
        //        int i = 0;
        //
        //        for (Note n : note.getAllNotes()) {
        //            notes[i] = n.pitch.noteOrder;
        //            if (onDurations == 0) {
        //                onDurations = n.timeCount * dt;
        //            }
        //            ++i;
        //        }
        //        playMultipleNote(defaultChannel,
        //                notes, velocities, onDurations, offDurations);
    }

    public void playMultipleNote(Collection<Pitch> pitches, int velocities, int onDurations, int offDurations) {
        playMultipleNote(defaultChannel, pitches, velocities, onDurations, offDurations);
    }

    /**
     * If velocity is zero, this method instead acts like noteOff(int),
     * terminating the note.
     * 
     * 
     * noteNumber - the MIDI note number, from 0 to 127 (60 = Middle C)
     * velocity - the speed with which the key was depressed
     * 
     * @param channel
     * @param notes
     * @param velocities
     * @param durations
     */
    public static void playNoteSequence(MidiChannel channel,
            int [] notes, int [] velocities, int [] durations) {
        for (int i = 0; i < notes.length; i++) {
            channel.noteOn(notes[i], velocities[i]);
            try {
                Thread.sleep(durations[i]);
            } catch (InterruptedException e) {}
        } // for
        for (int i = 0; i < notes.length; i++)
            channel.noteOff(notes[i]);
    } // playChannel

    public static void playSingleNote(MidiChannel channel,
            int note, int velocities, int onDurations, int offDurations) {
        playMultipleNote(channel, new int[] {note}, velocities, onDurations, offDurations);
    }

    public static void playMultipleNote(MidiChannel channel,
            Collection<Pitch> pitches, int velocities, int onDurations, int offDurations) {
        int notes[] = new int[pitches.size()];
        int i = 0;
        for (Pitch p : pitches) {
            notes[i] = p.noteOrder;
            ++i;
        }
        playMultipleNote(channel, notes, velocities, onDurations, offDurations);
    }

    public static void playMultipleNote(MidiChannel channel,
            int [] notes, int velocities, int onDurations, int offDurations) {
        for (int i = 0; i < notes.length; i++) {
            channel.noteOn(notes[i], velocities);
        } // for
        try {
            Thread.sleep(onDurations);
        } catch (InterruptedException e) {}
        for (int i = 0; i < notes.length; i++)
            channel.noteOff(notes[i], offDurations);
        try {
            Thread.sleep(offDurations);
        } catch (InterruptedException e) {}
        //            channel.noteOff(notes[i]);
    }

    public static void playNote(Synthesizer synthesizer, int channel, int note, int velocity, int duration) {
        ShortMessage myMsg = new ShortMessage();
        // Play the note Middle C (60) moderately loud
        // (velocity = 93)on channel 4 (zero-based).        
        try {
            myMsg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
            Receiver synthRcvr = synthesizer.getReceiver();
            synthRcvr.send(myMsg, -1); // -1 means no time stamp
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {}
        } catch (InvalidMidiDataException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MidiUnavailableException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

}
