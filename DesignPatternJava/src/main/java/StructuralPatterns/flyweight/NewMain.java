/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.flyweight;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * 享元模式相当于维持一个对象池
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int extrinsicstate=22;
        FlyweightFactory f=new FlyweightFactory();
        Flyweight fx=f.getFlyweight("x");
        fx.operation(--extrinsicstate);
        Flyweight fy=f.getFlyweight("y");
        fy.operation(--extrinsicstate);
        Flyweight fz=f.getFlyweight("z");
        fz.operation(--extrinsicstate);

    }

}
