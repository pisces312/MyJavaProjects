package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *��������
 * @author pisces
 */
public class IntegerDivide {

    /**
     * ��������n�Ļ��ָ���
     * @param n
     * @return
     */
    public static int p(int n) {
        return q(n, n);
    }

    /**
     * 
     * @param n
     * @param m
     * @return
     */
    private static int q(int n, int m) {
        if (n < 1 || m < 1) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        if (n < m) {
            return q(n, n);
        } else if (n == m) {
            return 1 + q(n, n - 1);
        }
        return q(n, m - 1) + q(n - m, m);

    }

    /**
     * ���Ժ���
     */
    public void test() {
        System.out.println(IntegerDivide.p(1));
        System.out.println(IntegerDivide.p(6));
    }
}
