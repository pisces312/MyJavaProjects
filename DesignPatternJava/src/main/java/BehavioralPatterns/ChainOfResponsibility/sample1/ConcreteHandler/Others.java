/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler;

import BehavioralPatterns.ChainOfResponsibility.sample1.Handler.Chain;

/**
 *默认的处理，在最后执行
 * @author pisces312
 */
public class Others implements Chain {
    private Chain nextChain = null;
    private String responsibility = "";

    public Others() {
    }
    public void setNextChain(Chain c) {
        nextChain = c;
    }


    public void sendToChain(String mesg) {
            System.out.println("No one can handle -->  " + mesg);
    }

}