/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Prototype;

/**
 *����ʵ��Cloneable�ӿ�
 * @author pisces312
 */
public class Resume implements Cloneable {

    String name, sex, age, company;

    public Resume(String name, String sex, String age, String company) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.company = company;
    }

    public Resume() {
    }

    public void print() {
        System.out.println(name);
        System.out.println(sex);
        System.out.println(age);
        System.out.println(company);
    }

    /**
     * ������ǻ���������ֱ����super.clone()
     * ��������е���Ҫ����clone
     * @return
     */
    @Override
    public Object clone() {
//        return super.clone();
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Do not support clone !!!");
            throw new InternalError();
        }
    }
}
