/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.countinversion;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class CountInversionNum {

    /**
     * 以递增顺序计算逆序数
     * @param array
     * @return
     */
    public static int countInversionNum(int[] array) {
        int[] t = new int[array.length];
        return sortAndCount(array, t, 0, array.length - 1);
    }

    private static int sortAndCount(int[] array, int[] t, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int m = (left + right) / 2;
        return sortAndCount(array, t, left, m) + sortAndCount(array, t, m + 1, right) + mergeAndCount(array, t, left, m, right);
//        return ra + rb + r;
    }

    /**
     *
     * @param src
     * 原数组
     * @param t
     * 辅助数组
     * @param cmp
     * @param start
     * @param middle
     * @param end
     */
    private static int mergeAndCount(int[] src, int[] t, int start, int middle, int end) {
        System.out.println("合并分组 [" + start + " - " + middle + "] [" + (middle + 1) + " - " + end + " ]");
        int array1 = start, array2 = middle + 1, k = start;
        int count = 0;
        while ((array1 <= middle) && (array2 <= end)) {
            if (src[array1] <= src[array2]) {
                t[k++] = src[array1++];
            } else {
                count += (middle - array1 + 1);
                t[k++] = src[array2++];
            }

        }
        if (array1 > middle) {
            for (int q = array2; q <= end; q++) {
                t[k++] = src[q];
            }
        } else {
            for (int q = array1; q <= middle; q++) {
                t[k++] = src[q];
            }
        }
        System.arraycopy(t, start, src, start, end - start + 1);
//        for (array1 = start; array1 <= end; array1++) {
//            src[array1] = t[array1];
//        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 6, 5};
//        int[] a = {5, 7, 4, 3, 2, 1, 6, 8};
        System.out.println(countInversionNum(a));
        System.out.println(Arrays.toString(a));
    }
}
