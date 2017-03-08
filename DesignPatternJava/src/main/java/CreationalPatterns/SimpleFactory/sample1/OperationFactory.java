/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.SimpleFactory.sample1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *简单工厂中一般只有一个静态方法，内部用选择语句实现
 * @author pisces312
 */
public class OperationFactory {

    /**
     * 必须用static
     * 选择语句
     * 使用参数来选择具体实例化的对象
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
     * 必须用static
     * 使用反射
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
