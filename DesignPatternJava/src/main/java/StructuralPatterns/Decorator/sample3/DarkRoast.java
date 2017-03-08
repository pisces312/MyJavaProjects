package StructuralPatterns.Decorator.sample3;/*
 * DarkRoast.java
 *
 * Created on 2007年9月26日, 下午11:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author pisces312
 */
public class DarkRoast extends Beverage{
    
    /** Creates a new instance of DarkRoast */
    public DarkRoast() {
    }

    public double cost() {
        System.out.println("DarkRoast");
        return 1;
    }
    
}
