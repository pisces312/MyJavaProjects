/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.TemplateMethod;

/**
 *
 * @author pisces312
 */
public abstract class Display {
    public void paint(){
        System.out.println("setFocus");
        //在抽象类的普通函数中调用了抽象函数，而抽象函数需要在具体类中实现
        doDisplay();
        System.out.println("resetFocue");
    }
    public abstract void doDisplay();


}
