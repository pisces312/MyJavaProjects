/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Command.sample1;

/**
 *�ͻ��ˣ����յĵ���
 *
 * ����ģʽ��һ�������װΪһ������ ���Ӷ�ʹ���ʹ�ò�ʹ������Կͻ����в�����
 * �������Ŷӻ��¼������־���Լ�֧�ֿɳ�������
 * Ӧ�ã�
 * undo������redo�ָ�����
 *
 *
 * ����ģʽ���һ�㶼�����¼�����ɫ���ͻ��ˣ������ߣ�����ӿڣ�����ʵ�֣�������,
 * @author pisces312
 */
public class Client {

    public static void main(String[] args) {
        Waiter girl = new Waiter();
        Barbecuer boy = new Barbecuer();
        girl.addOrder(new BakeChickenWingCommand(boy));
        girl.addOrder(new BakeMuttonCommand(boy));
        girl.excute();
    }
}
