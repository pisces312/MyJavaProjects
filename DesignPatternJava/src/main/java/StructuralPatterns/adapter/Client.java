/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.adapter;

/**
 *应用于统一接口的情况
 * @author pisces312
 */
public class Client {

    public static void main(String[] args) {
        //要使用的目标对象的引用
        Target target=new Adapter();
        target.request();
    }
}
