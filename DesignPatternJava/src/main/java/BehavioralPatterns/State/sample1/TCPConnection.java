/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.State.sample1;

/**
 *
 * @author pisces312
 */
public class TCPConnection {

    TCPState state;

    public TCPConnection() {
        state = TCPClosed.getInstance();
    }

    public void activeOpen() {
        state.activeOpen(this);
    }

    public void passiveOpen() {
        state.passiveOpen(this);
    }

    public void close() {
        state.close(this);
    }

    public void synchronize() {
        state.synchronize(this);
    }

    public void changeState(TCPState state) {
        this.state = state;
    }

    public void acknowledge() {
        state.acknowledge(this);
    }
}
