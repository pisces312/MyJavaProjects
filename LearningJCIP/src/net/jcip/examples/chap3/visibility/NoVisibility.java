package net.jcip.examples.chap3.visibility;

/**
 * NoVisibility
 * <p/>
 * Sharing variables without synchronization
 * 
 * Two threads and two sharing variables
 * 
 * Result: Keep running or print 0
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
