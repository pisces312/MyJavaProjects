/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.adapter;

/**
 *
 * @author pisces312
 */
public class Adapter extends Target{
    Adaptee adaptee;

    public Adapter() {
        adaptee=new Adaptee();
    }

    @Override
    public void request() {
        System.out.println("通过适配器调用");
        adaptee.specificRequest();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
