/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution2;

import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class Woman extends Human {

    Man curMan;
    Hashtable<Man, Integer> rank = new Hashtable<Man, Integer>();
//    int[] rank;

    public Woman(String name, Human[] mates) {
        super(name, null);
        setPrefList(mates);
    }

    public int getRank(Man man) {
        return rank.get(man);
//        for(int i=0;i<prefList.length;i++){
//            if(man==prefList[i]){
//                return i;
//            }
//        }
//        return -1;
    }

    public Woman(String name) {
        super(name, null);
    }

    /**
     * ���������⴦��ȷ�����˵�����
     * @param mates
     */
    @Override
    public void setPrefList(Human[] mates) {
        super.setPrefList(mates);
        for (int i = 0; i < mates.length; i++) {
            rank.put((Man) mates[i], i);
        }
    }

    public Man getCurrentMan() {
        return curMan;
    }

    public void setCurrentMan(Man curMan) {
        this.curMan = curMan;
    }
}
