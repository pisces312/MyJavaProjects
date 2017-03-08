/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.State.sample1;

/**
 *
 * @author pisces312
 */
public abstract class  TCPState {
    public abstract void transmit(TCPConnection connection,Object obj);
    public abstract void activeOpen(TCPConnection connection);
    public abstract void passiveOpen(TCPConnection connection);
    public abstract void close(TCPConnection connection);
    public abstract void synchronize(TCPConnection connection);
    public abstract void acknowledge(TCPConnection connection);
    public abstract void send(TCPConnection connection);
    //������ȫ��״̬��ת��������״̬�ڲ�
    public void changeState(TCPConnection connection,TCPState state){
        connection.changeState(state);
    }

}
