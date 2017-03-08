package random;

import java.util.Arrays;
import java.util.Random;

public final class RandomUtils {
	public static final Random random = new Random();

	public static int nextInt(int p, int r) {
		return random.nextInt(r - p + 1) + p;
	}

	public static Integer[] createRandomData(int size) {
		Integer[] keys = new Integer[size];
		createRandomData(keys, System.currentTimeMillis());
		return keys;
	}

	public static Integer[] createRandomPositiveData(int size, int min, int max) {
		if (size < 0 || min < 0 || max < 0 || min > max) {
			return null;
		}
		Integer[] keys = new Integer[size];
		int range = max - min + 1;
		for (int i = 0; i < size; ++i) {
			keys[i] = random.nextInt(range) + min;
		}
		return keys;
	}

	public static long createRandomData(Integer[] keys) {
		return createRandomData(keys, System.currentTimeMillis());
	}

	public static long createRandomData(Integer[] keys, long seed) {
		for (int i = 0; i < keys.length; ++i) {
			keys[i] = random.nextInt() % keys.length;
		}
		return seed;
	}

	public static void main(String[] args) {
		Integer[] keys = new Integer[10];
		createRandomData(keys, System.currentTimeMillis());
		System.out.println(Arrays.toString(keys));
	}
}
