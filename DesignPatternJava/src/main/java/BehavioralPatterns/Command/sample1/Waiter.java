/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

import java.util.LinkedList;

/**
 *�����ߣ������õ������������
 * �������ϲ��Ӧ���ṩһЩ����
 * @author pisces312
 */
public class Waiter {
    LinkedList<Command> orders=new LinkedList<Command>();
    public void addOrder(Command command){
        orders.add(command);
    }
    public void cancelOrder(Command command){
        orders.remove(command);
    }
    public void excute(){
        for(Command cmd:orders){
            cmd.excute();
        }
    }
    

}
