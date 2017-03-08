/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAlgorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author pisces312
 */
public class Sample1 implements Observer {

    public void update(Observable arg0, Object arg1) {
        RandomAlgorithm random = new RandomAlgorithm();
        int[] a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Graphics g = (Graphics) arg1;
        g.setColor(Color.BLACK);
        Rectangle rect = g.getClipBounds();
        int width = 640;// (int) rect.getWidth();
        int height = 480;//(int) rect.getHeight();
        g.drawLine(0, height / 2, width, height / 2);
        g.drawLine(width / 2, 0, width / 2, height);
        for (int i = 1; i <= 10000; i++) {
            int k = (int) (random.averageDistribution(0, 10) + 1);
            a[k]++;
        }
        int x = width / 2;
        for (int i = 1; i <= 10; i++, x += 10) {
            System.out.print(a[i] + "  ");
            int y = (height / 2 - a[i]/10);
            System.out.println(y);
//            System.out.println((height / 2 - a[i]) / 10000);
            g.drawLine(x, height / 2, x, y);
        }

//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
