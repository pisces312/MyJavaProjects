/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory;

/**
 *
 * @author pisces312
 */
public class MotifWidgetFactory extends WidgetFactory{

    @Override
    public Window createWindow() {
        return new MotifWindow();
    }

    @Override
    public ScrollBar createScrollBar() {
        return new MotifScrollBar();
    }

}
