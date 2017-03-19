/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.Operation;

//Only support one kind of production
public interface OperationFactory {

	//A public method to create one instance
    public Operation createOperation();
}
