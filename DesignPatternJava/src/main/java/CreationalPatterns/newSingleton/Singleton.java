/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.newSingleton;
/**
 * 新型的单子模式
 * @author pisces312
 */
public class Singleton {
    //用内部类来实现对实例的隐藏

    private static class SingletonHolder {

        static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
    public void print(){
        System.out.println("我是新型单子模式");
    }
    public static void main(String[] args) {
        Singleton.getInstance().print();

    }
}
