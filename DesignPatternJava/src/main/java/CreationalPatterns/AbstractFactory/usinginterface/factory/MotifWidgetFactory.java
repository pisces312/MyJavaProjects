/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory.usinginterface.factory;

import CreationalPatterns.AbstractFactory.usinginterface.product.ScrollBar;
import CreationalPatterns.AbstractFactory.usinginterface.product.MotifWindow;
import CreationalPatterns.AbstractFactory.usinginterface.product.Window;
import CreationalPatterns.AbstractFactory.usinginterface.product.MotifScrollBar;

/**
 *������Ĺ����Ե���ģʽʵ��
 * @author pisces312
 */
public class MotifWidgetFactory implements WidgetFactory{
    private static MotifWidgetFactory factory=new MotifWidgetFactory();
    private MotifWidgetFactory(){
    }
    public static MotifWidgetFactory getFactory(){
        return factory;
    }

    public Window createWindow() {
        return new MotifWindow();
    }


    public ScrollBar createScrollBar() {
        return new MotifScrollBar();
    }

}
