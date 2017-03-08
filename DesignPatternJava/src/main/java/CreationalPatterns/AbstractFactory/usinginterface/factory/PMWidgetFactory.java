/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.AbstractFactory.usinginterface.factory;

import CreationalPatterns.AbstractFactory.usinginterface.product.ScrollBar;
import CreationalPatterns.AbstractFactory.usinginterface.product.Window;
import CreationalPatterns.AbstractFactory.usinginterface.product.PMWindow;
import CreationalPatterns.AbstractFactory.usinginterface.product.PMScrollBar;

/**
 *������Ĺ����Ե���ģʽʵ��
 * @author pisces312
 */
public class PMWidgetFactory implements WidgetFactory {

    private static PMWidgetFactory factory = new PMWidgetFactory();

    private PMWidgetFactory() {
    }

    public static PMWidgetFactory getFactory() {
        return factory;
    }

    public Window createWindow() {
        return new PMWindow();
    }

    public ScrollBar createScrollBar() {
        return new PMScrollBar();
    }
}
