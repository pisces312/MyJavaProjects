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

/**
 * This canvas size is fixed
 * 
 * time end must be set
 * 
 * Double buffered to enable scrollable GUI
 * 
 * @author nili66china
 * 
 */
public class SpectrumFixedCanvas extends JPanel implements DigitalSignalProcessor {
    //    public class SpectrumFixedCanvas extends Component implements KJDigitalSignalProcessor {
    /**
     * 
     */
    private static final long serialVersionUID = 32431772692351150L;
    boolean                   isShowNote;
    boolean                   isOnlyShowNoteFreq;

    private class SpectrumRenderer {
        Set<Pitch> noteSet = new HashSet<Pitch>();

        /**
         * pass in the freq value
         * 
         * @param fft
         * @param freq
         */
        public void draw(float fft[], float timeStamp, float ratio) {
            int x = getXFromTime(timeStamp);
            if (x < 0 || x > spectrumArea.width + spectrumArea.x) {
                return;
            }
            noteSet.clear();
            Graphics graphics = getDoubleBuffer().getGraphics();
            draw1(graphics, x, fft, ratio);

        }

        /**
         * 
         * @param graphics
         * @param x
         * @param fft
         */
        private void draw1(Graphics graphics, int x, float [] fft, float ratio) {
            int y = 0;
            int freqT = 0;
            Pitch n = null;
            for (int i = fft.length - 1; i >= 0; --i) {
                freqT = (int)(i * ratio);
                if (freqT > freqRanageEnd || freqT < freqRanageStart) {
                    continue;
                }
                if (fft[i] > minA) {
                    n = Pitch.getPitchFromFreq(freqT);
                    if (isOnlyShowNoteFreq) {
                        if (n == null) {
                            continue;
                        }
                        freqT = (int)n.freq;
                    }
                    y = getYFromFreq(freqT);
                    graphics.setColor(Color.red);
                    graphics.fillRect(x, y, barWidthPixelSize, freqDeltaHeight);
                    if (isShowNote) {
                        if (n != null) {
                            if (!noteSet.contains(n)) {
                                noteSet.add(n);

                                graphics.setColor(Color.BLACK);
                                //                            String showStr = n.name.toString() + n.octave
                                //                                    + String.format("(%3.0fDB)", Tool.getDBFromNormalizedData(fft[i]));
                                graphics.drawString(n.name.toString() + n.octave, x, y);
                                //                            System.out.print(showStr + " ");
                                //                            graphics.drawString(n.name.toString() + n.octave, x, y);
                            }
                        }
                    }
                }
            }

        }

    }
    private static class FFTData {
        public float [] fftResult;
        public float    timeStamp;
        public float    ratio;
    }

    //GUI
    private Image           bi;
    SpectrumRenderer        renderer;
    int                     barWidthPixelSize;
    //    int                     barHeightPixelSize;
    //x,y line area
    Rectangle               timeLineArea;
    Rectangle               freqLineArea;
    Rectangle               spectrumArea;
    //height of each freq, at least 1 pixel
    int                     freqDeltaHeight;
    int                     dx                 = 50;
    int                     dy                 = 25;
    float                   timePrecision      = 0.25f;
    //
    //
    int                     freqRanageStart;
    int                     freqRanageEnd;
    int                     freqRange;
    float                   timeRangeStart;
    //default is Float.MAX
    float                   timeRangeEnd;
    float                   timeRange;
    //    double                  timeDelta;

    public final static int DEFAULT_SAMPLESIZE = 512;
    public final static int DEFAULT_SAMPLERATE = 8000;
    //
    float                   minA               = 0;
    //
    List<FFTData>           data;
    int                     actualSampleSizeEachTime;
    int                     sampleRate;

    private SpectrumFixedCanvas(int w, int h) {

        setSize(w, h);
        //        setSpectrumSize(w, h);
//        setPreferredSize(new Dimension(w, h));
        setFreqRange(200, 2000);

        data = new LinkedList<FFTData>();
        renderer = new SpectrumRenderer();
        isShowNote = true;

    }

