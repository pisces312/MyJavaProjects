/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WidgetFactory factory=new MotifWidgetFactory();
        factory=new PMWidgetFactory();
        Window window=factory.createWindow();
        ScrollBar scrollBar=factory.createScrollBar();
    }

}
