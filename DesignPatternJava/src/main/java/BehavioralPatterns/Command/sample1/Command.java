/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

/**
 *������ó����࣬DP�в��õ�interface
 * ����ӿڣ�ֻ��һ��
 * �����˽����ߣ����ƴ�����ӵĵ����˽������еĺ���
 * @author pisces312
 */
public abstract class Command {
    //�������������ִ���ߵ�����
   protected Barbecuer receiver;

    public Command(Barbecuer receiver) {
        this.receiver = receiver;
    }
    public abstract void excute();

}
