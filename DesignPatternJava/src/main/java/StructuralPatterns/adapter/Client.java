/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.adapter;

/**
 *Ӧ����ͳһ�ӿڵ����
 * @author pisces312
 */
public class Client {

    public static void main(String[] args) {
        //Ҫʹ�õ�Ŀ����������
        Target target=new Adapter();
        target.request();
    }
}
