package algorithm_exercise;

import java.util.Arrays;

import search.Search;
import sort.Sort;

public class Exercise {
	/**
	 * 2.1-4 Consider the problem of adding two n-bit binary integers, stored in
	 * two n-element arrays A and B. The sum of the two integers should be
	 * stored in binary form in an (n + 1)-element array C. State the problem
	 * formally and write pseudocode for adding the two integers.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] add(int[] a, int[] b, int n) {
		int[] c = new int[n + 1];
		int sum = 0;
		// !! n-element value index
		for (int i = n - 1; i >= 0; --i) {
			sum = a[i] + b[i];
			c[i + 1] += (sum % 10);
			if (sum > 10) {
				++c[i];
			}
		}
		return c;
	}

	public static void exercise2_1_4() {
		int[] a = { 1, 3, 6, 3 };
		int[] b = { 1, 3, 6, 3 };
		int[] c = add(a, b, a.length);
		System.out.println(Arrays.toString(c));
	}

	/**
	 * !!! 2.3-7 ? Describe a ‚.n lg n/-time algorithm that, given a set S of n
	 * integers and another integer x, determines whether or not there exist two
	 * elements in S whose sum is exactly x.
	 */
	public static boolean isExistedSumOfNonnegativeValues(int x, int[] numbers) {
		// use mergesort
		Sort.mergeSort(numbers);
		// Sort.quickSortRandomized(numbers, 0, numbers.length - 1);
		for (int i = 0; i < numbers.length && x >= numbers[i]; ++i) {
			int d = x - numbers[i];
			if (d >= numbers[i]) {
				// make sure numbers[i]<x/2
				int idx = Search.binarySearch2(numbers, i, numbers.length, d);
				if (idx >= 0) {
					return true;
				}
			} else {
				break;
			}
		}
		return false;
	}

	public static boolean isExistedSumOfAnyValues(int x, Integer[] numbers) {
		Sort.quickSortRandomized(numbers, 0, numbers.length - 1);
		for (int i = 0; i < numbers.length; ++i) {
			int d = x - numbers[i];
			if (d >= numbers[i]) {
				int idx = Search.binarySearch2(numbers, 0, numbers.length, d);
				if (idx >= 0) {
					return true;
				}
			} else {
				break;
			}
		}
		return false;
	}

	public static void exercise2_3_7() {
		int[] a = { 2, 5, 1, 4, 5, 2, 1, 8, 9, 54, 2, 90, 6, 1 };
		boolean flag = isExistedSumOfNonnegativeValues(56, a);
		System.out.println(flag);
		//
		flag = isExistedSumOfNonnegativeValues(9, a);
		System.out.println(flag);
		//
		//
		Integer[] b = { 2, 5, 1, -4, 5, 2, -1, 8, 9, 54, 2, 90, 6, 1 };
		flag = isExistedSumOfAnyValues(50, b);
		System.out.println(flag);
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// exercise2_1_4();
		// exercise2_3_7();

	}

}
