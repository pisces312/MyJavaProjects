package StructuralPatterns.Decorator.sample3;

/*
 * BeverageMain.java
 *
 * Created on 2007年9月26日, 下午11:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author pisces312
 */
public class BeverageMain {

    /** Creates a new instance of BeverageMain */
    public BeverageMain() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Beverage b = new DarkRoast();
        b = new Mocha(b);
        b = new Whip(b);
        b.cost();
    }
}
