/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.flyweight;

/**
 *
 * @author pisces312
 */
public class UnsharedConcreteFlyweight extends Flyweight{

    @Override
    public void operation(int extrinsicstate) {
        System.out.println("������ľ���Flyweight"+extrinsicstate);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
