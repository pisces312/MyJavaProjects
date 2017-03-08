package com.ibm.nili.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.ibm.nili.common.Tool;
import com.ibm.nili.dsp.DigitalSignalProcessor;
import com.ibm.nili.signalgenerator.Dial;
import com.ibm.nili.signalgenerator.Dial.DTMFTone;
import com.ibm.nili.spectrum.SerialAudioConsumer;

/**
 * 
 * @author nili66china
 * 
 */
public class DTMFAnalysis extends JPanel implements DigitalSignalProcessor {
    private static final long serialVersionUID = 32431772692351150L;
    private Font              font             = new Font("宋体", Font.BOLD, 26);

    private class SpectrumRenderer {
        DTMFTone             lastTone;
        LinkedList<DTMFTone> dtmfData = new LinkedList<DTMFTone>();

        public int getMTFFreq(int freq, int d) {
            for (int i : Dial.low) {
                if (Math.abs(freq - i) <= d) {
                    return i;
                }
            }
            for (int i : Dial.high) {
                if (Math.abs(freq - i) <= d) {
                    return i;
                }
            }
            return 0;
        }

        public void draw(float fft[], float timeStamp, float ratio) {
            int x = getXFromTime(timeStamp);
            if (x < 0 || x > spectrumArea.width + spectrumArea.x) {
                return;
            }
            Graphics graphics = getDoubleBuffer().getGraphics();
            int f1 = 0;
            int f2 = 0;
            for (int i = fft.length - 1, y = 0, freqT = 0; i >= 0; --i) {
                freqT = (int)(i * ratio);
                if (freqT > freqRanageEnd || freqT < freqRanageStart) {
                    continue;
                }
                if (fft[i] > minA) {
                    int f = getMTFFreq(freqT, 50);
                    if (f > 0) {
                        if (f1 == 0) {
                            f1 = f;
                        } else if (f2 == 0 && f1 != f) {
                            f2 = f;
                        }
                    }
                    ///////////////////////////////////////
                    y = getYFromFreq(freqT);
                    graphics.setColor(Color.red);
                    graphics.fillRect(x, y, barWidthPixelSize, freqDeltaHeight);
                }
            }

            //blank tone will be thought to be separation
            DTMFTone tone = DTMFTone.getDTMF(f1, f2);
            if (tone != null && (dtmfData.isEmpty() || lastTone == null)) {
                dtmfData.add(tone);
                System.out.println(tone);
                graphics.setFont(font);
                graphics.setColor(Color.BLACK);
                graphics.drawString(tone.toString(), x, getHeight());
                //                graphics.drawString(tone.toString(), x, timeLineArea.height + font.getSize());
            }

            lastTone = tone;

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

    private DTMFAnalysis(int w, int h) {

        setSize(w, h);
        //        setSpectrumSize(w, h);
        //        setPreferredSize(new Dimension(w, h));
        setFreqRange(200, 2000);

        data = new LinkedList<FFTData>();
        renderer = new SpectrumRenderer();

    }

    public void setPeakValve(float min) {
        minA = min;
        System.out.println(minA);
    }

    public int getMinYOfSpectrumArea() {
        return spectrumArea.y;
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

    public static DTMFAnalysis createInstance(int canvasW, int canvasH,
            int actualSampleSize, int sampleRate,
            float timeBegin, float timeEnd, float timeP,
            int freqBegin, int freqEnd,
            float valve

            ) {
        DTMFAnalysis canvas = new DTMFAnalysis(canvasW, canvasH);
        canvas.setTimeRange(timeBegin, timeEnd);
        canvas.setTimePrecision(timeP);
        canvas.setFreqRange(freqBegin, freqEnd);
        canvas.setSampleInfo(actualSampleSize, sampleRate);
        canvas.setPeakValve(valve);
        return canvas;
    }

    @Override
    public void afterAllProccesed() {}

    public static JFrame loadMainFrame(int frameW, int frameH, Component canvas) {

        final JFrame frame = new JFrame("DMTF Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setResizable(false);
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);
        frame.setVisible(true);
        return frame;
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        String fileToPlay = "SavedAudio8.wav";
        //GUI
        float timeBegin = 0f;
        float valve = (float)Tool.getNormalizedDataFromDB(-16);
        int freqBegin = 500;
        int freqEnd = 1500;
        int actualSampleSize = 256;

        int frameW = 800;
        int frameH = 600;
        //canvas size can larger than frame size
        //        int canvasW = 4300;
        int canvasW = frameW - 20;
        int canvasH = frameH - 50;
        ///////////////////////////////////////////////////////////////////////
        AudioInputStream ais = Tool.getAudisoInputStream(fileToPlay);
        if (ais == null) {
            return;
        }
        try {
            AudioFormat format = ais.getFormat();
            float timeEnd = Tool.getMusicTimeinSecond(ais);
            System.out.println("timeEnd " + timeEnd);

            float timeP = (timeEnd - timeBegin) / 10;
            //            timeP=0.1f;
            //GUI

            Collection<DigitalSignalProcessor> dsps = new LinkedList<DigitalSignalProcessor>();

            final DTMFAnalysis canvas = DTMFAnalysis.createInstance(canvasW, canvasH, actualSampleSize,
                    (int)format.getSampleRate(), timeBegin, timeEnd, timeP, freqBegin, freqEnd, valve);
            loadMainFrame(frameW, frameH, canvas);
            //data process
            dsps.add(canvas);
            SerialAudioConsumer.spectrumAnalyze(ais, actualSampleSize, timeBegin, timeEnd, dsps);
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
