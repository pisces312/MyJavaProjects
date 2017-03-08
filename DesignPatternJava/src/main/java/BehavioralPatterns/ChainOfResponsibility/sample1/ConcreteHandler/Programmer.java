/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler;

import BehavioralPatterns.ChainOfResponsibility.sample1.Handler.Chain;

/**
 *
 * @author pisces312
 */
public class Programmer implements Chain {

    private Chain nextChain = null;
    //标记自己的责任
    private String responsibility = "Coding";

    public Programmer() {
    }

    public void setNextChain(Chain c) {
        nextChain = c;
    }


    public void sendToChain(String mesg) {
        if (mesg.equals(responsibility)) {
            System.out.println("A Programmer  -->  Coding");
        } else {
            if (nextChain != null) {
                nextChain.sendToChain(mesg);
            }
        }
    }
}
