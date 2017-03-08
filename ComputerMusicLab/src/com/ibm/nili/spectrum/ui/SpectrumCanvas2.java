package com.ibm.nili.spectrum.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import com.ibm.nili.common.Tool;
import com.ibm.nili.dsp.DigitalSignalProcessor;
import com.ibm.nili.music.Pitch;
import com.ibm.nili.music.TonePlayer;

public class SpectrumCanvas2 extends JPanel implements DigitalSignalProcessor {

    private static final long serialVersionUID                 = -3328246995154027505L;
    /**
     * GUI
     */
    //double buffer
    private Image             bi;
    SpectrumRenderer          renderer;
    //the width for each time stamp, at least 1 pixel
    int                       barWidthPixelSize;
    int                       widthPerTimePrecision;
    //x,y line area
    Rectangle                 timeLineArea;
    Rectangle                 freqLineArea;
    Rectangle                 spectrumArea;
    //height of each freq, at least 1 pixel
    int                       freqDeltaHeight;
    int                       dx                               = 50;
    int                       dy                               = 25;

    /**
     * Data
     */
    float                     timePrecision                    = 0.25f;
    int                       freqRanageStart                  = 100;
    int                       freqRanageEnd                    = 2000;
    int                       freqRange;
    float                     timeRangeStart                   = 0.0f;
    //default is Float.MAX
    float                     timeRangeEnd                     = Float.MAX_VALUE;
    //TODO
    //    float                     timeRange;
    float                     currentTime;
    //valve
    float                     minA                             = 0;
    //data buffer
    List<float []>            data;
    int                       actualSampleSizeEachTime;
    int                       sampleRate;

    /**
     * Const
     */
    public final static int   DEFAULT_SAMPLESIZE               = 512;
    public final static int   DEFAULT_SAMPLERATE               = 8000;
    public final static int   DEFAULT_FREQ_LOW                 = 100;
    public final static int   DEFAULT_FREQ_HIGH                = 2000;

    public final static int   DEFAULT_WIDTH_FOR_TIME_PRECISION = 10;

    //

    public SpectrumCanvas2(float timePrecision, int sampleSize, int sampleRate) {

        this.timePrecision = timePrecision;
        this.actualSampleSizeEachTime = sampleSize;
        this.sampleRate = sampleRate;
        //one timePrecision has 10 pixels
        widthPerTimePrecision = DEFAULT_WIDTH_FOR_TIME_PRECISION;
        float timeDelta = actualSampleSizeEachTime / (float)sampleRate;

        barWidthPixelSize = getPixelsByTimeStamp(timeDelta);
        if (barWidthPixelSize < 1) {
            barWidthPixelSize = 1;
        }

        //default values
        setFreqRange(DEFAULT_FREQ_LOW, DEFAULT_FREQ_HIGH);

        printInfo();
        data = new LinkedList<float []>();
        renderer = new SpectrumRenderer();
    }

    public int getPixelsByTimeStamp(float timeStamp) {
        return (int)(widthPerTimePrecision * timeStamp / timePrecision);
    }

    public void setPeakValve(float min) {
        minA = min;
        System.out.println(minA);
    }

    public int getMinYOfSpectrumArea() {
        return spectrumArea.y;
    }

    //    public int getMaxYOfSpectrumArea(){
    //        
    //    }

    public void printInfo() {
        System.out.println("freqRanageStart:" + freqRanageStart);
        System.out.println("freqRanageEnd:" + freqRanageEnd);
        System.out.println("freqRange:" + freqRange);
        System.out.println("timeRangeStart:" + timeRangeStart);
        System.out.println("timeRangeEnd:" + timeRangeEnd);
        //        System.out.println("timeRange:" + timeRange);
        System.out.println("barWidthPixelSize:" + barWidthPixelSize);
        //        System.out.println("barHeightPixelSize:" + barHeightPixelSize);
        System.out.println("freqHeightForGUI:" + freqDeltaHeight);

        System.out.println("timeLineArea:" + timeLineArea);
        System.out.println("freqLineArea:" + freqLineArea);
        System.out.println("spectrumArea:" + spectrumArea);

    }

