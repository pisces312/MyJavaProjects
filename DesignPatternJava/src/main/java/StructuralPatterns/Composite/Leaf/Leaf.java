/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.Composite.Leaf;

import StructuralPatterns.Composite.Component.Equipment;

/**
 *
 * @author pisces312
 */
public class Leaf extends Equipment{

    public Leaf(String name,float price) {
        super(name,true);
        this.price=price;
    }

}
