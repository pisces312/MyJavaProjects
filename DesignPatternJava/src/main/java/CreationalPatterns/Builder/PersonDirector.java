/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Builder;

/**
 *用于编排，指定Builder中的各组件的装配顺序
 * @author pisces312
 */
public class PersonDirector {

    private PersonBuilder pb;

    /**
     * 设置由谁来建造
     * @param pb
     */
    public PersonDirector(PersonBuilder pb) {
        this.pb = pb;
    }

    /**
     * 装配过程
     */
    public void createPerson() {
        pb.buildHead();
        pb.buildBody();
        pb.buildLeftArm();
        pb.buildRightArm();
        pb.buildLeftLeg();
        pb.buildRightLeg();
    }
}
