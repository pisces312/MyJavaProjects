/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.Operation;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //��ͬ�ڼ򵥹�������������ȴ���һ��������ʵ��
        OperationFactory factory=new AddFactory();
        Operation o1=factory.createOperation();
        o1.setNumA(1);
        o1.setNumB(2);
        System.out.println(o1.getResult());
    }

}
