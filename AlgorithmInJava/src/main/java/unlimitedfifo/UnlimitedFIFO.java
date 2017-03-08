/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unlimitedfifo;

import java.util.LinkedList;

/**
 *
 * @author Administrator
 */
public class UnlimitedFIFO {

//    Stack inStack,outStack;
//    LinkedList inStack;
    LinkedList outStack;

    public UnlimitedFIFO() {
//        inStack = new LinkedList();
        outStack = new LinkedList();
    }

    public void push(Object e) {
//        for (Object out : outStack) {
//            if (out != null) {
//                continue;
//            }
//        }
        outStack.add(e);

    }

    public Object pop() {
        return outStack.pop();
//        return 
    }

    public void print() {
        for (Object out : outStack) {
            System.out.println(out);
        }
    }
//    public Class<T> pop(){
//        return T;
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here\
        UnlimitedFIFO fifo = new UnlimitedFIFO();
        fifo.push("a");
        fifo.print();
        fifo.push("B");
        fifo.print();
        fifo.pop();
        fifo.print();
    }
}
