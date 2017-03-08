/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.whitebox.Originator;

import BehavioralPatterns.Memento.whitebox.Memento.Memento;

/**
 *负责创建内部状态的备忘录
 * @author pisces312
 */
public class Originator {

    private String state;

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        this.state = memento.getState();
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
        System.out.println("Current state = " + this.state);
    }
}
