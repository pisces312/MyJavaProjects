/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Memento.sample2.Originator;

import BehavioralPatterns.Memento.sample2.Memento.GameMemento;

/**
 *
 * @author pisces312
 */
public class MasterGameObject {
    Object gameState;

    public MasterGameObject() {
        gameState=memento.getState();
    }
    GameMemento memento;

    public Object getCurrentGameState(){
        return gameState;
    }
    public void restoreGameState(Object savedGameState){
        gameState=savedGameState;
        memento.setState(gameState);

    }

}
