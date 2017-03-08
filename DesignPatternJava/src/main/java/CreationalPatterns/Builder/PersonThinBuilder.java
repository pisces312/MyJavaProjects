/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.Builder;

/**
 *
 * @author pisces312
 */
public class PersonThinBuilder extends PersonBuilder{

    @Override
    public void buildHead() {
        System.out.println("build thin person head");
    }

    @Override
    public void buildBody() {
        System.out.println("build thin person body");
    }

    @Override
    public void buildLeftArm() {
        System.out.println("build thin person left arm");
    }

    @Override
    public void buildRightArm() {
        System.out.println("build thin person right arm");
    }

    @Override
    public void buildLeftLeg() {
        System.out.println("build thin person left leg");
    }

    @Override
    public void buildRightLeg() {
        System.out.println("build thin person right leg");
    }

}
