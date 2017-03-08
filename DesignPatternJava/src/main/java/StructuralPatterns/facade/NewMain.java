/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.facade;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Facade facade=new Facade();
        facade.operation1();
        facade.operation2();
    }

}
