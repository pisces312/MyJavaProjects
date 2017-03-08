/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Builder;

/**
 *
 * @author pisces312
 */
public class PersonFatBuilder extends PersonBuilder {

    @Override
    public void buildHead() {
        System.out.println("build fat person head");
    }

    @Override
    public void buildBody() {
        System.out.println("build fat person body");
    }

    @Override
    public void buildLeftArm() {
        System.out.println("build fat person left arm");
    }

    @Override
    public void buildRightArm() {
        System.out.println("build fat person right arm");
    }

    @Override
    public void buildLeftLeg() {
        System.out.println("build fat person left leg");
    }

    @Override
    public void buildRightLeg() {
        System.out.println("build fat person right leg");
    }
}
