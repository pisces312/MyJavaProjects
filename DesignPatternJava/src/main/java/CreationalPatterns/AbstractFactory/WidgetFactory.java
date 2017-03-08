/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory;

/**
 *XXX应该使用接口，而不是抽象类，？？
 * @author pisces312
 */
public abstract class WidgetFactory {
    public abstract Window createWindow();
    public abstract ScrollBar createScrollBar();

}
