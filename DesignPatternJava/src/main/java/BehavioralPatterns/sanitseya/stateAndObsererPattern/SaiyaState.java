/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.sanitseya.stateAndObsererPattern;

/**
 *状态抽象类
 * 在具体实现类中含有状态变化的信息，即在当前状态下的下一个状态是什么
 * @author pisces312
 */
public abstract class SaiyaState {

    protected Saiya saiya;

    public SaiyaState(Saiya saiya) {
        this.saiya = saiya;
    }

    public String status() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
    //星矢被打了
    public abstract void hit();
}


