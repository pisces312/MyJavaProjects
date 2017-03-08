package com.ibm.nili.music;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * A phrase is made up of notes
 * @author nili66china
 *
 */
public class PolyNote {

    //sorted by dB value
    LinkedList<Note> notes;
    //for non-tone
    int              timeCount;
    int              maxNoteCount;

    //max note count
    public PolyNote(int maxNoteCount) {
        //        notes = new PriorityQueue<Note>(8, Note.noteComparator);
        notes = new LinkedList<Note>();
        timeCount = 1;
        this.maxNoteCount = maxNoteCount;
        //        notes = new HashSet<Note>();
    }

    public Collection<Note> getAllNotes() {
        return notes;
    }

    /**
     * TODO
     * add a new type of tone
     * 
     * return whether a new added one
     * 
     * @param t
     */
    public boolean addTone(Note note) {
      boolean isNewAdded = false;
//        if (notes.size() == 0) {
//            notes.add(new Note(t, dB));
//            return true;
//        }

//
//        Note note = null;
//        int len = notes.size();
//        int i = 0;
//        for (; i < len; ++i) {
//            note = notes.get(i);
//            if (note.pitch.equals(t)) {
//                note.updateDBValue(dB);
//                //update sort
//                for (int j = i + 1; j < len; ++j) {
//                    note = notes.get(j - 1);
//                    Note note2 = notes.get(j);
//                    if (dB > note2.db) {
//                        notes.set(j - 1, note2);
//                        notes.set(j, note);
//                    }
//                }
//                break;
//            }
//        }
//        //not found
//        if (i == notes.size()) {
//            if (notes.size() < maxNoteCount) {
//                for (i = 0; i < len; ++i) {
//                    if (notes.get(i).getDB() > dB) {
//                        isNewAdded = true;
//                        break;
//                    }
//                }
//                if (isNewAdded) {
//                    notes.add(i, new Note(t, dB));
//                }
//                if (i == len) {
//                    notes.add(new Note(t, dB));
//                    isNewAdded = true;
//                }
//
//            } else {
//                for (i = 0; i < len; ++i) {
//                    if (notes.get(i).getDB() > dB) {
//
//                        break;
//                    }
//                }
//                if (i != 0) {
//                    notes.set(i - 1, new Note(t, dB));
//                }
//            }
//        }

        return isNewAdded;
    }

    public void removeTone(Pitch t) {
        Iterator<Note> itr = notes.iterator();
        while (itr.hasNext()) {
            Note n = itr.next();
            if (n.pitch.equals(t)) {
                itr.remove();
                break;
            }
        }
    }

    public static boolean isIdentical(PolyNote note1, PolyNote note2, float correctness) {
        if (note1.getAllNotes().isEmpty() && note2.getAllNotes().isEmpty()) {
            return true;
        }
        if (note1.getAllNotes().isEmpty() != note2.getAllNotes().isEmpty()) {
            return false;
        }
        int c = 0;
        Collection<Note> list2 = note2.getAllNotes();
        for (Note n1 : note1.getAllNotes()) {
            Iterator<Note> itr = list2.iterator();
            while (itr.hasNext()) {
                Note n2 = itr.next();
                if (n1.equals(n2)) {
                    ++c;
                    break;
                }
            }
        }
        float r = c / (float)note1.getAllNotes().size();
//        System.out.println("correctness:" + r);
        if (r > correctness) {
            return true;
        }
        return false;
    }

//    public void updateExistedNoteWithIdenticalOne() {
//        for (Note n : getAllNotes()) {
//            n.addTimeCount(1);
//        }
//        timeCount++;
//
//    }

    //    public CompositeNote updateExistedNote(CompositeNote note, float correctness) {
    //        if (isIdentical(this, note, correctness)) {
    //            for (Note n : getAllNotes()) {
    //                n.addTimeCount(1);
    //            }
    //            timeCount++;
    //            return null;
    //        }
    //        return note;
    //
    //    }

    /**
     * return the remained note
     * TODO
     * @param note
     * @return
     */
//        public PolyNote updateExistedNote(PolyNote note) {
//            if (note.getAllNotes().isEmpty()) {
//                if (getAllNotes().isEmpty()) {
//                    ++timeCount;
//                    return null;
//                }
//                return note;
//            }
//    
//            Collection<Note> list2 = note.getAllNotes();
//            //        System.out.println("updateExistedNote");
//            for (Note n : notes) {
//                //            if (note.getAllNotes().contains(n)) {
//                //                n.addTimeCount(1);
//                //                note.removeTone(n.pitch);
//                //                System.out.println("remove");
//                //            }
//                Iterator<Note> itr = list2.iterator();
//                while (itr.hasNext()) {
//                    Note n2 = itr.next();
//                    if (n.equals(n2)) {
//                        n.addTimeCount(1);
//                        itr.remove();
//                        break;
//                    }
//                }
//            }
//            //after filtering
//            if (list2.isEmpty()) {
//                return null;
//            }
//            return note;
    
//        }

    @Override
    public String toString() {
        if (notes.isEmpty()) {
            return "blank note (" + timeCount + ")";
        }
        StringBuilder sb = new StringBuilder();
        for (Note n : notes) {
            sb.append(n.toString() + " ");
        }
        return sb.toString();
    }

}
