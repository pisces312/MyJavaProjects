package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.SubOperation;
import CreationalPatterns.FactoryMethod.Product.Operation;

//A factory for "-" operation
public class SubFactory implements OperationFactory {

    public Operation createOperation() {
        return new SubOperation();
    }

}
