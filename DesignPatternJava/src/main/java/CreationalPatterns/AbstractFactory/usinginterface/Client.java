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
        //ֻ��һ����Ҫָ��ʹ�����ĸ�����
//        WidgetFactory factory=new MotifWidgetFactory();
                WidgetFactory factory=MotifWidgetFactory.getFactory();
//        factory=new PMWidgetFactory();
        //������벻��
        Window window=factory.createWindow();
        ScrollBar scrollBar=factory.createScrollBar();
    }

}
