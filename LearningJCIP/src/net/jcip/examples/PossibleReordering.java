package net.jcip.examples;

/**
 * PossibleReordering
 * <p/>
 * Insufficiently synchronized program that can have surprising results
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                try {
                    a = 1;
                    Thread.sleep(100);
                    x = b;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread other = new Thread(new Runnable() {
            public void run() {
                try {
                    b = 1;
                    Thread.sleep(100);
                    y = a;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("( " + x + "," + y + ")");
    }
}
