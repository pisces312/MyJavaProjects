package CreationalPatterns.Singleton;

/*
 * Singleton.java
 *
 * Created on 2007年9月21日, 下午2:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author pisces312
 */
public class Singleton {

    private static Singleton s;
    final static Object lock = new Object();

    /** Creates a new instance of Singleton */
    private Singleton() {
    }

    public static Singleton make() {
        if (s == null) {
            s = new Singleton();
            System.out.println("successfully create a Singleton instance!");
        }
        return s;
    }

    /**
     * 双重加锁，提高性能
     * @return
     */
    public static Singleton makeDoubleLock() {
        if (s == null) {
            synchronized (lock) {
                if (s == null) {
                    s = new Singleton();
                    System.out.println("successfully create a Singleton instance!");
                } else {
                    System.out.println("second check is not null ");
                }
            }
        } else {
            System.out.println("first check is not null ");
        }
        return s;
    }

    public synchronized static Singleton makeForThreads() {
        if (s == null) {
            s = new Singleton();
            System.out.println("successfully create a Singleton instance!");
        }
        return s;
    }
}
