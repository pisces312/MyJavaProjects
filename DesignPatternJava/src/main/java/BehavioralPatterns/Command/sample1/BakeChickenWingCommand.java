/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

/**
 *
 * @author pisces312
 */
public class BakeChickenWingCommand extends Command{

    public BakeChickenWingCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }


    @Override
    public void excute() {
        receiver.bakeChickenWing();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
