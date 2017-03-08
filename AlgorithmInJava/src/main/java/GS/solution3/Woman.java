/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution3;

/**
 *
 * @author Administrator
 */
public class Woman extends Human {

    Man curMan;
    public Woman(String name, Human[] mates) {
        super(name, mates);
    }

    public int getRank(Man man) {
        return prefList.get(man);
    }

    public Woman(String name) {
        super(name);
    }
    public Man getCurrentMan() {
        return curMan;
    }

    public void setCurrentMan(Man curMan) {
        this.curMan = curMan;
    }
}
