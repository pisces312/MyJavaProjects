/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;
import StructuralPatterns.Decorator.sample1usingInheritance.Decorator.Decorator;

/**
 *�൱��װ����
 * @author pisces312
 */
public class BorderDecorator extends Decorator{
    private int width;
    public BorderDecorator(VisualComponent component,int borderWidth) {
        super(component);
        width=borderWidth;
    }
    /**
     * Decorator�����������е�
     * @param width
     */
    public void drawBorder(int width){
        System.out.println("draw border");
    }

    @Override
    public void draw() {
        //�������������ȵ��ø���
        super.draw();
        drawBorder(width);

    }




}
