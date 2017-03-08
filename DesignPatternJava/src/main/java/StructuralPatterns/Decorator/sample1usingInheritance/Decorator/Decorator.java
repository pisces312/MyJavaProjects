/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Decorator.sample1usingInheritance.Decorator;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;

/**
 *
 * @author pisces312
 */
public class Decorator extends VisualComponent{
    private VisualComponent component;

    public Decorator(VisualComponent component) {
        this.component = component;
    }

    @Override
    public void draw() {
        component.draw();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resize() {
        component.resize();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
