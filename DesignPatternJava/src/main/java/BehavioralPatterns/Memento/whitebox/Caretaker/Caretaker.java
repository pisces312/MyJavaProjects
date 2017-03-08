/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Memento.whitebox.Caretaker;

import BehavioralPatterns.Memento.whitebox.Memento.Memento;

/**
 *����¼�����洢������
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
