/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;
import StructuralPatterns.Decorator.sample1usingInheritance.Decorator.Decorator;

/**
 *
 * @author pisces312
 */
public class ScrollDecorator extends Decorator {

    int scrollWidth;

    public ScrollDecorator(VisualComponent component, int scrollWidth) {
        super(component);
        this.scrollWidth = scrollWidth;
    }
    public void drawScroll(int scrollWidth){
        System.out.println("draw scroll");
    }

    @Override
    public void draw() {
        super.draw();
        drawScroll(scrollWidth);
    }

}
