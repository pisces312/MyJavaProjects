package com.ibm.nili.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestLargeCanvas {
    static class LargeCanvas extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 3063141473805076109L;

        public LargeCanvas() {
            //            setSize(2000, 2000);
            //            setv
            //            set
        }

        @Override
        public void paint(Graphics g) {
            //refresh background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(10, 10, getWidth() - 1, 300);

            //            super.paint(g);
        }
    }

    /**
     * @param args
     */
    public static void main(String [] args) {
        int frameW = 800;
        int frameH = 600;
        final JFrame frame = new JFrame("Spectrum Analyzer");
        //        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setResizable(false);

        LargeCanvas canvas = new LargeCanvas();
        JScrollPane scrollPane = new JScrollPane(canvas);
        canvas.setPreferredSize(new Dimension(2000, 2000));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //        scrollPane.set

        frame.add(scrollPane);
        frame.setVisible(true);

    }

}
