/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.newSingleton;
/**
 * ���͵ĵ���ģʽ
 * @author pisces312
 */
public class Singleton {
    //���ڲ�����ʵ�ֶ�ʵ��������

    private static class SingletonHolder {

        static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
    public void print(){
        System.out.println("�������͵���ģʽ");
    }
    public static void main(String[] args) {
        Singleton.getInstance().print();

    }
}
