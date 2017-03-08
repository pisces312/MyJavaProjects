/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAlgorithm;

/**
 *
 * @author pisces312
 */
public class NewMain {

    public static void sample1() {
        RandomFrame frame = new RandomFrame(new Sample1());
        frame.setVisible(true);
    }

    public static void sample2() {
        RandomAlgorithm random = new RandomAlgorithm();
        int[] a = new int[31];
        //命中率
        double p = 0.5;
        double q = 1 - p;
        for (int i = 1; i <= 200; i++) {
            int k = (int) random.geometricDistribution(q);
            a[k]++;
        }
        //如果击中概率位一半，则用一发子弹击中的次数为总次数的一半
        for (int i = 1, m = 0; i <= 30; i++) {
            System.out.println("用" + i + "发:" + a[i] + "次");
            m++;
            if (m % 4 == 0) {
                System.out.println();
            }

        }
    }

    public static void sample3() {
        RandomAlgorithm random = new RandomAlgorithm();
        int[] a = new int[11];
        double p = 0.8;
        int n = 10;
        for (int i = 0; i < 1000; i++) {
            int k = random.binomialDistribution(n, p);
            a[k]++;
        }
        for (int i = n; i >= 0; i--) {
            System.out.println("命中" + i + "发:" + a[i] + "次");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        sample3();
    }
}
