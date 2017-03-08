/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.sanitseya.stateAndObsererPattern;

import java.util.Observer;

/**
 *
 * @author pisces312
 */
public class StateMain {

    public static void main(String[] args) {

        Saiya saiya = new Saiya();
        Observer athena = new Athena();
        saiya.addObserver(athena);
        System.out.println("星矢最初的状态是：" + saiya.status());
        for (int i = 0; i < 5; i++) {
            System.out.println("星矢被揍第" + (i + 1) + "次");
            saiya.hit();
            System.out.println("星矢现在的状态是：" + saiya.status());
        }
    }
}