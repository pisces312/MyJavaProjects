package com.ibm.nili.test.jm.drum;

class WriteDiscoBeat {
    static final DrumBeat beat = new DiscoBeat();

    public static final void main(final String [] args) {
        //        beat.saveToFile();
        beat.play();
    }
}