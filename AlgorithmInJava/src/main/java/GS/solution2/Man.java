/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution2;

/**
 *
 * @author Administrator
 */
public class Man extends Human {

    int nextWoman = 0;

    public Man(String name, Human[] mates) {
        super(name, mates);
    }

    public Man(String name) {
        super(name, null);
    }

    public Woman getNextWoman() {
        if (nextWoman == prefList.length) {
            return null;
        }
        return (Woman) prefList[nextWoman++];

    }
}
