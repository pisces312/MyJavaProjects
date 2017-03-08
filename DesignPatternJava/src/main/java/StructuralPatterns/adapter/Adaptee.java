/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StructuralPatterns.adapter;

/**
 *
 * @author pisces312
 */
public class Adaptee {
    //这里的方法间接被client调用
    public void specificRequest(){
        System.out.println("specific request");
    }

}
