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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * a user interface component to show an amplitude value
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the
 *         GNU General Public License,
 *         for details see file license.txt in the distribution
 *         package of this software
 * 
 * @version 1, January 20, 2005
 */

public class LevelMeter {

    /**
     * constructor
     * 
     * @param bi BufferedImage the offscreen image to use for painting
     * @param index int index inside a group of level meters
     * @param width int width of this level meter
     * @param height int height of this level meter
     * @param min int minimum value to display by this level meter
     * @param max int maximum value to display by this level meter
     */
    public LevelMeter(BufferedImage bi, int index, int width, int height, int min,
            int max) {
        this.bi = bi;
        this.index = index;

        setSize(width, height);

        this.min = min;
        this.max = max;
        sideMargin = barGap / 2;
    }

    /**
     * set the size of this component
     * 
     * @param width int the width
     * @param height int the height
     */
    public void setSize(int width, int height) {
        if (width > 0 && height > 0) {
            this.outerWidth = width;
            this.meterHeight = height;
            meterWidth = outerWidth - barGap;
        }
    }

    /**
     * repaint this component
     * 
     * @param value double the decibel value to display
     */
    private void repaintMeter(double value) {
        if (value > min && value < max) {
            double oldValue = meterValue;
            if (oldValue != value) {
                //paintValue = oldValue;
                meterValue = value;
                if (bi != null) {
                    Graphics2D g2 = bi.createGraphics();
                    if (oldValue < value) {
                        increaseMeter(g2, value);
                    }
                    else {
                        reduceMeter(g2, value);
                    }
                    g2.dispose();
                }
            }
        }
    }

    /**
     * set the decibel value this component should display
     * 
     * @param value double the decibel value to display
     */
    public void setValue(double value) {
        repaintMeter(value);
    }

    /**
     * set the width of this component
     * 
     * @param width int the width
     */
    public void setWidth(int width) {
        this.outerWidth = width;
    }

    /**
     * paint this level meter without showing a value
     */
    public void paintInitially() {
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.BLACK);
        int x = outerWidth * index;
        int y = meterHeight;
        while (y >= 0) {
            g2.drawLine(x + sideMargin, y, x + meterWidth, y);
            y -= barSegmentStep;
        }
        x += outerWidth;
        meterPosY = meterHeight;
        g2.dispose();
    }

    /**
     * set the offscreen image this component should draw to
     * 
     * @param bi BufferedImage the offscreen image to draw to
     */
    public void setOffscreenImage(BufferedImage bi) {
        this.bi = bi;
    }

    /**
     * increase the level meter to accommodate a new decibel value
     * 
     * @param g2 Graphics2D the graphics context to paint to
     * @param newValue double the new (higher) decibel value to display
     */
    private void increaseMeter(Graphics2D g2, double newValue) {
        int x = outerWidth * index;
        int valueY = meterHeight - (int)newValue; //calcY(newValue);
        //System.out.println("increaseBar newValue=" + newValue + ", valueY=" + valueY);
        int y = meterPosY;
        g2.setColor(Color.GREEN);
        while (y > valueY) {
            g2.drawLine(x + sideMargin, y, x + meterWidth, y);
            meterPosY = y;
            y -= barSegmentStep;
        }
    }

    /**
     * reduce the level meter to accommodate a new decibel value
     * 
     * @param g2 Graphics2D the graphics context to paint to
     * @param newValue double the new (lower) decible value to display
     */
    private void reduceMeter(Graphics2D g2, double newValue) {
        int x = outerWidth * index;
        int valueY = meterHeight - (int)newValue; //calcY(newValue);
        //System.out.println("reduceBar newValue=" + newValue + ", valueY=" + valueY);
        int y = meterPosY;
        g2.setColor(Color.BLACK);
        while (y < valueY) {
            g2.drawLine(x + sideMargin, y, x + meterWidth, y);
            meterPosY = y;
            y += barSegmentStep;
        }
    }

    private BufferedImage bi;

    /** room between level meters in pixels */
    private int           barGap         = 2;
    /** room between segments of this level meter in pixels */
    private int           barSegmentStep = 2;
    /** the decibel value this level meter currently displays */
    private double        meterValue;
    /** the Y position of the current meter value */
    private int           meterPosY;
    /**
     * width of a level meter in pixels including the room between two level
     * meters
     */
    private int           outerWidth;
    /** margin width in pixels at each side of a level meter */
    private int           sideMargin;
    /**
     * width in pixels of a level meter without the room between two
     * level meters (the width of segments of a level meter)
     */
    private int           meterWidth;
    /** height in pixels */
    private int           meterHeight;
    /** minimum value */
    private int           min;
    /** maximum value */
    private int           max;
    /** index of this meter in a range of meters */
    private int           index;

    //double paintValue;
}
