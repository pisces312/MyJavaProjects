/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeNumber;

/**
 *
 * @author pisces312
 */
public class PrimeAlgorithm {

    public static void calcPrime2_9999(final int end) {

        if (end > 9999 || end < 2) {
            return;
        }
        //默认是FALSE,即都为素数
        boolean[] array = new boolean[end + 1];
//        for(int i=0;i<array.length;i++){
//            System.out.println(array[i]);
//        }
        int seed = 2, filter, step, count = 2;
        do {
            filter = seed * seed;
            step = seed * 2;
            int div = filter;
            while (div < end) {
                array[div] = true;

            }
            count++;
        } while (count < end);
        for (int i = 2; i < array.length; i++) {
            System.out.println(i + ":" + array[i]);
        }
    }

    public static void main(String[] args) {
        calcPrime2_9999(100);
    }
}
