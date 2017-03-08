/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.sanitseya.stateAndObsererPattern;

/**
 *
 * @author pisces312
 */
public class GoddessState extends SaiyaState {

    public GoddessState(Saiya saiya) {
        super(saiya);

    }

    /* 小宇宙爆发状态被打进入瀕死状态
     *
     */
    public void hit() {
        saiya.setState(saiya.UNIVERSE);

    }
}
