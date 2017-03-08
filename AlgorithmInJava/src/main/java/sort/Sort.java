package sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;

import random.RandomUtils;
import search.Search;
import utils.Pair;
import config.Config;

public final class Sort {
	public static final Logger logger = Logger.getLogger(Sort.class
			.getPackage().getName());
	public static final String CLASS_NAME = Sort.class.getName();

	// private static final Random random=new Random();

	private Sort() {
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * internal use, don't check parameters
	 * 
	 * @param array
	 * @param i
	 * @param j
	 */
	private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
		T t = array[i];
		array[i] = array[j];
		array[j] = t;
	}

	private static <T extends Comparable<T>> void compareExchange(T[] array,
			int i, int j) {
		if (array[i].compareTo(array[j]) > 0) {
			T t = array[i];
			array[i] = array[j];
			array[j] = t;
		}
	}

	/**
	 * 8.5 d. Give an algorithm that k-sorts an n-element array in O(nlg(n/k))
	 * time. We can also show a lower bound on the time to produce a k-sorted
	 * array, when k is a constant.
	 */
	public static <T extends Comparable<T>> void kSort() {
		// TODO
	}

	/**
	 * 8.5 e. Show that we can sort a k-sorted array of length n in O(nlgk)
	 * time.
	 */
	public static <T extends Comparable<T>> void sortFromKSorted() {
		// TODO
	}

	/**
	 * Simplest insertion sort * space:O(n), can sort in place time:O(n^2) *
	 * 
	 * @param x
	 */
	public static <T extends Comparable<T>> void insertSort1(T[] x) {
		for (int i = 1; i < x.length; i++)
			for (int j = i; j > 0 && x[j - 1].compareTo(x[j]) > 0; j--)
				swap(x, j - 1, j);
	}

	/*
	 * Move assignments to and from t out of loop
	 * 
	 * decrease the swap count
	 */
	public static <T extends Comparable<T>> void insertSort3(T[] x) {

		for (int i = 1, j; i < x.length; i++) {
			// method2
			T t = x[i];
			// !!! ">" make sure stable
			for (j = i; j > 0 && x[j - 1].compareTo(t) > 0; j--)
				x[j] = x[j - 1];
			x[j] = t;
			//
			// method1
			// j=i;
			// while(j>0&&x[i]<x[j-1]) {
			// x[j]=x[j-1];
			// --j;
			// }
			// x[j]=x[i];
		}

	}

	/**
	 * 2.1-2 Rewrite the INSERTION-SORT procedure to sort into nonincreasing
	 * instead of non- decreasing order.
	 */
	public static <T extends Comparable<T>> void insertSortNonincreasing(T[] x) {

		for (int i = 1, j; i < x.length; ++i) {
			T t = x[i];
			// !!! ">" make sure stable
			for (j = i; j > 0 && x[j - 1].compareTo(t) < 0; --j)
				x[j] = x[j - 1];
			x[j] = t;
		}

	}

	/**
	 * !!! use binary search to find the position to insert 2.3-6 overall is
	 * still O(n^2)
	 */
	public static <T extends Comparable<T>> void insertSortWithBisearch(T[] x) {
		int i, j, p;
		T t;
		for (i = 1; i < x.length; i++) {
			t = x[i];
			// ////////////////////
			// decrease compare count
			p = Search.binarySearch(x, 0, i - 1, t);
			// smaller than the first element
			if (p < 0) {
				p = 0;
			}
			// ////////////////////
			for (j = i; j > p; j--)
				x[j] = x[j - 1];
			x[j] = t;
		}

	}

	public static <T extends Comparable<T>> void insertSortRecursive(T[] a,
			int n) {
		if (n == 1)
			return;
		insertSortRecursive(a, n - 1);
		T t = a[n - 1];
		int j = n - 2;
		while (j >= 0 && a[j].compareTo(t) > 0) {
			a[j + 1] = a[j];
			--j;
		}
		a[j + 1] = t;
	}

	// ///////////////////////////////////////////////////////////////////////

	public static <T extends Comparable<T>> void selectSort(T[] x) {
		int i, j, len = x.length - 1;
		for (i = 0; i < len; i++)
			// select the minimum each iteration and place at index i
			for (j = i + 1; j < x.length; j++)
				// always compare with x[i]
				if (x[j].compareTo(x[i]) < 0)
					swap(x, i, j);
	}

	// ///////////////////////////////////////////////////////////////////////
	/**
	 * A[p,q] A[q+1,r]
	 * 
	 * not in place, need extra storage
	 * 
	 * @param a
	 * @param p
	 * @param q
	 * @param r
	 */
	public static void mergeWithSentinel(int[] a, int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int[] left = new int[n1 + 1];
		int[] right = new int[n2 + 1];
		int i = 0;
		int j = 0;
		for (i = 0; i < n1; ++i) {
			left[i] = a[p + i];
		}
		// !! sentinel
		left[i] = Integer.MAX_VALUE;
		for (j = 0; j < n2; ++j) {
			right[j] = a[q + 1 + j];
		}
		right[j] = Integer.MAX_VALUE;
		i = 0;
		j = 0;
		for (int k = p; k <= r; ++k) {
			if (left[i] <= right[j]) {
				a[k] = left[i];
				++i;
			} else {
				a[k] = right[j];
				++j;
			}
		}

	}

