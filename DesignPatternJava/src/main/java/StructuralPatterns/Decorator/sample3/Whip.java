package StructuralPatterns.Decorator.sample3;/*
 * Whip.java
 *
 * Created on 2007年9月26日, 下午11:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author pisces312
 */
public class Whip extends Beverage{
    Beverage beverage;
    /** Creates a new instance of Whip */
    public Whip(Beverage beverage) {
        this.beverage=beverage;
    }

    public double cost() {
        System.out.println("Whip");
        return 0.4+beverage.cost();
    }
    
}
