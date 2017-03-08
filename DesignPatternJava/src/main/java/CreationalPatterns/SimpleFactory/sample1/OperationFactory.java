/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.SimpleFactory.sample1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *�򵥹�����һ��ֻ��һ����̬�������ڲ���ѡ�����ʵ��
 * @author pisces312
 */
public class OperationFactory {

    /**
     * ������static
     * ѡ�����
     * ʹ�ò�����ѡ�����ʵ�����Ķ���
     * @param opr
     * @return
     */
    public static Operation createOperation(String opr) {
        if (opr.equals("add")) {
            return new AddOperation();
        } else if (opr.equals("sub")) {
            return new SubOperation();
        }
        return null;
    }

    /**
     * ������static
     * ʹ�÷���
     * @param opr
     * @return
     */
    public static Operation createOperationUsingReflection(String className) {
        try {
//            return (Operation) Class.forName(opr,true,ClassLoader.getSystemClassLoader()).newInstance();
            return (Operation) Class.forName(OperationFactory.class.getName().replaceAll("OperationFactory", className)).newInstance();
//            return (Operation) Class.forName(OperationFactory.class.getPackage()+"."+opr,true,OperationFactory.class.getClassLoader()).newInstance();
//        if(opr.equals("add")){
//            return new AddOperation();
//        }else if(opr.equals("sub")){
//            return new SubOperation();
//        }
//        return null;
        } catch (InstantiationException ex) {
            Logger.getLogger(OperationFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OperationFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperationFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if(opr.equals("add")){
//            return new AddOperation();
//        }else if(opr.equals("sub")){
//            return new SubOperation();
//        }
        return null;
    }
}
