package com.ibm.nili.music;

import java.util.Comparator;

public class Note implements Comparable<Note> {
    public final static Comparator<Note> noteComparator = new Comparator<Note>() {

                                                            @Override
                                                            public int compare(Note arg0, Note arg1) {
                                                                return arg0.pitch.noteOrder - arg1.pitch.noteOrder;
                                                            }
                                                        };

    Pitch                                pitch;

    double                               rhythmValue;

    //the number of delta time
    //    int                                  timeCount;
    //    float                                db             = Float.NEGATIVE_INFINITY;

    //mili
    //    int   durationMiliSecond;

    public Note(Pitch pitch, double rhythmValue) {
        this.pitch = pitch;
        this.rhythmValue = rhythmValue;
        //        updateDBValue(db);
    }

    public Note(Pitch pitch) {
        this(pitch, Float.NEGATIVE_INFINITY);
    }

    //    public float getDB() {
    //        return db;
    //    }
    //
    //    public void updateDBValue(float db) {
    //        if (db > this.db)
    //            this.db = db;
    //    }

    //    public void addTimeCount(int d) {
    //        timeCount += d;
    //    }

    @Override
    public String toString() {
        return pitch.name.toString() + pitch.octave;
        //        return pitch.name.toString() + pitch.octave + "(" + timeCount + "," + db + "dB)";
    }

    @Override
    public int compareTo(Note o) {
        return pitch.noteOrder - o.pitch.noteOrder;
    }

    @Override
    public boolean equals(Object arg0) {
        Note n = (Note)arg0;
        return pitch.equals(n.pitch);
    }

}
