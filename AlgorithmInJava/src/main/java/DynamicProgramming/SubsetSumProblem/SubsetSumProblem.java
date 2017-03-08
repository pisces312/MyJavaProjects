/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DynamicProgramming.SubsetSumProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *子集和问题
 * @author Administrator
 */
public class SubsetSumProblem {

//    LinkedList<Integer> queue = new LinkedList<Integer>();
    ArrayList<Integer> queue = new ArrayList<Integer>();
    int maxWeight;

    /**
     *
     * @param max
     * 背包大小
     */
    public SubsetSumProblem(int max) {
        this.maxWeight = max;
    }

    public void addWeight(int w) {
        queue.add(w);
    }

    public int process1() {
        return opt(queue.size(), maxWeight);
    }

    /**
     * 法二，使用二维m数组
     */
    public void process3() {
        int[][] m = new int[queue.size() + 1][maxWeight + 1];
//        Arrays.fill(m, 0);
        for (int i = 1; i <= queue.size(); i++) {
            int wi = queue.get(i - 1);
            for (int w = 0; w <= maxWeight; w++) {
                if (wi > w) {
                    m[i][w] = m[i - 1][w];
                } else {
                    m[i][w] = Math.max(m[i - 1][w], wi + m[i - 1][w - wi]);
                }
            }
        }
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
//????能否用一维数组完成

    public void process2() {
        int[] m = new int[queue.size() + 1];
//        Arrays.fill(m, 0);
        //i为背包的个数
        m[0] = 0;
//        int w=maxWeight;
        for (int i = 1; i <= queue.size(); i++) {
            System.out.println(Arrays.toString(m));
            int wi = queue.get(i - 1);
            for (int w = 0; w <= maxWeight; w++) {
//            int w = maxWeight - m[i - 1];//剩余的重量
//                System.out.println("wi=" + wi + "   leftW=" + w);
                if (wi > w) {
                    m[i] = m[i - 1];
                } else {
                    m[i] = Math.max(m[i - 1], wi + m[i - 1]);
                }
//                if (wi > w) {
//                    m[i] = m[i - 1];
//                } else {
//                    int t = wi + m[i - 1];
//                    if (t > m[i - 1]) {
//                        w -= wi;
//                        m[i] = t;
//                    } else {
//                        m[i] = m[i - 1];
//                    }
////                m[i] = Math.max(m[i - 1], wi + m[i - 1]);
//                }
            }
            System.out.println();

        }
//        for (int i = 1; i <= queue.size(); i++) {
//            System.out.println(Arrays.toString(m));
//            int wi = queue.get(i - 1);
//            int w = maxWeight - m[i - 1];//剩余的重量
//            System.out.println("wi=" + wi + "   leftW=" + w);
//            if (wi > w) {
//                m[i] = m[i - 1];
//            } else {
//                m[i] = Math.max(m[i - 1], wi + m[i - 1]);
//            }
//            System.out.println();
//
//        }

        System.out.println(Arrays.toString(m));
    }
//

    private int opt(int i, int w, int[] m) {
        if (i < 1) {
            return 0;
        }
        int wi = queue.get(i - 1);
        if (w < wi) {
            return opt(i - 1, w);
        }
        return Math.max(opt(i - 1, w), wi + opt(i - 1, w - wi));
    }

    /**
     * i为个数
     * 方法一，没有用m
     * @param i
     * @param w
     * @return
     */
    private int opt(int i, int w) {
        if (i < 1) {
            return 0;
        }
        int wi = queue.get(i - 1);
        if (w < wi) {
            return opt(i - 1, w);
        }
        return Math.max(opt(i - 1, w), wi + opt(i - 1, w - wi));
    }

    public static void main(String[] args) {
        SubsetSumProblem ssp = new SubsetSumProblem(6);
        ssp.addWeight(2);
        ssp.addWeight(2);
        ssp.addWeight(3);
//        System.out.println(ssp.process1());
//        System.out.println(ssp.process2());
//        ssp.process3();
        ssp.process2();
    }
}
