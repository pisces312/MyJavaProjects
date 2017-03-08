/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAlgorithm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observer;

/**
 *
 * @author pisces312
 */
public class RandomCanvas extends Canvas {

    Image buffer;
    Graphics g;
    Observer observer;

    public RandomCanvas() {
        setSize(640, 480);
        
        //修改屏幕的相对坐标！！xxx
//        setLocation(320, 240);
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g = buffer.getGraphics();
        //转换坐标
//        g.translate(WIDTH, WIDTH);
//        setLocation(WIDTH, WIDTH);
        draw();
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void update() {
        observer.update(null, g);
        repaint();
    }

    public void draw() {
        System.out.println("draw");
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.BLACK);
//        g.drawString("canvas", getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);

    }
}
