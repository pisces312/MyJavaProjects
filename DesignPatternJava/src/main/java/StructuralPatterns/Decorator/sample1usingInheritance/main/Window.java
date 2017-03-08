/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Decorator.sample1usingInheritance.main;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;
import StructuralPatterns.Decorator.sample1usingInheritance.ConcreteComponent.TextView;
import StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator.BorderDecorator;
import StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator.DropShadowDecorator;
import StructuralPatterns.Decorator.sample1usingInheritance.ConcreteDecorator.ScrollDecorator;

/**
 *
 * @author pisces312
 */
public class Window {
//    private static int shadowWidth;
    VisualComponent component;

    public void setContents(VisualComponent component) {
        this.component = component;
    }

    public void draw() {
        component.draw();
    }

    public static void main(String[] args) {
        Window window = new Window();
        TextView textView = new TextView();
        int shadowWidth = 10;
        int scrollWidth = 10;
        int borderWidth = 10;
        window.setContents(new BorderDecorator(new ScrollDecorator(new DropShadowDecorator(textView, shadowWidth), scrollWidth), borderWidth));
        window.draw();
    }
}
