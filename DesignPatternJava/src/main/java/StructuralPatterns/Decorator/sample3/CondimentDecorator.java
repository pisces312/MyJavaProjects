package StructuralPatterns.Decorator.sample3;/*
 * CondimentDecorator.java
 *
 * Created on 2007年9月26日, 下午11:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author pisces312
 */
public class CondimentDecorator extends Beverage{
    public Beverage beverage;
//    abstract Beverage beverage;
    /** Creates a new instance of CondimentDecorator */
    public CondimentDecorator() {
    }

    public double cost() {
        return beverage.cost();
    }
    
}
