/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.AbstractFactory.usinginterface.product;

/**
 *
 * @author pisces312
 */
public class MotifScrollBar implements  ScrollBar{

    public MotifScrollBar() {
        System.out.println("generate a Motif scroll bar");
    }

}
