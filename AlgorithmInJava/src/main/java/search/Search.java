package search;

import java.util.Arrays;

import junit.framework.Assert;
import sort.Sort;

public final class Search {
	public static <T extends Comparable<T>> int linearSearch(final T[] keys,
			int b, int e, final T key) {
		int i = b;
		while (i <= e && keys[i].compareTo(key) != 0) {
			++i;
		}
		if (i > e) {
			return -1;
		}
		return i;
	}

	public static <T extends Comparable<T>> int binarySearch(final T[] keys,
			int b, int e, final T key) {
		if (key.compareTo(keys[b]) < 0) {
			return -1;
		}
		if (keys[e].compareTo(key) < 0) {
			return e + 1;
		}
		--b;
		++e;
		int m;
		while (b + 1 != e) {
			m = b + ((e - b) / 2);
			if (keys[m].compareTo(key) < 0) {
				b = m;
			} else {
				e = m;
			}
		}
		return e;
	}

	public static <T extends Comparable<T>> int binarySearch2(final T[] keys,
			int low, int high, final T key) {
		int mid = -1;
		int r = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			// System.out.println(low+","+mid+","+high);
			r = keys[mid].compareTo(key);
			if (r == 0) {
				return mid;
			}
			if (r < 0) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;
	}

	public static int binarySearch2(final int[] keys, int low, int high,
			final int key) {
		int mid = -1;
		int r = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			// System.out.println(low+","+mid+","+high);
			r = keys[mid] - key;
			if (r == 0) {
				return mid;
			}
			if (r < 0) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;
	}

	public static <T extends Comparable<T>> int binarySearchRecursive(
			final T[] keys, int low, int high, final T key) {
		if (low > high) {
			return -1;
		}
		int mid = (low + high) / 2;
		int r = keys[mid].compareTo(key);
		if (r == 0) {
			return mid;
		}
		if (r < 0) {
			return binarySearchRecursive(keys, mid + 1, high, key);
		}
		return binarySearchRecursive(keys, low, mid - 1, key);
	}

	public static void testLinearSearch() {
		Integer[] values = { 3, 1, 4, 7, 4, 2, 0, 5, 2, 8, 6, 9 };
		int i = linearSearch(values, 0, values.length - 1, 0);
		System.out.println(i);
	}

	public static void main(String[] args) {
		Integer[] values = { 3, 1, 4, 7, 4, 2, 0, 5, 2, 8, 6, 9 };
		Sort.quickSort(values, 0, values.length - 1);
		System.out.println(Arrays.toString(values));
		//
		//
		int i = binarySearch2(values, 0, values.length - 1, 0);
		System.out.println(i);
		Assert.assertTrue(i == 0);
		i = binarySearch2(values, 0, values.length - 1, 8);
		System.out.println(i);
		Assert.assertTrue(i == 10);
		// testLinearSearch();
		//
		i = binarySearchRecursive(values, 0, values.length - 1, 0);
		System.out.println(i);
		Assert.assertTrue(i == 0);
		i = binarySearchRecursive(values, 0, values.length - 1, 8);
		System.out.println(i);
		Assert.assertTrue(i == 10);
	}

}
