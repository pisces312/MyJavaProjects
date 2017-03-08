/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.blackbox.Client;

import BehavioralPatterns.Memento.blackbox.Memento.*;
import BehavioralPatterns.Memento.blackbox.Caretaker.Caretaker;
import BehavioralPatterns.Memento.blackbox.Originator.Originator;

/**
 *Client�����NarrowMemento��ͬһ������
 * ˫�ӿڵ�ʵ�֣���խ�ӿڣ�����)
���ʵ�ֿ�խ�ӿ��أ��ڲ���Ҳ���Ǹ��÷��������ǰѱ���¼�����"�ɷ�����"���ڲ��࣬���������е�������ͬһ
package�е�������Ҳ�ܷ��ʵ���Ϊ�˽��������⣬���ǿ��԰�"����¼"�ķ�����Ƴ�˽�еķ�����������
���Ա�����װ���ֱ����������ܷ��ʵ���
 * @author pisces312
 */
public class Client {

    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();

    public static void main(String[] args) {
        //use wide interface
        o.setState("On");
        c.saveMemento(o.createMemento());
        o.setState("Off");
        o.restoreMemento(c.retrieveMemento());
        //use narrow interface
        NarrowMemento memento = o.getNarrowMemento();
        memento.narrowMethod();

    }
}
