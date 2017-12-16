package nili.colorbit;

import java.awt.Font;

public class ColorByteUIConfig extends ColorByteConfig {
    /* For UI */

    int sendInterval = 1000;

    int screeShotX = 0;
    int screeShotY = 45;

    //layout config
//    int bitStartX = 16;
//    int bitStartY = 61;

    int frameW;
    int frameH;


    int fontSize;
    Font font;

    public ColorByteUIConfig() {
        super();
        setFontSize(colorByteHeight*2);
        setFrameSize(totalByteWidth, totalByteHeight);

    }

    public ColorByteUIConfig(int maxW, int maxH, int d) {
        super(maxW, maxH, d);
        setFontSize(colorByteHeight*2);
        setFrameSize(totalByteWidth, totalByteHeight);
    }

    public void setFontSize(int size) {
        this.fontSize = size;
        font = new Font("TimesRoman", Font.BOLD, fontSize);
    }

    public void setFrameSize(int w, int h) {
        frameW = w;
        frameH = h + 2 * fontSize;
    }

}
