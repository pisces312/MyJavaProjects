/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GS.solution2;

/**
 *
 * @author Administrator
 */
public abstract class Human{
    String name;

    Human[] prefList;

    public Human(String name, Human[] mates) {
        this.name = name;
        this.prefList = mates;
    }
    public void setPrefList(Human[] mates){
        this.prefList=mates;
    }

    public String getName() {
        return name;
    }


    
    

}
