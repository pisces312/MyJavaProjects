/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Builder;

/**
 *���ڱ��ţ�ָ��Builder�еĸ������װ��˳��
 * @author pisces312
 */
public class PersonDirector {

    private PersonBuilder pb;

    /**
     * ������˭������
     * @param pb
     */
    public PersonDirector(PersonBuilder pb) {
        this.pb = pb;
    }

    /**
     * װ�����
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
