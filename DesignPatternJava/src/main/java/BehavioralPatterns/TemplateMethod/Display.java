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
        //�ڳ��������ͨ�����е����˳�����������������Ҫ�ھ�������ʵ��
        doDisplay();
        System.out.println("resetFocue");
    }
    public abstract void doDisplay();


}
