/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Command.sample1;

/**
 *命令实现，多个实现
 * @author pisces312
 */
public class BakeMuttonCommand extends Command{

    public BakeMuttonCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }


    @Override
    public void excute() {
        receiver.bakeMutton();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