	public static void mergeWithSentinel(int[] a, int p, int q, int r,
			int[] left, int[] right) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int i = 0;
		int j = 0;
		for (i = 0; i < n1; ++i) {
			left[i] = a[p + i];
		}
		// !! sentinel
		left[i] = Integer.MAX_VALUE;
		for (j = 0; j < n2; ++j) {
			right[j] = a[q + 1 + j];
		}
		right[j] = Integer.MAX_VALUE;
		i = 0;
		j = 0;
		for (int k = p; k <= r; ++k) {
			if (left[i] <= right[j]) {
				a[k] = left[i];
				++i;
			} else {
				a[k] = right[j];
				++j;
			}
		}

	}

	public static void mergeSort(int[] a) {
		int h = a.length / 2 + 2;
		int[] left = new int[h];
		int[] right = new int[h];
		mergeSort(a, 0, a.length - 1, left, right);

	}

	public static void mergeSort(int[] a, int p, int r, int[] left, int[] right) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(a, p, q, left, right);
			mergeSort(a, q + 1, r, left, right);
			mergeWithSentinel(a, p, q, r, left, right);
		}

	}

	public static void mergeSort(int[] a, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(a, p, q);
			mergeSort(a, q + 1, r);
			mergeWithSentinel(a, p, q, r);
		}

	}

	/**
	 * 2.3-2 Rewrite the MERGE procedure so that it does not use sentinels,
	 * instead stopping once either array L or R has had all its elements copied
	 * back to A and then copying the remainder of the other array back into A.
	 */
	public static void mergeWithoutSentinel(int[] a, int p, int q, int r,
			int[] left, int[] right) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int i = 0;
		int j = 0;
		for (i = 0; i < n1; ++i) {
			left[i] = a[p + i];
		}
		for (j = 0; j < n2; ++j) {
			right[j] = a[q + 1 + j];
		}
		i = 0;
		j = 0;
		int k = p;
		for (; i < n1 && j < n2; ++k) {
			if (left[i] <= right[j]) {
				a[k] = left[i];
				++i;
			} else {
				a[k] = right[j];
				++j;
			}
		}
		for (; i < n1; ++i) {
			a[k++] = left[i];
		}
		// !! this can be ignored since right array is copied from the end of a
		// array
		// else if (j < n2) {
		// for (; j < n2; ++j) {
		// a[k++] = right[j];
		// }
		// }

	}

	public static void mergeSortWithoutSentinel(int[] a, int p, int r,
			int[] left, int[] right) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSortWithoutSentinel(a, p, q, left, right);
			mergeSortWithoutSentinel(a, q + 1, r, left, right);
			mergeWithoutSentinel(a, p, q, r, left, right);
		}

	}

	public static void mergeSortWithoutSentinel(int[] a) {
		int h = a.length / 2 + 1;
		int[] left = new int[h];
		int[] right = new int[h];
		mergeSortWithoutSentinel(a, 0, a.length - 1, left, right);

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 8.2 e. Suppose that the n records have keys in the range from 1 to k.
	 * Show how to modify counting sort so that it sorts the records in place in
	 * O(n+k) time. You may use O(k) storage outside the input array. Is your
	 * algorithm stable? (Hint: How would you do it for k = 3?)
	 * 
	 * @param a
	 * @param b
	 * @param k
	 */
	public static void countingSortInPlace(int[] a, int k) {
		int[] c = new int[k + 1];
		int i = 0;
		for (; i < c.length; ++i) {
			c[i] = 0;
		}
		for (i = 0; i < a.length; ++i) {
			++c[a[i]];
		}
		for (i = 1; i < c.length; ++i) {
			c[i] += c[i - 1];
		}
		for (i = k; i >= 1; --i) {
			a[c[i] - 1] = i;
			--c[i];
		}
	}

	/**
	 * positive integer
	 * 
	 * stable
	 * 
	 * @param a
	 * @param b
	 *            output
	 * @param c
	 */
	public static void countingSort(int[] a, int[] b, int k) {
		int[] c = new int[k];
		int i = 0;
		for (; i < c.length; ++i) {
			c[i] = 0;
		}
		for (i = 0; i < a.length; ++i) {
			++c[a[i]];
		}
		// System.out.println(Arrays.toString(c));
		for (i = 1; i < c.length; ++i) {
			c[i] += c[i - 1];
		}
		// System.out.println(Arrays.toString(c));
		// guarantee the sort is stable
		for (i = a.length - 1; i >= 0; --i) {
			// !!! -1 to point to correct index
			b[c[a[i]] - 1] = a[i];
			--c[a[i]];
		}
		// System.out.println(Arrays.toString(b));
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param a
	 * @param weight
	 *            the weight of digit d
	 * @param b
	 * @param c
	 */
	public static void countingSortOfOneDigit(int[] a, int weight,
			int[] weightCache, int[] b, int[] c) {
		int i = 0;
		int d = 0;
		for (; i < c.length; ++i) {
			c[i] = 0;
		}
		for (i = 0; i < a.length; ++i) {
			d = a[i] / weight % 10;
			weightCache[i] = d;
			++c[d];
		}
		// System.out.println(Arrays.toString(c));
		for (i = 1; i < c.length; ++i) {
			c[i] += c[i - 1];
		}
		// System.out.println(Arrays.toString(c));
		// guarantee the sort is stable
		for (i = a.length - 1; i >= 0; --i) {
			// !!! count-1 to point to correct index
			// count->position
			b[c[weightCache[i]] - 1] = a[i];
			--c[weightCache[i]];
		}
		// System.out.println(Arrays.toString(b));
	}

	/**
	 * 
	 * @param a
	 *            may not contain the result
	 * @param radix
	 * @param digitLen
	 * @return result
	 */
	public static int[] radixSortForPositive(int[] a, int radix, int digitLen) {
		int[] b = new int[a.length];
		int[] c = new int[radix];
		int[] t = null;
		int[] weightCache = new int[a.length];
		int weight = 1;
		int d = 0;
		while (d < digitLen) {
			countingSortOfOneDigit(a, weight, weightCache, b, c);
			// System.out.println(Arrays.toString(b));
			weight *= radix;
			++d;
			t = a;
			a = b;
			b = t;
		}
		return a;
	}

	/**
	 * prefix ' '
	 * 
	 * @param a
	 * @param maxStrLen
	 * @return
	 */
	public static String[] radixSortForASCIIString(String[] a, int maxStrLen) {
		String[] b = new String[a.length];
		int[] c = new int[256];
		String[] t = null;
		int d = maxStrLen - 1;
		while (d >= 0) {
			// counting sort
			for (int i = 0; i < c.length; ++i) {
				c[i] = 0;
			}
			for (int i = 0; i < a.length; ++i) {
				if (d < a[i].length()) {
					++c[a[i].charAt(d)];
				} else {
					++c[0];
				}
			}
			for (int i = 1; i < c.length; ++i) {
				c[i] += c[i - 1];
			}
			for (int i = a.length - 1; i >= 0; --i) {
				if (d < a[i].length()) {
					b[c[a[i].charAt(d)] - 1] = a[i];
					--c[a[i].charAt(d)];
				} else {
					b[c[0] - 1] = a[i];
					--c[0];
				}
			}
			// System.out.println(Arrays.toString(b));
			--d;
			t = a;
			a = b;
			b = t;
		}
		return a;
	}

	public static void bucketSort1(int[] a, int bucketNumber, int aMax) {
		// System.out.println("bucket number:" + bucketNumber);
		// store index or value itself
		// store value this time
		List<?> buckets[] = new LinkedList<?>[bucketNumber];
		int i = 0;
		for (i = 0; i < buckets.length; ++i) {
			buckets[i] = new LinkedList<Integer>();
		}
		for (i = 0; i < a.length; ++i) {
			// 1) !!! distribute to correct bucket
			int idx = (int) ((a[i] / (double) aMax) * (bucketNumber - 1));
			// System.out.println("idx:" + idx);
			// //////////////////////////////////
			// 2) insertion sort
			ListIterator<Integer> bucketItr = ((List<Integer>) buckets[idx])
					.listIterator();
			while (bucketItr.hasNext()) {
				Integer value = bucketItr.next();
				if (a[i] < value) {
					break;
				}
			}
			bucketItr.add(a[i]);

		}
		// 3) concatenate
		i = 0;
		for (List<?> bucket : buckets) {
			// System.out.println(bucket);
			for (Integer value : (List<Integer>) bucket) {
				a[i++] = value;
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	public static <T extends Comparable<T>> int partitionMedian(T A[], int p,
			int r) {
		int range = r - p + 1;
		int med1 = p + RandomUtils.random.nextInt(range);
		int med2 = p + RandomUtils.random.nextInt(range);
		int med3 = p + RandomUtils.random.nextInt(range);
		// System.out.println(med1+","+med2+","+med3);
		int med = (A[med1].compareTo(A[med2]) < 0) ? ((A[med2]
				.compareTo(A[med3]) < 0) ? med2
				: ((A[med1].compareTo(A[med3]) < 0) ? med3 : med1)) : ((A[med1]
				.compareTo(A[med3]) < 0) ? med1
				: ((A[med2].compareTo(A[med3]) < 0) ? med2 : med3));

		swap(A, med, r);
		return partitionLT2ndPart(A, p, r);
	}

	public static <T extends Comparable<T>> void quickSortMedian(T A[], int p,
			int r) {
		if (p < r) {
			int q = partitionMedian(A, p, r);
			quickSortMedian(A, p, q - 1);
			quickSortMedian(A, q + 1, r);
		}
	}

	public static <T extends Comparable<T>> int partitionRandomized(T a[],
			int p, int r) {
		int i = RandomUtils.nextInt(p, r);
		swap(a, i, r);
		// return partitionLT2ndPart(a, p, r);
		return partitionGT1stPart(a, p, r);

	}

	public static <T extends Comparable<T>> void quickSortRandomized(T[] a,
			int p, int r) {
		if (p < r) {
			int q = partitionRandomized(a, p, r);
			// A[p,q-1], A[q], A[q+1,r]
			quickSortRandomized(a, p, q - 1);
			quickSortRandomized(a, q + 1, r);
		}
	}

	public static <T extends Comparable<T>> void quickSortTailRecursive(T[] a,
			int p, int r) {
		while (p < r) {
			int q = partitionRandomized(a, p, r);
			quickSortTailRecursive(a, p, q - 1);
			p = q + 1;
		}
	}

	/**
	 * At the beginning of each iteration of the loop of lines 3�C6, for any
	 * array index k,
	 * 
	 * 1. If p<= k <= i ,then A[k]<=x.
	 * 
	 * 2. If i + 1 <= k <= j - 1,then A[k] > x.
	 * 
	 * 3. If k == r,then A[k] = x.
	 * 
	 * The indices between j and r - 1 are not covered by any of the three
	 * cases, and the values in these entries have no particular relationship to
	 * the pivot x.
	 * 
	 * @param a
	 * @param p
	 * @param r
	 * @return the last index q that A[p..q-1]<=A[q]; A[q]<A[q+1..r]
	 */
	public static <T extends Comparable<T>> int partitionLT2ndPart(T a[],
			int p, int r) {
		// choose last element as default
		T x = a[r];
		int i = p - 1;
		// i+1 point to the first element that >a[r]
		int j = p;
		for (; j < r; ++j) {
			// !!
			if (a[j].compareTo(x) <= 0) {
				++i;
				swap(a, i, j);
			}
		}
		++i;
		swap(a, i, r);// or swap(a,i,j), j==r
		return i;
	}

	public static <T extends Comparable<T>> int partitionGT1stPart(T a[],
			int p, int r) {
		// choose last element as default
		T x = a[r];
		int i = p - 1;
		// i+1 point to the first element that >a[r]
		int j = p;
		for (; j < r; ++j) {
			// !!
			if (a[j].compareTo(x) < 0) {
				++i;
				swap(a, i, j);
			}
		}
		++i;
		swap(a, i, r);// or swap(a,i,j), j==r
		return i;
	}

	public static <T extends Comparable<T>> Pair<Integer, Integer> partition3Parts(
			T a[], int p, int r) {

		// choose last element as default
		T x = a[r];
		int i = p - 1;
		// i+1 point to the first element that >a[r]
		int j = p;
		int t = 0;
		int d = 0;
		for (; j < r; ++j) {
			// !!
			d = a[j].compareTo(x);
			if (d < 0) {
				++i;
				swap(a, i, j);
			} else if (d == 0) {
				++t;
			}
		}
		++i;
		swap(a, i, r);// or swap(a,i,j), j==r
		return new Pair<Integer, Integer>(i, i + t);
	}

	/**
	 * 
	 * @param a
	 * @param p
	 * @param r
	 *            last index
	 */
	public static <T extends Comparable<T>> void quickSort(T[] a, int p, int r) {
		if (p < r) {
			int q = partitionGT1stPart(a, p, r);
			// int q = partitionLT2ndPart(a, p, r);
			quickSort(a, p, q - 1);
			quickSort(a, q + 1, r);
		}
	}

	/**
	 * Every element of A[p .. j ] is less than or equal to every element of A[j
	 * +1..r] when HOARE-PARTITION terminates
	 * 
	 * @param a
	 * @param p
	 * @param r
	 */
	public static <T extends Comparable<T>> int partitionHoare(T a[], int p,
			int r) {
		// System.out.println(Arrays.toString(a) + " " + p + "," + r);
		T x = a[p];
		int i = p - 1;
		int j = r + 1;
		while (true) {
			do {
				--j;
			} while (a[j].compareTo(x) > 0);
			do {
				++i;
			} while (a[i].compareTo(x) < 0);
			// System.out.println(i + "," + j);
			// System.out.println(j);
			if (i < j) {
				swap(a, i, j);
			} else {
				//
				// System.out.println(Arrays.toString(a));
				// System.out.println(j);
				return j;
			}
		}
	}

	public static <T extends Comparable<T>> void quickSortHoare(T[] a, int p,
			int r) {
		if (p < r) {
			int q = partitionHoare(a, p, r);
			// !!!A[p,q] and A[q+1,r], two partitions
			quickSortHoare(a, p, q);
			quickSortHoare(a, q + 1, r);
		}
	}

	// non recursive version
	/**
	 * sort in place
	 * 
	 * @param x
	 */
	// XXX
	// public static <T extends Comparable<T>> void quickSort(T[] x) {
	// Stack<Integer> s1 = new Stack<Integer>();
	// Stack<Integer> s2 = new Stack<Integer>();
	// s1.push(0);
	// s2.push(x.length - 1);
	// Integer tl, th, p;
	// while (!s1.empty()) {
	// tl = s1.pop();
	// th = s2.pop();
	// System.out.println("[" + tl + "," + th + "]");
	// if (tl >= th)
	// continue;
	// p = partition(x, tl, th);
	// if (tl < p + 1) {
	// s1.push(tl);
	// s1.push(p + 1);
	// }
	// //
	// if (p - 1 < th) {
	// s2.push(p - 1);
	// s2.push(th);
	// }
	// }
	// }
	// public static <T extends Comparable<T>> void quickSort(T[] x) {
	// Stack<Integer> s1 = new Stack<Integer>();
	// Stack<Integer> s2 = new Stack<Integer>();
	// s1.push(0);
	// s2.push(x.length - 1);
	// Integer tl, th, p;
	// while (!s1.empty()) {
	// tl = s1.pop();
	// th = s2.pop();
	// System.out.println("[" + tl + "," + th + "]");
	// if (tl >= th)
	// continue;
	// p = partition(x, tl, th);
	// if (tl < p + 1) {
	// s1.push(tl);
	// s1.push(p + 1);
	// }
	// //
	// if (p - 1 < th) {
	// s2.push(p - 1);
	// s2.push(th);
	// }
	// }
	// }
	// ////////////////////////////////////////////////////////////
	/**
	 * Horner��s rule
	 */
	public static double calcPolynomialByHornerRule(double[] a, double x) {
		double y = 0;
		for (int i = a.length - 1; i >= 0; --i) {
			y *= x;
			y += a[i];
		}
		return y;
	}

	public static double calcPolynomialByHornerRuleRecursive(double[] a,
			int idx, double x) {
		if (idx == a.length) {
			return 0;
		}
		return a[idx] + x * calcPolynomialByHornerRuleRecursive(a, idx + 1, x);
	}

	public static double calcPolynomial2(double[] a, double x) {
		double y = 0;
		double t = 1;
		for (int i = 0; i < a.length; ++i, t *= x) {
			y += (a[i] * t);
		}
		return y;
	}

	public static double calcPolynomial1(double[] a, double x) {
		double y = 0;
		double t = 1;
		for (int i = 0; i < a.length; ++i) {
			t = a[i];
			for (int j = 0; j < i; ++j) {
				t *= x;
			}
			y += t;
		}
		return y;
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * inversion
	 * 
	 * 
	 */
	public static int calcInversionNumber(int[] x) {
		int c = 0;
		for (int i = 1, j; i < x.length; i++) {
			int t = x[i];
			for (j = i; j > 0 && x[j - 1] < t; j--) {
				x[j] = x[j - 1];
				++c;
			}
			x[j] = t;
		}
		return c;
	}

	/**
	 * decrease shift operations but increase compare operations to n(n-1)/2
	 * 
	 * @param x
	 * @return
	 */
	public static int calcInversionNumber2(int[] x) {
		int c = 0;
		for (int i = 1, j; i < x.length; ++i) {
			for (j = i; j > 0; --j) {
				if (x[j - 1] < x[i]) {
					++c;
				}
			}
		}
		return c;
	}

	public static int calcInversionByMerge(int[] a) {
		int h = a.length / 2 + 2;
		int[] left = new int[h];
		int[] right = new int[h];
		return countInversionByMerge(a, 0, a.length - 1, left, right);

	}

	public static int countInversionByMerge(int[] a, int p, int r, int[] left,
			int[] right) {
		if (p < r) {
			int q = (p + r) / 2;
			int c = countInversionByMerge(a, p, q, left, right);
			c += countInversionByMerge(a, q + 1, r, left, right);
			c += mergeInversion(a, p, q, r, left, right);
			return c;
		}
		return 0;
	}

	public static int mergeInversion(int[] a, int p, int q, int r, int[] left,
			int[] right) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int i = 0;
		int j = 0;
		for (i = 0; i < n1; ++i) {
			left[i] = a[p + i];
		}
		left[i] = Integer.MAX_VALUE;
		for (j = 0; j < n2; ++j) {
			right[j] = a[q + 1 + j];
		}
		right[j] = Integer.MAX_VALUE;
		// //////////////////////////////
		i = 0;
		j = 0;
		int c = 0;
		for (int k = p; k <= r; ++k) {
			if (left[i] <= right[j]) {
				a[k] = left[i];
				++i;
			} else {
				a[k] = right[j];
				++j;
				c += (n1 - i);
			}
		}
		return c;
	}

	// ////////////////////////////////////////////////////////////////
	// TODO test tools
	//
	//
	private static <T extends Comparable<T>> void assertSorted(T[] x) {
		Assert.assertTrue(isSorted(x));
	}

	public static <T extends Comparable<T>> boolean isSorted(T x[]) {
		for (int i = 1; i < x.length; i++)
			if (x[i - 1].compareTo(x[i]) > 0)
				return false;
		return true;
	}

	public static boolean isSorted(int x[]) {
		for (int i = 1; i < x.length; i++)
			if (x[i - 1] > x[i])
				return false;
		return true;
	}

	private static void assertSorted(int[] x) {
		Assert.assertTrue(isSorted(x));
	}

	private static void test(String methodName) {
		test(methodName, 10, 10);
	}

	private static <T extends Comparable<T>> long test(T[] keys, Method m,
			int count) {
		long total = 0;
		try {
			Object[] objs = null;
			if (m.getParameterTypes().length > 1) {
				objs = new Object[] { keys, 0, keys.length - 1 };
			} else {
				objs = new Object[] { keys };
			}
			Object[] cache = new Object[keys.length];

			for (int i = 0; i < count; ++i) {
				System.arraycopy(keys, 0, cache, 0, cache.length);
				long start = System.nanoTime();
				// System.out.println(Arrays.toString(keys));
				m.invoke(null, objs);
				// System.out.println(Arrays.toString(keys));
				long end = System.nanoTime() - start;
				total += end;
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return total;
	}

	private static long test(int[] keys, Method m, int count) {
		long total = 0;
		try {
			Object[] objs = null;
			if (m.getParameterTypes().length > 1) {
				objs = new Object[] { keys, 0, keys.length - 1 };
			} else {
				objs = new Object[] { keys };
			}
			int[] cache = new int[keys.length];

			for (int i = 0; i < count; ++i) {
				System.arraycopy(keys, 0, cache, 0, cache.length);
				long start = System.nanoTime();
				// System.out.println(Arrays.toString(keys));
				m.invoke(null, objs);
				// System.out.println(Arrays.toString(keys));
				long end = System.nanoTime() - start;
				total += end;
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return total;
	}

	private static long test(String methodName, int size, int count) {
		final String method = "test";
		Class<?> c = Sort.class;
		Class<?> ptypes[] = new Class[] { Comparable[].class };
		if (count < 1) {
			count = 1;
		}
		long total = 0;
		Method m = null;
		try {
			m = c.getMethod(methodName, ptypes);
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			try {
				m = c.getMethod(methodName, new Class[] { Comparable[].class,
						Integer.TYPE, Integer.TYPE });
			} catch (NoSuchMethodException e2) {
				try {
					m = c.getMethod(methodName, new Class[] { int[].class });
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		if (m != null) {
			Integer[] keys = new Integer[size];
			RandomUtils.createRandomData(keys, System.currentTimeMillis());
			Class<?>[] classes = m.getParameterTypes();
			if (classes[0].equals(Comparable.class)) {
				logger.logp(Level.FINEST, CLASS_NAME, method,
						Arrays.toString(keys));
				total = test(keys, m, count);
				logger.logp(Level.FINEST, CLASS_NAME, method,
						Arrays.toString(keys));
				assertSorted(keys);
			} else {
				int[] intKeys = new int[size];
				for (int i = 0; i < intKeys.length; ++i) {
					intKeys[i] = keys[i];
				}
				logger.logp(Level.FINEST, CLASS_NAME, method,
						Arrays.toString(intKeys));
				total = test(intKeys, m, count);
				logger.logp(Level.FINEST, CLASS_NAME, method,
						Arrays.toString(intKeys));
				assertSorted(intKeys);
			}
			logger.logp(Level.FINER, CLASS_NAME, method, methodName + ":"
					+ (total / (double) count / 1000.0) + "ms");

		}
		return total;

	}

	// private static long test(String methodName, int size, int count) {
	// final String method = "test";
	// Class<?> c = Sort.class;
	// Class<?> ptypes[] = new Class[] { Comparable[].class };
	// if (count < 1) {
	// count = 1;
	// }
	// long total = 0;
	// try {
	// // System.out.println(Arrays.toString(c.getMethods()));
	// Method m = c.getMethod(methodName, ptypes);
	// Integer[] keys = new Integer[size];
	// for (int i = 0; i < count; ++i) {
	// RandomUtils.createRandomData(keys, System.currentTimeMillis());
	// logger.logp(Level.FINEST, CLASS_NAME, method,
	// Arrays.toString(keys));
	// long start = System.nanoTime();
	// m.invoke(null, new Object[] { keys });
	// long end = System.nanoTime() - start;
	// total += end;
	// logger.logp(Level.FINEST, CLASS_NAME, method,
	// Arrays.toString(keys));
	// assertSorted(keys);
	// }
	//
	// logger.logp(Level.FINER, CLASS_NAME, method, methodName + ":"
	// + (total / (double) count / 1000.0) + "ms");
	// // for (int i = 0; i < count; ++i) {
	// // logger.logp(Level.FINEST, CLASS_NAME, method,
	// // "---------testing " + methodName + " " + (i + 1)
	// // + "---------");
	// // Method m = c.getMethod(methodName, ptypes);
	// // Integer[] keys = new Integer[10];
	// // RandomUtils.createRandomData(keys, System.currentTimeMillis());
	// // logger.logp(Level.FINEST, CLASS_NAME,
	// // method,Arrays.toString(keys));
	// //
	// // long start = System.nanoTime();
	// // m.invoke(null, new Object[] { keys });
	// // long end = System.nanoTime() - start;
	// // total += end;
	// // logger.logp(Level.FINEST, CLASS_NAME,
	// // method,Arrays.toString(keys));
	// //
	// // assertSorted(keys);
	// // }
	// //
	// //
	// //
	// // logger.logp(Level.FINER, CLASS_NAME,
	// // method,"----------average time:"
	// // + (total / (double) count / 1000.0) + "ms");
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// return total;
	//
	// }
	private static void testPartition() {

		int i = -1;
		/**
		 * the first index q that A[p..q-1]<A[q]; A[q]<=A[q+1..r]
		 */
		Integer[] keys = { 5, 2, -3, 5, -5, -9, 7, 4, 1, 5 };
		// [-9, -5, -3, 1, 2, 4, 5, 5, 5, 7]
		i = partitionLT2ndPart(keys, 0, keys.length - 1);
		Assert.assertTrue(i == 8);
		quickSort(keys, 0, keys.length - 1);
		System.out.println(Arrays.toString(keys));
		System.out.println(i);

		//
		//
		Integer[] keys2 = { 5, 2, -3, 5, -5, -9, 7, 4, 1, 5 };
		// [-9, -5, -3, 1, 2, 4, 5, 5, 5, 7]
		i = partitionGT1stPart(keys2, 0, keys2.length - 1);
		Assert.assertTrue(i == 6);
		System.out.println(i);
		//
		//
		Integer[] keys3 = { 5, 2, -3, 5, -5, -9, 7, 4, 1, 5 };
		// [-9, -5, -3, 1, 2, 4, 5, 5, 5, 7]
		Pair<Integer, Integer> pair = partition3Parts(keys3, 0,
				keys3.length - 1);
		// Assert.assertTrue(i == 6);
		System.out.println(pair.getFirst() + "," + pair.getSecond());

	}

	private static void testCountingSort() {
		// Integer[] keys = { 8, 2, -3, 5, -5, -9, 7, 4, 1 };

		int[] a = { 8, 4, 3, 5, 5, 9, 7, 6, 1, 5 };
		// int[] a = { 8, 2, 3, 5, 5, 9, 7, 4, 1, 5 };
		System.out.println(Arrays.toString(a));
		int[] b = new int[a.length];
		countingSort(a, b, 10);

		// quickSortTailRecursive(keys, 0, keys.length - 1);
		// quickSortMedian(keys, 0, keys.length - 1);
		// // // quickSort1(keys, 0, keys.length - 1);
		System.out.println(Arrays.toString(b));
		assertSorted(b);

		//
		//
		int[] a1 = { 81, 46, 34, 57, 52, 93, 78, 65, 19, 52 };
		// int[] a = { 8, 2, 3, 5, 5, 9, 7, 4, 1, 5 };
		System.out.println(Arrays.toString(a1));
		int[] b1 = new int[a1.length];
		int[] c = new int[10];
		int[] weightCache = new int[a1.length];
		countingSortOfOneDigit(a1, 1, weightCache, b1, c);
		System.out.println(Arrays.toString(b1));
		countingSortOfOneDigit(b1, 10, weightCache, a1, c);
		System.out.println(Arrays.toString(a1));

		// quickSortTailRecursive(keys, 0, keys.length - 1);
		// quickSortMedian(keys, 0, keys.length - 1);
		// // // quickSort1(keys, 0, keys.length - 1);

		// assertSorted(b1);
		System.out.println("finished");
	}

	private static void testExercise8_2_e() {
		int[] a = { 8, 4, 3, 6, 5, 9, 7, 2, 1 };
		System.out.println(Arrays.toString(a));
		countingSortInPlace(a, 9);
		System.out.println(Arrays.toString(a));
		assertSorted(a);
	}

	private static void testRadixSort() {
		int A[] = { 332, 653, 632, 755, 433, 722, 48 };
		int[] b = radixSortForPositive(A, 10, 3);

		System.out.println(Arrays.toString(b));
	}

	private static int getMaxStrLen(String[] strs) {
		if (strs == null) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		for (String str : strs) {
			if (str.length() > max) {
				max = str.length();
			}
		}
		return max;
	}

	private static void testRadixSortForASCII() {
		String[] strs = { "green", "red", "blue", "yellow", "orange", "pink",
				"black", "white", "gray", "purple" };
		// String[] strs = { "fefs", "aef", "ter", "4t3fa", "aegds" };
		int maxStrLen = getMaxStrLen(strs);
		String[] results = radixSortForASCIIString(strs, maxStrLen);

		System.out.println(Arrays.toString(results));
	}

	private static void testBucketSort() {
		int A[] = { 332, 653, 632, 755, 433, 722, 48 };
		System.out.println(Arrays.toString(A));
		int max = Integer.MIN_VALUE;
		for (int a : A) {
			if (a > max) {
				max = a;
			}
		}
		System.out.println(max);
		bucketSort1(A, 10, max);

		System.out.println(Arrays.toString(A));
	}

	/**
	 * 8-3 Sorting variable-length items a. You are given an array of integers,
	 * where different integers may have different numbers of digits, but the
	 * total number of digits over all the integers in the array is n. Show how
	 * to sort the array in O.n/ time.
	 * 
	 * @param args
	 */
	public static void exercise8_3() {
		int A[] = { 332, 653, 632, 755, 433, 722, 48 };
		int[] b = radixSortForPositive(A, 10, 3);
		System.out.println(Arrays.toString(b));
	}

	public static void exercise2_1_2() {
		Integer A[] = { 4, 3, 6, 2, 1, 6, 5 };
		insertSortNonincreasing(A);
		System.out.println(Arrays.toString(A));

	}

	private static void testMergeSort() {
		int A[] = { 1, 3, 5, 2, 4, 6, 8 };
		mergeWithSentinel(A, 0, 2, 6);
		System.out.println(Arrays.toString(A));

		int a[] = { 4, 3, 6, 2, 1, 6, 5 };

		mergeSort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));

		int b[] = { 332, 653, 632, 755, 433, 722, 48 };
		mergeSort(b);
		System.out.println(Arrays.toString(b));
	}

	private static void testInsertion() {
		Integer a[] = { 4, 3, 6, 2, 1, 6, 5 };
		insertSortRecursive(a, a.length);
		System.out.println(Arrays.toString(a));
	}

	public static void testHornerRule() {

		// 1+2x+3x2+4x3
		double[] a = { 1, 2, 3, 4 };
		double x = 2;// 1+4+12+32=49
		System.out.println(calcPolynomialByHornerRule(a, x));
		System.out.println(calcPolynomialByHornerRuleRecursive(a, 0, x));
		System.out.println(calcPolynomial2(a, x));
		System.out.println(calcPolynomial1(a, x));
	}

	public static void testInversion() {
		int[] x = { 2, 3, 8, 6, 1 };
		int c = calcInversionNumber(x);
		System.out.println(c);
		Assert.assertTrue(c == 5);
		//
		//
		int[] y = { 2, 3, 8, 6, 1 };
		c = calcInversionNumber2(y);
		System.out.println(c);
		Assert.assertTrue(c == 5);
		//
		//
		int[] z = { 2, 3, 8, 6, 1 };
		c = calcInversionByMerge(y);
		System.out.println(c);
		Assert.assertTrue(c == 5);
	}

	public static void main(String[] args) {
		testHornerRule();
		// testInversion();
		// testInsertion();
		// testMergeSort();
		// exercise2_1_2();

		//
		Config.initLogConfig();
		int size = 1000, n = 20;

		// test("insertSort1", size, n);
		// test("insertSort3", size, n);
		// test("insertSortWithBisearch", size, n);
		// test("selectSort", size, n);
		// test("quickSortHoare", size, n);
		// test("quickSort", size, n);
		// test("quickSortMedian", size, n);
		// test("quickSortRandomized", size, n);
		// test("quickSortTailRecursive", size, n);
		// test("mergeSort", size, n);
		// test("mergeSortWithoutSentinel", size, n);
		// System.out.println(((char) (0x1e)));
		// System.out.print("[" + new String(new byte[] { 0x1e }) + "]");
		// System.out.println('\u001e');

		//
		//
		//
		// testCountingSort();
		// testRadixSort();
		// testBucketSort();
		// testRadixSortForASCII();
		// testExercise8_2_e();
		// testPartition();
		//
		// test("insertSort1", 10, 3);
		// test("quickSort1", 10, 1);
		// Integer[] keys = { 8, 2, -3, 5, -5, -9, 7, 4, 1 };
		// Integer[] keys = { 8, 2, -3, 5, -5, -9, 7, 4, 1, 5 };
		// countingSort(keys, new int[keys.length], k)

		// quickSortTailRecursive(keys, 0, keys.length - 1);
		// quickSortMedian(keys, 0, keys.length - 1);
		// // // quickSort1(keys, 0, keys.length - 1);
		// System.out.println(Arrays.toString(keys));
		// assertSorted(keys);
		// System.out.println("finished");
		// Integer[] keys = RandomUtils.createRandomData(10);
		// System.out.println(keys[0]);
		// partition(keys, 0, keys.length - 1);
		// System.out.println(Arrays.toString(keys));
	}

}
