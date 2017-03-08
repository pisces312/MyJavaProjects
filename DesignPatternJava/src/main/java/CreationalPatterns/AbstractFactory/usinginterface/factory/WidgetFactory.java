/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.AbstractFactory.usinginterface.factory;

import CreationalPatterns.AbstractFactory.usinginterface.product.ScrollBar;
import CreationalPatterns.AbstractFactory.usinginterface.product.Window;

/**
 *
 * @author pisces312
 */
public interface WidgetFactory {

    public Window createWindow();

    public ScrollBar createScrollBar();
}
