/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.SimpleFactory.sample1;

/**
 *
 * @author pisces312
 */
public abstract class Operation {
    double numA,numB;

//    public Operation(double numA, double numB) {
//        this.numA = numA;
//        this.numB = numB;
//    }

    public void setNumA(double numA) {
        this.numA = numA;
    }

    public void setNumB(double numB) {
        this.numB = numB;
    }
    
    public abstract double getResult();
}
