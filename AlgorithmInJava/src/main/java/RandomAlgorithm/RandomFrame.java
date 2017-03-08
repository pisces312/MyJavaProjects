/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAlgorithm;

import java.util.Observer;
import javax.swing.JFrame;

/**
 *
 * @author pisces312
 */
public class RandomFrame extends JFrame {

    RandomCanvas canvas;

    public RandomFrame(Observer observer) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        
        canvas = new RandomCanvas();
        add(canvas);
        canvas.setObserver(observer);
        canvas.update();
        
//        getContentPane().setLayout(arg0);
    }
}
