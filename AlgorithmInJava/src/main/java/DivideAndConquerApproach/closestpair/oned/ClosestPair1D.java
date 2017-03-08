/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.oned;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class ClosestPair1D {

    public static void closestPairOneDimeninalDriver(double[] s) {
        //保证数组排好序
        Arrays.sort(s);
        System.out.println(Arrays.toString(s));
        int[] pair = new int[2];
//        double d = closestPairOneDimeninal(s, 0, s.length - 1, pair);
        double d = closestPairOneDimeninal(s, 0, s.length - 1, pair);
        System.out.println(d);
        System.out.println(Arrays.toString(pair));
        System.out.println(s[pair[0]] + " " + s[pair[1]]);
    }
//?????如何正确获得两个点

    private static double closestPairOneDimeninal(double[] s, int startIndex, int endIndex, int[] pair) {
        int n = endIndex - startIndex + 1;
        if (n < startIndex || n < 3) {
            return Double.MAX_VALUE;
        }
        System.out.println(n);
        if (n == 3) {
//            pair[0] = startIndex;
//            pair[1] = endIndex;
////            System.out.println("aaaaaaaa "+pair[0]+"        "+pair[1]);
            double d1 = s[endIndex] - s[endIndex - 1];
            double d2 = s[endIndex - 1] - s[endIndex];
//            return s[endIndex] - s[startIndex];
            return d1 < d2 ? d1 : d2;
        } else if (n == 2) {
            return s[endIndex] - s[startIndex];
        }
        //到这至少是3个
        int index = n / 2;
//        if (index < startIndex) {//!!!!否则由d2的递归式可能造成起始比中间大
//            return Double.MAX_VALUE;
//        }
        double d3 = s[index + 1] - s[index];
//        if (index == 1) {
//
//            pair[0] = index;
//            pair[1] = index + 1;
//            return d3;
//        }
        System.out.println("d1 " + startIndex + "   " + index);
        double d1 = closestPairOneDimeninal(s, startIndex, index, pair);

        System.out.println("d2  " + (index + 1) + "    " + endIndex);
        double d2 = closestPairOneDimeninal(s, index + 1, endIndex, pair);

        if ((d1 <= d2 && d2 <= d3) || (d1 <= d3 && d3 <= d2)) {
//            if (index - startIndex != 1) {
            pair[0] = startIndex;
            pair[1] = index;
//            }
            return d1;
        } else if ((d2 <= d1 && d1 <= d3) || (d2 <= d3 && d3 <= d1)) {
//            if (endIndex - index != 2) {
            pair[0] = index + 1;
            pair[1] = endIndex;
//            }
            return d2;
        } else {

            System.out.println("d3 ");
            pair[0] = index;
            pair[1] = index + 1;
            return d3;
        }


//        return Math.min(Math.min(d1, d2), s[index + 1] - s[index]);

    }

    private static double closestPairOneDimeninal(double[] s, int startIndex, int endIndex) {
        int n = endIndex - startIndex + 1;
        System.out.println(n);
        if (n == 2) {
            return s[endIndex] - s[startIndex];
        } else if (n < 2) {
            return Double.MAX_VALUE;
        }
        //到这至少是3个
        int index = n / 2;
        if (index < startIndex) {//!!!!否则由d2的递归式可能造成起始比中间大
            return Double.MAX_VALUE;
        }
        System.out.println("d1 " + startIndex + "   " + index);
        double d1 = closestPairOneDimeninal(s, startIndex, index);
        System.out.println("d2  " + (index + 1) + "    " + endIndex);
        double d2 = closestPairOneDimeninal(s, index + 1, endIndex);
        return Math.min(Math.min(d1, d2), s[index + 1] - s[index]);

    }

    public static void testOne() {
        double[] s = {1, 3, 5, 8, 12};
//        double[] s = {1, 3, 5, 6, 12};
//        double[] s = {3, 4, 8, 2, 9, 1, 6, 5};
        closestPairOneDimeninalDriver(s);
    }

    public static void main(String[] args) {
        testOne();
//        Node[] nodes = {new Node(1, 2), new Node(2, 3)};
//        ClosestPair cp = new ClosestPair(nodes);


    }
}
