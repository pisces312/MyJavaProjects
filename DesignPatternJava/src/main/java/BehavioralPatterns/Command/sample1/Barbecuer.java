/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

/**
 *接收者，实现所有命令的具体动作
 * @author pisces312
 */
public class Barbecuer {
    public void bakeMutton(){
        System.out.println("烤羊肉");
    }
    public void bakeChickenWing(){
        System.out.println("烤鸡翅");
    }

}
