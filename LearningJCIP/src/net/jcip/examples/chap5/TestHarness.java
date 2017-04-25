package net.jcip.examples.chap5;

import java.util.concurrent.*;

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        //only need 1 for start gate
        final CountDownLatch startGate = new CountDownLatch(1);
        //the number of thread for end gate
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        System.out.println("[main] Create all threads");
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        System.out.println(getName() + " is ready to start");
                        //wait countdown to start
                        startGate.await();
                        System.out.println(getName() + " is started");
                        try {
                            task.run();
                        } finally {
                            //tell wait it's finished
                            endGate.countDown();
                            System.out.println(getName() + " is finished");
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }
        System.out.println("[main] Start all threads now");
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("[main] All threads are finished");
        return end - start;
    }

    public static void main(String[] args) {
        try {
            new TestHarness().timeTasks(6, new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
