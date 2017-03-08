/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Command.sample2;

/**
 *
 * @author pisces312
 */
class ConcreteCommandOne implements Command {

    Receiver receiver;

    public ConcreteCommandOne(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        System.out.println("command1");
        receiver.doSomething();
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
