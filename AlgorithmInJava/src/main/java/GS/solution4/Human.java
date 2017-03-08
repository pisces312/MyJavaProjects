/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution4;

import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class Human {

    Human curMan;
    int next = 0;
    String name;
//�˺����εĶ�Ӧ,����������
    Hashtable<Human, Integer> prefList;
    //�����±�����ҵ�һ�����µĶ���
    Human[] mates;

    public Human(String name, Human[] mates) {
        this.name = name;
        setPrefList(mates);
    }

    public String getName() {
        return name;
    }

    public Human(String name) {
        this.name = name;
    }

//    public void setHuman(Human human, int rank) {
//        prefList.put(human, rank);
//    }

//    public void removeHuman(Human human) {
//        prefList.remove(human);
//    }

    public void setPrefList(Human[] mates) {
        this.mates = mates;
        prefList = new Hashtable<Human, Integer>(mates.length);
        for (int i = 0; i < mates.length; i++) {
            prefList.put(mates[i], i);
        }
    }

    public Human getNext() {
        if (next == mates.length) {
            return null;
        }
        return mates[next++];

    }

    public int getRank(Human human) {
        return prefList.get(human);
    }

    public void setCurrent(Human curHuman) {
        this.curMan = curHuman;
    }

    public Human getCurrent() {
        return curMan;
    }
}
