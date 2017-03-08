/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Decorator.sample1usingInterface.Decorator;

import StructuralPatterns.Decorator.sample1usingInterface.Component.VisualComponent;

/**
 *
 * @author pisces312
 */
public class Decorator implements  VisualComponent{
    private VisualComponent component;

    public Decorator(VisualComponent component) {
        this.component = component;
    }


    public void draw() {
        component.draw();
//        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void resize() {
        component.resize();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
