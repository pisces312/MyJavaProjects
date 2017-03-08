/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory;

/**
 *
 * @author pisces312
 */
public class PMWidgetFactory extends WidgetFactory{

    @Override
    public Window createWindow() {
        return new PMWindow();
    }

    @Override
    public ScrollBar createScrollBar() {
        return new PMScrollBar();
    }

}
