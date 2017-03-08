/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.TemplateMethod;

/**
 *
 * @author pisces312
 */
public class MyDisplay extends Display{

    @Override
    public void doDisplay() {
        System.out.println("It's my display!");
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void main(String[] args) {
        Display display=new MyDisplay();
        display.paint();
    }

}
