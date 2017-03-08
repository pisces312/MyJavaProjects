/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.SimpleFactory.sample1;

/**
 *
 * @author pisces312
 */
public class AddOperation extends Operation{

//    public AddOperation(double numA,double numB) {
//        super(numA, numB);
//    }


    @Override
    public double getResult() {
        return numA+numB;
    }

}
