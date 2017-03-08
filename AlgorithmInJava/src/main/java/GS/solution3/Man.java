/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution3;

/**
 *
 * @author Administrator
 */
public class Man extends Human {

    int nextWoman = 0;
    Human[] mates;

    public Man(String name, Human[] mates) {
        super(name, mates);
    }

    public Man(String name) {
        super(name);
    }

    @Override
    public void setPrefList(Human[] mates) {
        super.setPrefList(mates);
        this.mates = mates;
    }

    public Woman getNextWoman() {
        if (nextWoman == mates.length) {
            return null;
        }
        return (Woman) mates[nextWoman++];

    }
}
