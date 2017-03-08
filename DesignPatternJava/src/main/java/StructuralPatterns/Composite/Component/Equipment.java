/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Composite.Component;

import java.util.LinkedList;

/**
 *
 * @author pisces312
 */
public abstract class Equipment {

    String name;
    protected boolean isLeaf;
    LinkedList<Equipment> list = new LinkedList();
    Equipment parent;
    protected float price;

    public Equipment(String name) {
        this.name = name;
        isLeaf = false;
    }

    public Equipment(String name, boolean isLeaf) {
        this.name = name;
        this.isLeaf = isLeaf;
    }

    public String getName() {
        return name;
    }

    public float getPrices() {
        //!!!!!!!!µÝ¹éµÄ¼ÆËã
        float sum = price;
        for (Equipment e : list) {
            sum += e.getPrices();
        }
        return sum;
    }
//    public float getPrice(){
//        return price;
//    }

    public void add(Equipment e) {
        if (isLeaf) {
            return;
        }
        System.out.println("add " + e.getName());
        list.add(e);
    }

    public void remove(Equipment e) {
        if (isLeaf) {
            return;
        }
        System.out.println("remove " + e.getName());
        list.remove(e);
    }
}
