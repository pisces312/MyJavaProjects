/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.sample1.Memento;

import java.util.LinkedList;

/**
 *
 * @author pisces312
 */
public class TextMemento {
    //String state;
    int i = 0;
    LinkedList<String> memoList;

    public TextMemento() {
        //state = null;
        memoList = new LinkedList<String>();
    }

    public void addMemo(String state) {
        memoList.addLast(state);
    }

    public String getState() {
        if (memoList.size() != 0) {
            String tmp = (String) memoList.getLast();
            memoList.removeLast();
            return tmp;
        }
        return null;
    }
}
