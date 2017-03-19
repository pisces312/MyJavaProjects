package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.AddOperation;
import CreationalPatterns.FactoryMethod.Product.Operation;

//A factory for "+" operation
public class AddFactory implements OperationFactory{

    public Operation createOperation() {
        return new AddOperation();
    }

}
