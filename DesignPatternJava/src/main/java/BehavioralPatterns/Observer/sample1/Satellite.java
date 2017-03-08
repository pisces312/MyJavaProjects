/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Observer.sample1;

import java.util.Observable;
import java.util.Observer;

/**
 *  观察对象：气象卫星
 *
 *    @author    zjun
 *    @version    1.0  create  on  2006-5-18  9:46:30
 */
public class Satellite implements Observer {

    private String weather;

    public void update(Observable obj, Object arg) {
        weather = (String) arg;

        //    捕获天气变化情况，反馈给检测者
        System.out.println("  近期天气变化：  " + weather);
    }
}