    //    public void setSampleInfo(int actualSampleSizeEachTime, int sampleRate) {
    //       
    //    }

    //    public float getTimeFromX(int x) {
    //        return (float)(timeRange * (x - timeLineArea.x) / (double)timeLineArea.width + timeRangeStart);
    //    }
    //
    //    public int getXFromTime(float time) {
    //        float t = time - timeRangeStart;
    //        if (t < 0) {
    //            return -1;
    //        }
    //        return (int)(timeLineArea.width * (t / timeRange) + timeLineArea.x);
    //    }

    public float getFreqFromY(int y) {
        return (float)(freqRange * ((double)freqLineArea.height - (y - freqLineArea.y)) / (double)freqLineArea.height)
                + freqRanageStart;
    }

    public int getYFromFreq(float f) {
        return (int)(freqLineArea.height * (1 - (f - freqRanageStart) / freqRange) + freqLineArea.y);
    }

    //    private int getYOfFreq(int freq) {
    //        int t = (freq - freqRanageStart);
    //        if (t < 0) {
    //            return -1;
    //        }
    //        return barHeightPixelSize - t * freqHeightForGUI;
    //
    //        //        freqRanageStart+freq
    //        //        return timeLinePos.height;
    //    }

    public void setFreqRange(int b, int e) {
        freqRanageStart = b;
        freqRanageEnd = e;
        freqRange = e - b;
        freqDeltaHeight = (int)(spectrumArea.height / (double)freqRange);
        if (freqDeltaHeight < 1) {
            freqDeltaHeight = 1;
        }
    }

    private synchronized Image getDoubleBuffer() {
        if (bi == null || (bi.getWidth(null) != getSize().width || bi.getHeight(null) != getSize().height)) {
            bi = getGraphicsConfiguration().createCompatibleVolatileImage(
                    getSize().width,
                    getSize().height);
            //            System.out.println("--------------creat a new buffer");
        }
        return bi;
    }

    @Override
    public void paint(Graphics pGraphics) {
        //        pGraphics.drawImage(getDoubleBuffer(), 0, 0, null);
        flush();
    }

    //    public static void calcScale(int [] scale, int b, int e, double start,double end) {
    //        if(b==e+1){
    //            scale[b]=start;
    //            scale[e]=end;
    //        }
    //
    //    }

    private void drawTimeLine() {
        Graphics graphics = getDoubleBuffer().getGraphics();
        graphics.setColor(Color.BLACK);
        //TODO
        //        int mx = timeLineArea.width + timeLineArea.x;
        //        int my = timeLineArea.y + timeLineArea.height;
        //        int y1 = my >> 1;
        //        graphics.drawLine(timeLineArea.x, my, mx,
        //                my);
        //
        //        int devide = (int)(timeRange / timePrecision);
        //        int dw = timeLineArea.width / devide;
        //        double [] xArray = Tool.calcScale(dw, timeLineArea.x, mx);
        //        for (int i = 0, x = 0; i < xArray.length; ++i) {
        //            x = (int)xArray[i];
        //            String value = String.format("%3.2f", getTimeFromX(x));
        //            graphics.drawString(value, x + 2, my - 2);
        //            graphics.drawLine(x, my, x, y1);
        //        }

    }

    private void drawFreqLine() {
        Graphics graphics = getDoubleBuffer().getGraphics();
        graphics.setColor(Color.BLACK);
        int mx = freqLineArea.width + freqLineArea.x;
        int my = freqLineArea.y + freqLineArea.height;
        int mxh = mx >> 1;
        graphics.drawLine(mx, freqLineArea.y, mx,
                my);

        int devide = 16;
        int dh = freqLineArea.height / devide;
        double [] yArray = Tool.calcScale(dh, freqLineArea.y, my);
        System.out.println(Arrays.toString(yArray));
        for (int i = 0, y = 0; i < yArray.length; ++i) {
            y = (int)yArray[i];
            String value = String.format("%3.0f", getFreqFromY(y));
            graphics.drawString(value, freqLineArea.x, y);
            graphics.drawLine(mx, y, mxh, y);
        }

    }

