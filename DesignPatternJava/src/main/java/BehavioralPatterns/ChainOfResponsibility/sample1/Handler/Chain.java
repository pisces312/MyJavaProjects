/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.ChainOfResponsibility.sample1.Handler;

/**
 *һ���ӿ�
 * @author pisces312
 */
public interface Chain {

    /**
     * ���һ��
     * @param c
     */
    public void setNextChain(Chain c);

    public void sendToChain(String msg);
}
