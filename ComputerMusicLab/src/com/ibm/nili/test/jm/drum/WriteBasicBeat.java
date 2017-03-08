package com.ibm.nili.test.jm.drum;

class WriteBasicBeat {
    static final DrumBeat beat = new BasicBeat();

    public static final void main(final String [] args) {
//        beat.saveToFile();
        beat.play();
    }
}