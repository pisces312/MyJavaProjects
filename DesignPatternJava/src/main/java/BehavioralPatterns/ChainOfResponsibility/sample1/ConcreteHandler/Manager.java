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
public class Manager implements Chain {

    private Chain nextChain = null;
    private String responsibility = "Get Project";
    ;

    public Manager() {
    }

    public void setNextChain(Chain c) {
        nextChain = c;
    }


    public void sendToChain(String mesg) {
        if (mesg.equals(responsibility)) {
            System.out.println("A manager  -->  Get a Project");
        } else {
            if (nextChain != null) {
                nextChain.sendToChain(mesg);
            }
        }
    }
}
