/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Decorator.sample1usingInterface.ConcreteComponent;

import StructuralPatterns.Decorator.sample1usingInterface.Component.VisualComponent;

/**
 *����װ�Ķ��󣡣�
 * @author pisces312
 */
public class TextView implements VisualComponent {


    public void draw() {
        System.out.println("draw textview");
//        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void resize() {
        System.out.println("textview resize");
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
