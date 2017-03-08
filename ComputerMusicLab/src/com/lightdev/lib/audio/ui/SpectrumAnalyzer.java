/*
 * Light Development Spectrum Analyzer Component
 * Copyright (C) 2003-2005 Ulrich Hilger
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.lightdev.lib.audio.ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kj.dsp.KJDigitalSignalProcessor;
import kj.dsp.KJFFT;

import com.lightdev.lib.audio.Player;
import com.lightdev.lib.audio.PlayerListener;

/**
 * A user interface component to display the sound pressure for a range of
 * frequencies
 * over time as produced by an audio data stream.
 * 
 * <p>
 * This is the second version of the spectrum analyzer component. It has
 * significantly improved performance with the help of the KJ signal processor
 * package by Kris Fudalewski. KJ signal processor synchronizes processed bytes
 * with the audio output.
 * </p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author KJ signal processor by Kris Fudalewski at <a
 *         href="http://sirk.sytes.net/">http://sirk.sytes.net/</a>
 * @author published under the terms and conditions of the
 *         GNU General Public License,
 *         for details see file license.txt in the distribution
 *         package of this software
 * 
 * @version 2, January 20, 2005
 */

public class SpectrumAnalyzer extends Canvas implements PlayerListener, KJDigitalSignalProcessor
{

    /**
     * constructor
     * 
     * @param freqCount int number of level meters to show for one channel (i.e.
     *            number of
     *            frequencies to measure amplitudes for)
     * @param expFftSize int the power of two to use to set the size of a fast
     *            fourier transform.
     *            When expFftSize is 8 for instance the fft will be of size 2^8
     *            = 256,
     *            i.e. the fft will transform 256 values of the amplitude/time
     *            domain to
     *            128 values of the amplitude/frequency domain.
     */
    public SpectrumAnalyzer(int freqCount, int expFftSize)
    {
        super();
        this.frequencyCount = freqCount;
        initComponent(expFftSize);
    }

    /**
     * initialze some vital values of this component upon creation
     * 
     * @param expFftSize int the size of a fast fourier transform expressed as a
     *            power of two
     */
    private void initComponent(int expFftSize) {
        fftSize = 1 << expFftSize;
        System.out.println("fftSize:" + fftSize);
        fftResultSize = fftSize / 2;
        fBandIx = new int[frequencyCount];
        meters = new LevelMeter[frequencyCount];
        createMeters();
        fft = new KJFFT(fftSize);
        lastY = new float[frequencyCount];
    }

    /* ------------------------ paint part start --------------------------- */

    /**
     * paint this component to the screen
     */
    private void paintScreen()
    {
        initOffscreenImage(getWidth(), getHeight());
        Graphics g = getGraphics();
        if ((g != null) && (bi != null)) {
            g.drawImage(bi, 0, 0, null);
            g.dispose();
        }
    }

    /**
     * initialize the image that is used by this component for
     * offscreen in-memory painting
     * 
     * @param w int the width of the offscreen image
     * @param h int the height of the offscreen image
     */
    private void initOffscreenImage(int w, int h) {
        if (firstTime && w > 0 && h > 0) {
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setColor(getBackground());
            g2.fillRect(0, 0, w, h);
            createMeters();
            paintMetersInitially();
            g2.dispose();
            firstTime = false;
        }
    }

    /**
     * Paints this component.
     * 
     * @param g the graphics context to use for painting
     */
    public void paint(Graphics g) {
        paintScreen();
    }

    /**
     * draw level meters as they appear before values are displayed
     */
    private void paintMetersInitially() {
        for (int i = 0; i < frequencyCount; i++) {
            meters[i].paintInitially();
        }
    }

    /**
     * set the size of this component
     * 
     * @param d Dimension the new size
     */
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    /**
     * set the size of this component
     * 
     * @param w int the width
     * @param h int the height
     */
    public void setSize(int w, int h) {
        if (w > 0 && h > 0) {
            super.setSize(w, h);
            if (myWidth != w || myHeight != h) {
                //System.out.println("setSize w=" + w + ", h=" + h);
                myWidth = w;
                myHeight = h;
                firstTime = true;
                initOffscreenImage(w, h);
            }
        }
    }

    /**
     * create an array of level meters suitable to display amplitude values
     * of this spectrum analyzer
     */
    private void createMeters() {
        int mWidth = getWidth() / frequencyCount;
        int mHeight = getHeight();
        for (int i = 0; i < frequencyCount; i++) {
            meters[i] = new LevelMeter(bi, i, mWidth, mHeight, minAmp, maxAmp);
        }
    }

