/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Observer.sample1;

import java.util.Observable;
import java.util.Observer;

/**
 *  �۲������������
 *
 *    @author    zjun
 *    @version    1.0  create  on  2006-5-18  9:46:30
 */
public class Satellite implements Observer {

    private String weather;

    public void update(Observable obj, Object arg) {
        weather = (String) arg;

        //    ���������仯����������������
        System.out.println("  ���������仯��  " + weather);
    }
}

