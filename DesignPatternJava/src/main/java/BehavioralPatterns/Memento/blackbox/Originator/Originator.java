/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.blackbox.Originator;

import BehavioralPatterns.Memento.blackbox.Memento.NarrowMemento;

/**
 *
 * @author pisces312
 */
public class Originator {

    private String state;
    private NarrowMemento memento;

    public Originator() {
    }

    public NarrowMemento createMemento() {
        memento = new Memento(this.state);
        return memento;
    }

    public void restoreMemento(NarrowMemento memento) {
        Memento aMemento = (Memento) memento;
        this.setState(aMemento.getState());
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
    //内部类
    //为了保证不能访问发起人，所以将备忘录的方法设计成私有的
    protected class Memento implements NarrowMemento {

        private String savedState;

        private Memento(String someState) {
            savedState = someState;
        }

        private void setState(String someState) {
            savedState = someState;
        }

        private String getState() {
            return savedState;
        }

        public void narrowMethod() {
            System.out.println("this is narrow method");
        }
    }

    public NarrowMemento getNarrowMemento() {
        return memento;
    }
}
