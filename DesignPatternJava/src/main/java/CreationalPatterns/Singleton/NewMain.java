/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Singleton;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                public void run() {
                    Singleton.makeDoubleLock();
                    Singleton.make();
                    Singleton.makeForThreads();
                }
            }).start();

        }
    }
}
