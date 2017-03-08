/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.State.sample1;

/**
 *
 * @author pisces312
 */
public class TCPClosed extends TCPState{
private static TCPState state;
    private TCPClosed(){
    }
    public static TCPState getInstance(){
        if(state==null){
            state=new TCPClosed();
        }
        return state;
    }
    @Override
    public void transmit(TCPConnection connection, Object obj) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activeOpen(TCPConnection connection) {
        changeState(connection, TCPEstablished.getInstance());
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void passiveOpen(TCPConnection connection) {
        changeState(connection, TCPListen.getInstance());
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronize(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void acknowledge(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void send(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
