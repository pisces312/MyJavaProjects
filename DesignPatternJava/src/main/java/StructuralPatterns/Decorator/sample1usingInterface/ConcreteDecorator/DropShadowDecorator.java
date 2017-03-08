/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Decorator.sample1usingInterface.ConcreteDecorator;

import StructuralPatterns.Decorator.sample1usingInterface.Component.VisualComponent;
import StructuralPatterns.Decorator.sample1usingInterface.Decorator.Decorator;

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
