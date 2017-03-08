/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.sanitseya.stateAndObsererPattern;

import java.util.Observable;
import java.util.Observer;

/**
 *实现观察者接口
 * @author pisces312
 */
public class Athena implements Observer {

    /* 我是雅典娜 我是观察者
     *
     */
    public void update(Observable arg0, Object arg1) {
        System.out.println("雅典娜说：星矢加油啊！！！");
    }
}