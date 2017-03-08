/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

import java.util.LinkedList;

/**
 *请求者，即调用到具体命令的类
 * 这里向上层的应用提供一些方法
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
