/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Command.sample2;

/**
 *
 * @author pisces312
 */
class Invoker {

    private Command commandOne;
    private Command commandTwo;

    public Invoker(Command commandOne, Command commandTwo) {
        this.commandOne = commandOne;
        this.commandTwo = commandTwo;
    }

    public void actionOne() {
        commandOne.execute();
    }

    public void actionTwo() {
        commandTwo.execute();
    }
}
