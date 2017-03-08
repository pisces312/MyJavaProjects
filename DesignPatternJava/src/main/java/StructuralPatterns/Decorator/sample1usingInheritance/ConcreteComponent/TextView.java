/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Decorator.sample1usingInheritance.ConcreteComponent;

import StructuralPatterns.Decorator.sample1usingInheritance.Component.VisualComponent;


/**
 *被包装的对象！！
 * @author pisces312
 */
public class TextView extends  VisualComponent{

    @Override
    public void draw() {
        System.out.println("draw textview");
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resize() {
        System.out.println("textview resize");
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
