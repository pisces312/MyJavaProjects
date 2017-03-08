/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory.usinginterface;

import CreationalPatterns.AbstractFactory.usinginterface.factory.WidgetFactory;
import CreationalPatterns.AbstractFactory.usinginterface.factory.MotifWidgetFactory;
import CreationalPatterns.AbstractFactory.usinginterface.product.ScrollBar;
import CreationalPatterns.AbstractFactory.usinginterface.product.Window;

/**
 *
 * @author pisces312
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //只有一句需要指明使用了哪个工厂
//        WidgetFactory factory=new MotifWidgetFactory();
                WidgetFactory factory=MotifWidgetFactory.getFactory();
//        factory=new PMWidgetFactory();
        //下面代码不变
        Window window=factory.createWindow();
        ScrollBar scrollBar=factory.createScrollBar();
    }

}
