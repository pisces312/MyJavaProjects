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
public class DropShadowDecorator extends Decorator {

    int shadowWidth;

    public DropShadowDecorator(VisualComponent component, int shadowWidth) {
        super(component);
        this.shadowWidth = shadowWidth;
    }

    public void drawShadow(int shadowWidth) {
        System.out.println("draw shadow");
    }

    @Override
    public void draw() {
        super.draw();
        drawShadow(shadowWidth);

    }
}
