package net.jcip.examples.chap5;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TaskRunnable
 * <p/>
 * Restoring the interrupted status so as not to swallow the interrupt
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue=new LinkedBlockingQueue<>();

    public void run() {
        System.out.println("running");
        try {
            Long x=0L;
            for(long i=0;i<Long.MAX_VALUE;++i,x+=1) {
                
            }
            System.out.println("Before blocking method");
            processTask(queue.take());//Blocking method, its interrupt status will be cleared and throw InterruptedException
        } catch (InterruptedException e) {
            //It will be false
            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println("restore interrupted status");
            // restore interrupted status
            //the current thread is interrupting itself, which is always permitted
            //!This will set interrupted status to true
            Thread.currentThread().interrupt();
            //True here
            System.out.println(Thread.currentThread().isInterrupted());
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
    
    public static void main(String[] args) {
        TaskRunnable task=new TaskRunnable();
        Thread t=new Thread(task);
            t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Interrupt from other thread
        t.interrupt();
        System.out.println("From main thread: "+t.isInterrupted());
    }
}