    public void setShowNoteFlag(boolean flag) {
        isShowNote = flag;
    }

    public void setOnlyShowNoteFreqFlag(boolean flag) {
        isOnlyShowNoteFreq = flag;
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
        System.out.println("timeRange:" + timeRange);
        System.out.println("barWidthPixelSize:" + barWidthPixelSize);
        //        System.out.println("barHeightPixelSize:" + barHeightPixelSize);
        System.out.println("freqHeightForGUI:" + freqDeltaHeight);

        System.out.println("timeLineArea:" + timeLineArea);
        System.out.println("freqLineArea:" + freqLineArea);
        System.out.println("spectrumArea:" + spectrumArea);

    }

    public void setTimePrecision(float dt) {
        timePrecision = dt;
    }

    public void setSampleInfo(int actualSampleSizeEachTime, int sampleRate) {
        this.actualSampleSizeEachTime = actualSampleSizeEachTime;
        this.sampleRate = sampleRate;
        double timeDelta = (double)actualSampleSizeEachTime / (double)sampleRate;

        barWidthPixelSize = (int)(timeLineArea.width * timeDelta / timeRange);
        if (barWidthPixelSize < 1) {
            barWidthPixelSize = 1;
        }

    }

    public void setTimeRange(float timeRangeStart, float timeRangeEnd) {
        this.timeRangeStart = timeRangeStart;
        this.timeRangeEnd = timeRangeEnd;
        timeRange = timeRangeEnd - timeRangeStart;
    }

    public float getTimeFromX(int x) {
        return (float)(timeRange * (x - timeLineArea.x) / (double)timeLineArea.width + timeRangeStart);
    }

    public int getXFromTime(float time) {
        float t = time - timeRangeStart;
        if (t < 0) {
            return -1;
        }
        return (int)(timeLineArea.width * (t / timeRange) + timeLineArea.x);

        //        return (int)(barWidthPixelSize * t / timeDelta) + timeLineArea.x;
        //        return (int)(barWidthPixelSize * t / timeRange);
    }

    public float getFreqFromY(int y) {
        return (float)(freqRange * ((double)freqLineArea.height - (y - freqLineArea.y)) / (double)freqLineArea.height)
                + freqRanageStart;
    }

    public int getYFromFreq(float f) {
        return (int)(freqLineArea.height * (1 - (f - freqRanageStart) / freqRange) + freqLineArea.y);
    }

    public void setFreqRange(int b, int e) {
        freqRanageStart = b;
        freqRanageEnd = e;
        freqRange = e - b;
        freqDeltaHeight = (int)(spectrumArea.height / (double)freqRange);
        if (freqDeltaHeight < 1) {
            freqDeltaHeight = 1;
        }
    }

    //    public void setSpectrumSize(int width, int height) {
    //        timeLineArea = new Rectangle(dx, 0, width - (dx * 2), dy);
    //        freqLineArea = new Rectangle(0, dy, dx, height - dy * 2);
    //        spectrumArea = new Rectangle(dx, dy, width - dx * 2, height - dy * 2);
    //    }

    @Override
    public void setSize(Dimension d) {
        if (d == null) {
            return;
        }
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        if (spectrumArea != null)
            System.out.println("before resize:" + spectrumArea.width + "," + spectrumArea.height);
        //        System.out.println("resize");
        timeLineArea = new Rectangle(dx, 0, width - (dx * 2), dy);
        freqLineArea = new Rectangle(0, dy, dx, height - dy * 2);
        spectrumArea = new Rectangle(dx, dy, width - dx * 2, height - dy * 2);
        System.out.println("spectrumArea:" + spectrumArea);
        System.out.println("timeLineArea:" + timeLineArea);
        System.out.println("freqLineArea:" + freqLineArea);
        System.out.println("resize:" + width + "," + height);
        setPreferredSize(new Dimension(width, height));
        super.setSize(width, height);
    }

