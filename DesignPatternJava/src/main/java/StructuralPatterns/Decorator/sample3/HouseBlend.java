package StructuralPatterns.Decorator.sample3;/*
 * HouseBlend.java
 *
 * Created on 2007年9月26日, 下午11:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author pisces312
 */
public class HouseBlend extends Beverage{
    
    /** Creates a new instance of HouseBlend */
    public HouseBlend() {
    }
    public double cost() {
        System.out.println("HouseBlend");
        return 1.2;
    }
    
}
