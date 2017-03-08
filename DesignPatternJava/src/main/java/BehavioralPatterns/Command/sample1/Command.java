/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

/**
 *这里采用抽象类，DP中采用的interface
 * 命令接口，只有一个
 * 包含了接收者，类似代理，间接的调用了接收者中的函数
 * @author pisces312
 */
public abstract class Command {
    //命令里面包含了执行者的引用
   protected Barbecuer receiver;

    public Command(Barbecuer receiver) {
        this.receiver = receiver;
    }
    public abstract void excute();

}
