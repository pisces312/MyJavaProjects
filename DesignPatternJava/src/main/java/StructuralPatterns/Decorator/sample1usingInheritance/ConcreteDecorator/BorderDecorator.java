/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;
import StructuralPatterns.Decorator.sample1usingInheritance.Decorator.Decorator;

/**
 *相当于装饰物
 * @author pisces312
 */
public class BorderDecorator extends Decorator{
    private int width;
    public BorderDecorator(VisualComponent component,int borderWidth) {
        super(component);
        width=borderWidth;
    }
    /**
     * Decorator具体类所特有的
     * @param width
     */
    public void drawBorder(int width){
        System.out.println("draw border");
    }

    @Override
    public void draw() {
        //！！这里总是先调用父类
        super.draw();
        drawBorder(width);

    }




}
