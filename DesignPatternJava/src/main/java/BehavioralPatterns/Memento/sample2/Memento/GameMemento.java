/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Memento.sample2.Memento;

/**
 *
 * @author pisces312
 */
public class GameMemento {
    Object saveGameState=new Object();
    public Object getState(){
        return saveGameState;
    }
    public void setState(Object state){
        saveGameState=state;
    }
}