    private synchronized Image getDoubleBuffer() {
        if (bi == null || (bi.getWidth(null) != getSize().width || bi.getHeight(null) != getSize().height)) {

            Image bi2 = getGraphicsConfiguration().createCompatibleVolatileImage(
                    getSize().width,
                    getSize().height);
            //for resize update
            if (bi != null) {
                bi2.getGraphics().setColor(Color.white);
                //            bi2.getGraphics().setPaintMode();
                //            bi2.getGraphics().
                bi2.getGraphics().fillRect(0, 0, getSize().width, getSize().height);

                bi2.getGraphics().drawImage(bi, 0, 0, null);
                //throw null
                //            bi2.getGraphics().drawImage(bi, 0, 0, bi.getWidth(null), bi.getHeight(null), null);
                bi = null;
                bi = bi2;

                setFreqRange(freqRanageStart, freqRanageEnd);

                for (FFTData fft : data) {
                    renderer.draw(fft.fftResult, fft.timeStamp, fft.ratio);
                }
                repaint();
            } else {
                bi = bi2;
            }

            drawTimeLine();
            drawFreqLine();
            //            System.out.println("--------------creat a new buffer");
        }
        return bi;
    }

    @Override
    public void paint(Graphics pGraphics) {
        pGraphics.drawImage(getDoubleBuffer(), 0, 0, null);
    }

    public void drawTimeLine() {
        Graphics graphics = getDoubleBuffer().getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(timeLineArea.x, timeLineArea.y, timeLineArea.width, timeLineArea.height);
        graphics.setColor(Color.BLACK);
        int mx = timeLineArea.width + timeLineArea.x;
        int my = timeLineArea.y + timeLineArea.height;
        int y1 = my >> 1;
        graphics.drawLine(timeLineArea.x, my, mx,
                my);

        int devide = (int)(timeRange / timePrecision);
        int dw = timeLineArea.width / devide;
        double [] xArray = Tool.calcScale(dw, timeLineArea.x, mx);
        for (int i = 0, x = 0; i < xArray.length; ++i) {
            x = (int)xArray[i];
            String value = String.format("%3.2f", getTimeFromX(x));
            graphics.drawString(value, x + 2, my - 2);
            graphics.drawLine(x, my, x, y1);
        }

    }

    public void drawFreqLine() {
        Graphics graphics = getDoubleBuffer().getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(freqLineArea.x, freqLineArea.y, freqLineArea.width, freqLineArea.height);
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

    /**
     * 
     */
    @Override
    public void process(float [] pLeftChannel, float [] pRightChannel, float timeStamp, float ratio) {

        //        System.out.println("-------process: " + timeStamp);
        FFTData fftData = new FFTData();
        fftData.fftResult = pLeftChannel;
        fftData.timeStamp = timeStamp;
        fftData.ratio = ratio;
        data.add(fftData);
        renderer.draw(pLeftChannel, timeStamp, ratio);
        repaint();

    }

    public static SpectrumFixedCanvas createInstance(int canvasW, int canvasH,
            int actualSampleSize, int sampleRate,
            float timeBegin, float timeEnd, float timeP,
            int freqBegin, int freqEnd,
            float valve

            ) {
        SpectrumFixedCanvas canvas = new SpectrumFixedCanvas(canvasW, canvasH);
        canvas.setTimeRange(timeBegin, timeEnd);
        canvas.setTimePrecision(timeP);
        canvas.setFreqRange(freqBegin, freqEnd);
        canvas.setSampleInfo(actualSampleSize, sampleRate);
        canvas.setPeakValve(valve);
        canvas.printInfo();
        return canvas;
    }

    @Override
    public void afterAllProccesed() {
        // TODO Auto-generated method stub

    }

}
