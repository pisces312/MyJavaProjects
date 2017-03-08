/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.State.sample1;

/**
 *
 * @author pisces312
 */
public class TCPEstablished extends TCPState {

    private static TCPState state;

    private TCPEstablished() {
    }

    public static TCPState getInstance() {
        if (state == null) {
            state = new TCPEstablished();
        }
        return state;
    }

    @Override
    public void transmit(TCPConnection connection, Object obj) {
        System.out.println("transmit");
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activeOpen(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void passiveOpen(TCPConnection connection) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close(TCPConnection connection) {
        changeState(connection, TCPListen.getInstance());
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronize(TCPConnection connection) {
        throw new UnsupportedOperationException("Not supported yet.");
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
