/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.SimpleFactory.sample1;

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
        Operation o1=OperationFactory.createOperation("add");
        o1.setNumA(1);
        o1.setNumB(2);
        System.out.println(o1.getResult());
        Operation o2=OperationFactory.createOperation("sub");
        o2.setNumA(2);
        o2.setNumB(1);
        System.out.println(o2.getResult());
        Operation o3=OperationFactory.createOperationUsingReflection("SubOperation");
        o3.setNumA(2);
        o3.setNumB(1);
        System.out.println(o3.getResult());
        
        
    }

}
