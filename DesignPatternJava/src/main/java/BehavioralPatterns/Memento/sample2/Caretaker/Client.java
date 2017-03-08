/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Memento.sample2.Caretaker;

import BehavioralPatterns.Memento.sample2.Originator.MasterGameObject;

/**
 *
 * @author pisces312
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MasterGameObject gameObject=new MasterGameObject();
        Object currentGameState=gameObject.getCurrentGameState();
        gameObject.restoreGameState(currentGameState);
    }

}
