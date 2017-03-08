/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.SubOperation;
import CreationalPatterns.FactoryMethod.Product.Operation;

/**
 *
 * @author pisces312
 */
public class SubFactory implements OperationFactory{

    public Operation createOperation() {
        return new SubOperation();
    }

}
