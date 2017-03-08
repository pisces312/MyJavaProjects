/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.ChainOfResponsibility.sample1.Handler;

/**
 *一个接口
 * @author pisces312
 */
public interface Chain {

    /**
     * 添加一环
     * @param c
     */
    public void setNextChain(Chain c);

    public void sendToChain(String msg);
}
