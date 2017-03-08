package StructuralPatterns.Decorator.sample3;/*
 * Mocha.java
 *
 * Created on 2007年9月26日, 下午11:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author pisces312
 */
public class Mocha extends Beverage{
    Beverage beverage;
    /** Creates a new instance of Mocha */
    public Mocha(Beverage beverage) {
        this.beverage=beverage;
    }

    public double cost() {
        System.out.println("Mocha");
        return 0.5+beverage.cost();
    }
    
}
