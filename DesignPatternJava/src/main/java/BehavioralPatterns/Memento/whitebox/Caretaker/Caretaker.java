/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Memento.whitebox.Caretaker;

import BehavioralPatterns.Memento.whitebox.Memento.Memento;

/**
 *备忘录仅被存储在这里
 * @author pisces312
 */
public class Caretaker{
    private Memento memento;
    public Memento retrieveMemento(){
        return this.memento;
    }
    public void saveMemento(Memento memento){
        this.memento = memento;
    }
}
