/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Command.sample1;

/**
 *客户端，最终的调用
 *
 * 命令模式将一个请求封装为一个对象 ，从而使你可使用不痛的请求对客户进行参数化
 * 对请求排队或记录请求日志，以及支持可撤销操作
 * 应用：
 * undo撤销，redo恢复操作
 *
 *
 * 命令模式里边一般都有以下几个角色：客户端，请求者，命令接口，命令实现，接受者,
 * @author pisces312
 */
public class Client {

    public static void main(String[] args) {
        Waiter girl = new Waiter();
        Barbecuer boy = new Barbecuer();
        girl.addOrder(new BakeChickenWingCommand(boy));
        girl.addOrder(new BakeMuttonCommand(boy));
        girl.excute();
    }
}
