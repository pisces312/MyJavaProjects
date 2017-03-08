/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GS.solution3;

import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public abstract class Human{
    String name;
//�˺����εĶ�Ӧ
    Hashtable<Human,Integer> prefList;

    public Human(String name, Human[] mates) {
        this.name = name;
        setPrefList(mates);
    }
    public String getName() {
        return name;
    }

    public Human(String name) {
        this.name=name;
    }

    public void setHuman(Human human,int rank){
        prefList.put(human, rank);
    }
    public void removeHuman(Human human){
        prefList.remove(human);
    }
    public void setPrefList(Human[] mates){
        prefList=new Hashtable<Human, Integer>(mates.length);
         for (int i = 0; i < mates.length; i++) {
            prefList.put(mates[i], i);
        }        
    }

    


    
    

}
