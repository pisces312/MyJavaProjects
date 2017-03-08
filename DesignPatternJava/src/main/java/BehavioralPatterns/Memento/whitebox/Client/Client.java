/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.whitebox.Client;

import BehavioralPatterns.Memento.whitebox.Caretaker.Caretaker;
import BehavioralPatterns.Memento.whitebox.Originator.Originator;

/**
 *˫�ؽӿڵ�ʵ��,����¼ģʽ
һ�����壺����¼(memento)ģʽ�ֽп���(snapshot)ģʽ����tokenģʽ����Ҫ���ܣ�
����¼ģʽ����һ���������洢����һ��������ڲ�״̬�Ŀ��գ�ʵ�ֱ���¼ģʽ�Ĺؼ������ڲ��ƻ���װ��
����£���һ�������״̬��׽ס�����ⲿ�����洢�������Ӷ������ں��ʵ�ʱ�򣬰��������ԭ��
˵��������¼ģʽ��ģʽ�бȽϺ�����һ��������Ͳ������ӣ����Ǳ���¼ģʽ��ģʽ��ʵ�ֱȽ��ѣ�����˵
ʵ�ֱȽ��ɵģ�������Ҫ˵˵��
��������¼ģʽ��ʵ��
1,����¼ģʽ�еĽ�ɫ
�����ˣ����������ڲ�״̬�ı���¼���󣬲�ʹ�ñ���¼����洢״̬
�����ˣ������˱��汸��¼���󣬵�����鱸��¼���������
����¼������¼���󽫷����˶�����ڲ�״̬�������������������ݲ��������˶���֮��Ķ������ȡ
ע�⣺�ڱ���¼�Ľ�ɫ�У�������������Բ�ͬ�����ṩ��ͬ�Ľӿڣ��Է������ṩ��ӿڣ��������κ����ṩխ
�ӿڡ�Ҳ����˵�Ҷ��ṩ��ӿڵ��ˡ�����Ҳ�Ǳ���¼��һ��ʵ�֣��������䱸��¼���������ַ����ķ�װû�����
�ã���ȫ�Բ����á�

 *
 * �����ǰ��䱸��¼��ʵ�֣�
 * ������ŵ㣺ʵ�ּ�
�����ȱ�㣺�ϱ�˵�ˣ��ƻ��˷�װ����ȫ����Щ���⡣
˵������������ʵ��ֻ������һ��״̬����ʵ�ǿ��Ա�����״̬�ġ�
 * @author pisces312
 */
public class Client {

    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();

    public static void main(String[] args) {
        o.setState("ON");
        c.saveMemento(o.createMemento());
        o.setState("OFF");
        o.restoreMemento(c.retrieveMemento());
    }
}
