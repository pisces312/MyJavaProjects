/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Composite.Composite;

import StructuralPatterns.Composite.Component.Equipment;

/**
 *
 * @author pisces312
 */
public class CompositeEquipment extends Equipment {

    public CompositeEquipment(String name, float price) {
        super(name, false);
        this.price = price;
    }
}
