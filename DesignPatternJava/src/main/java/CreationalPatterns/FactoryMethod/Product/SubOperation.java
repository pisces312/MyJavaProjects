/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.FactoryMethod.Product;

import CreationalPatterns.FactoryMethod.Product.Operation;

/**
 *
 * @author pisces312
 */
public class SubOperation extends Operation{
//    public SubOperation(double numA,double numB) {
//        super(numA, numB);
//    }
    @Override
    public double getResult() {
        return numA-numB;
    }

}
