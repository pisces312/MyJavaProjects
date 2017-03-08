/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.mergesort;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class MergeSort {

    private MergeSort() {
    }

    /**
     * 需要一个数组大小的辅助空间作为归并分组
     * @param array
     * @param cmp
     */
    public static void mergeSort(Object[] array, Comparator cmp) {
        Object[] t = new Object[array.length];
        mergeSort(array, t, cmp, 0, array.length - 1);

    }

    private static void mergeSort(Object[] array, Object[] t, Comparator cmp, int left, int right) {
        if (left >= right) {
            return;
        } else if (right - left == 1) {//对两个数组的情况优化处理
            if (cmp.compare(array[left], array[right]) > 0) {
                Object a = array[left];
                array[left] = array[right];
                array[right] = a;
            }
            return;
        }
        int m = (left + right) / 2;
        mergeSort(array, t, cmp, left, m);
        mergeSort(array, t, cmp, m + 1, right);
        merge(array, t, cmp, left, m, right);
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
    private static void merge(Object[] src, Object[] t, Comparator cmp, int start, int middle, int end) {
        System.out.println("合并分组 [" + start + " - " + middle + "] [" + (middle + 1) + " - " + end + " ]");
        int i = start, j = middle + 1, k = start;
        while ((i <= middle) && (j <= end)) {
            if (cmp.compare(src[i], src[j]) <= 0) {
                t[k++] = src[i++];
            } else {
                t[k++] = src[j++];
            }

        }
        if (i > middle) {
            for (int q = j; q <= end; q++) {
                t[k++] = src[q];
            }
        } else {
            for (int q = i; q <= middle; q++) {
                t[k++] = src[q];
            }
        }
        System.arraycopy(t, start, src, start, end - start + 1);
//        for (i = start; i <= end; i++) {
//            src[i] = t[i];
//        }
    }

    public static void main(String[] args) {
        Integer[] a = {5, 7, 4, 3, 2, 1, 6, 8};
        mergeSort(a, new Comparator() {

            public int compare(Object o1, Object o2) {
                Integer a = (Integer) o1;
                Integer b = (Integer) o2;
                return a < b ? -1 : (a == b ? 0 : 1);
            }
        });
        System.out.println(Arrays.toString(a));
    }
}
