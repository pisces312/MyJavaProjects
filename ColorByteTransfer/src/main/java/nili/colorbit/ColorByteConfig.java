package nili.colorbit;

import java.awt.Dimension;
import java.awt.Toolkit;

/* For Logic */
public class ColorByteConfig {


    int totalByteWidth;
    int totalByteHeight;

    int colorByteWidth;
    int colorByteHeight;

    //Numbers
    int maxByteX;
    int maxByteY;


    public ColorByteConfig(int maxW, int maxH, int d) {
        setSize(maxW, maxH, d);
    }

    public ColorByteConfig() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //!!!
        setSize(dim.width, dim.height, 8);
    }

    public int getBytePerScreen() {
        return maxByteX * maxByteY;
    }

    public int getTotalScreenNum(int totalBytes) {
        int bytePerScreen = getBytePerScreen();
        return totalBytes / bytePerScreen + (((totalBytes % bytePerScreen) == 0) ? 0 : 1);
    }

    public void setSize(int maxW, int maxH, int d) {
        colorByteWidth = colorByteHeight = d;

//        frameW = maxW;
//        frameH = maxH;

//        canvasWidth = maxW - 200;
//        canvasHeight = maxH - 100;

//        leftMargin = rightMargin = topMargin = bottomMargin = 16;

        totalByteWidth = maxW - 250;
        totalByteHeight = maxH - 140;

        maxByteX = totalByteWidth / colorByteWidth;
        maxByteY = totalByteHeight / colorByteHeight;

        // update
        totalByteWidth = maxByteX * colorByteWidth;
        totalByteHeight = maxByteY * colorByteHeight;



        System.out.println(totalByteWidth);
        System.out.println(totalByteHeight);
        System.out.println(maxByteX);
        System.out.println(maxByteY);
//        System.out.println("Bytes/Screen: " + getBytePerScreen());
    }

}