    /**
     * find out which frequencies belong to which band (i.e. level meter)
     * based on the hearable audio spectrum and the number of meters
     */
    private void initFrequencyBandIndices() {
        double factor = Math.pow(
                16000 / 20, (double)((double)1 / (
                (double)((double)frequencyCount - (double)1))));
        int fMin = 0;
        for (int i = 0; i < frequencyCount; i++) {
            int groupMin = (int)(20 * Math.pow(factor, i));
            for (int f = fMin; f < frequencies.length; f++) {
                if (frequencies[f] >= groupMin) {
                    fBandIx[i] = f;
                    //System.out.println("frequencies[" + f+ "]=" + frequencies[f] +
                    //                 ", fBandIx[" + i + "]=" + fBandIx[i]);
                    fMin = f;
                    break;
                }
            }
        }
    }

    /* ------------------------ paint part end ----------------------------- */

    /* ---------------------- player listener implementation start -------------- */

    /**
     * take stateChanged messages from the audio player as a trigger to
     * start and stop the spectrum analyzer
     * 
     * @param p Player the player whose status changed
     * @param fromStatus int the status changed from
     * @param toStatus int the status changed to
     */
    public void stateChanged(Player p, int fromStatus, int toStatus) {
        switch (toStatus) {
            case Player.PLAYING:
                frequencies = new int[fftResultSize];
                for (int i = 0; i < fftResultSize; i++) {
                    frequencies[i] = (int)(i * p.getSampleRate() / fftSize);
                }
                initFrequencyBandIndices();
                break;

            case Player.STOPPED_EOM:
                suspend();
                break;
            case Player.STOPPED_PLAY:
                suspend();
                break;
            case Player.PAUSED:
                suspend();
                break;
        }
    }

    /**
     * reset display
     */
    private void suspend() {
        paintMetersInitially();
        paintScreen();
    }

    /**
     * unused here
     * 
     * @param p Player the player that fired the event
     * @param progressValue long the microseconds played so far
     */
    public void progress(Player p, long progressValue) {}

    /**
     * unused here
     * 
     * @param p Player the player that fired the event
     * @param ex Exception the exception that occurred
     */
    public void exception(Player p, Exception ex) {}

    /**
     * unused here
     * 
     * @param p Player the player that fired the event
     * @param buf byte[] the decoded bytes
     * @param byteCount int the number of bytes in buf
     */
    public void decoded(Player p, byte [] buf, int byteCount) {}

    /* ---------------------- player listener implementation end -------------- */

    /* ---------------------- KJDigitalSignalProcessor implementation start ------------- */

    /**
     * process a range of samples received from the KJ signal processor, i.e.
     * repaint
     * level meters
     * 
     * @param samples float[] the samples to process
     */
    public void process(float [] samples) {
        float resultValues[] = fft.calculate(samples);
        int k = frequencyCount;
        int amplitudeIndex = 0;
        int meterIndex = 0;
        while (meterIndex < frequencyCount) {
            float y = 0f;
            while (amplitudeIndex < fBandIx[meterIndex]) {
                y += resultValues[amplitudeIndex++];
            }
            y = y * 32f * (float)Math.log(k);
            if (y > 48f) {
                y = 48f;
            }
            if (y > lastY[meterIndex]) {
                lastY[meterIndex] = y;
            }
            else {
                lastY[meterIndex] -= 2f;
                if (lastY[meterIndex] < 0f) {
                    lastY[meterIndex] = 0f;
                }
                y = lastY[meterIndex];
            }
            //            System.out.println("meter" + meterIndex + " value:" + y);
            meters[meterIndex++].setValue(y);
            --k;
        }
//        System.out.println("------------------------------");
        paintScreen();
    }

    /* ---------------------- KJDigitalSignalProcessor implementation end --------------- */

    /* ---------------------- class fields start -------------- */

    /** the value painted last */
    private float []      lastY;
    /** the BufferedImage of this SpectrumAnalyzer */
    private BufferedImage bi;
    /** indicates whether or not a paint process runs for the first time */
    private boolean       firstTime = true;
    /** the level meters of this SpectrumAnalyzer */
    private LevelMeter [] meters;
    /**
     * the frequencies (Hz) of the results of one fast fourier transform in the
     * current fft size
     */
    private int []        frequencies;
    /** indices of result per frequency band */
    private int []        fBandIx;
    /**
     * number of level meters to show for one channel (i.e. number of
     * frequencies to measure amplitudes for)
     */
    private int           frequencyCount;
    /** the minimum amplitude (db) to show in level meters */
    private int           minAmp    = 0;
    /** the maximum amplitude (db) to show in level meters */
    private int           maxAmp    = 150;
    /** the number of values to transform in a fast fourier transform (fft) */
    private int           fftSize;
    /** the number of results produced by a fast fourer transform (fft) */
    private int           fftResultSize;
    /** the object to use to do fast fourier transforms */
    private KJFFT         fft;
    /** this component's width */
    private int           myWidth   = 0;
    /** this component's height */
    private int           myHeight  = 0;

    @Override
    public void process(float [] pLeftChannel, float [] pRightChannel, float pFrameRateRatioHint) {
        process(pLeftChannel);

    }

    /* ---------------------- class fields end -------------- */

}
