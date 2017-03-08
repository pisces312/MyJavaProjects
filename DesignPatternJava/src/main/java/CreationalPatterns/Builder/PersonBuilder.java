/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.Builder;

/**
 *
 * @author pisces312
 */
public abstract class PersonBuilder {
    public abstract void buildHead();
    public abstract void buildBody();
    public abstract void buildLeftArm();
    public abstract void buildRightArm();
    public abstract void buildLeftLeg();
    public abstract void buildRightLeg();
}