    public void flush() {
        getGraphics().drawImage(getDoubleBuffer(), 0, 0, null);
    }

    /**
     * 
     */
    @Override
    public void process(float [] pLeftChannel, float [] pRightChannel, float timeStamp, float ratio) {

        //        System.out.println("-------process: " + timeStamp);
        data.add(pLeftChannel);

        int x = getPixelsByTimeStamp(timeStamp);
        int currentWidth = x + barWidthPixelSize;
        int currentHeight = (int)getPreferredSize().getHeight();
        timeLineArea = new Rectangle(dx, 0, currentWidth, dy);
        freqLineArea = new Rectangle(0, dy, dx, currentHeight);
        spectrumArea = new Rectangle(dx, dy, currentWidth, currentHeight);
        //update preferredSize
        setPreferredSize(new Dimension(currentWidth, currentHeight));
        //draw image
        drawTimeLine();
        drawFreqLine();
        renderer.draw(pLeftChannel, x);
        //update
        flush();

    }

    //////////////////////////////////////////////////////////////////////
    private class SpectrumRenderer {
        /**
         * each note will only show once for a timestamp
         */
        Set<Pitch>  noteSet = new HashSet<Pitch>();
        double     freqRatio;
        TonePlayer midi;

        public SpectrumRenderer() {
            midi = new TonePlayer();
        }

        /**
         * pass in the freq value
         * 
         * @param fft
         * @param freq
         */
        public void draw(float fft[], int x) {
            if (x < 0 || x > spectrumArea.width + spectrumArea.x) {
                return;
            }
            freqRatio = sampleRate / (double)(fft.length << 1);
            noteSet.clear();
            Graphics graphics = getDoubleBuffer().getGraphics();
            draw1(graphics, x, fft);

        }

        /**
         * 
         * @param graphics
         * @param x
         * @param fft
         */
        private void draw1(Graphics graphics, int x, float [] fft) {
            int y = 0;
            int freqT = 0;
            for (int i = fft.length - 1; i >= 0; --i) {
                freqT = (int)(i * freqRatio);
                if (freqT > freqRanageEnd || freqT < freqRanageStart) {
                    continue;
                }
                if (fft[i] > minA) {
                    y = getYFromFreq(freqT);
                    //                    y = getYFromFreq(freq);

                    graphics.setColor(Color.red);
                    graphics.fillRect(x, y, barWidthPixelSize, freqDeltaHeight);
                    //TODO
                    Pitch n = Pitch.getPitchFromFreq(freqT);
                    //                    Note n = Note.getNoteFromFreq(freqT, 20);
                    if (n != null) {
                        if (!noteSet.contains(n)) {
                            noteSet.add(n);
                            graphics.setColor(Color.BLACK);
                            //TODO
                            String showStr = n.name.toString() + n.octave
                                    + String.format("(%3.0fDB)", Tool.getDBFromNormalizedData(fft[i]));
                            graphics.drawString(n.name.toString() + n.octave, x, y);
                            System.out.print(showStr + " ");
                            //                            graphics.drawString(n.name.toString() + n.octave, x, y);
                        }
                    }
                }
            }
            if (!noteSet.isEmpty()) {
                System.out.println();
                //TODO
                //                midi.playMultipleNote(noteSet, 90, 500, 500);
            }
            else {
                System.out.println("-----------------");
            }
        }
        /**
         * rect can be a bar shape
         * 
         * @param graphics
         * @param fft
         * @param rect
         */
        //        public void drawOneSpectrumLine(Graphics graphics, float fft[], Rectangle rectOfAll) {
        //
        //        }
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterAllProccesed() {
        // TODO Auto-generated method stub
        
    }

}
