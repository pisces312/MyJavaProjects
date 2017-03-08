/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.facade;

/**
 *相当于同时包含多个类的对象引用，将其相互间的调用进行封装
 * 然后提供给上层使用，隐藏了下层的实现和不必要的类之间的依赖关系
 * @author pisces312
 */
public class Facade {

    SubSystem1 subSystem1;
    SubSystem2 subSystem2;
    SubSystem3 subSystem3;
    SubSystem4 subSystem4;

    public Facade() {
        subSystem1 = new SubSystem1();
        subSystem2 = new SubSystem2();
        subSystem3 = new SubSystem3();
        subSystem4 = new SubSystem4();
    }

    public void operation1() {
        System.out.println("begin invoke operation1");
        subSystem1.method1();
        subSystem2.method2();
        subSystem4.method4();
        System.out.println("end invoke operation1\n");
    }

    public void operation2() {
        System.out.println("begin invoke operation2");
        subSystem2.method2();
        subSystem3.method3();
        subSystem4.method4();
        System.out.println("end invoke operation2\n");
    }
}
