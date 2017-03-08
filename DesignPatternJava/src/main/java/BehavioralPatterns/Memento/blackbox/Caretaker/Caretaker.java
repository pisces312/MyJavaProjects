/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.blackbox.Caretaker;

import BehavioralPatterns.Memento.blackbox.Memento.NarrowMemento;

/**
 *
 * @author pisces312
 */
public class Caretaker {

    private NarrowMemento memento;

    public NarrowMemento retrieveMemento() {
        return this.memento;
    }

    public void saveMemento(NarrowMemento memento) {
        this.memento = memento;
    }
}