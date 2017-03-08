package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pisces
 */
public class Recursion {

    public int permCount;
    final static double sqrt5 = Math.sqrt(5);

    public static long Fibonacci(long n) {
        return Math.round((1 / sqrt5) * (Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1)));
    }

    /**
     * 쳲���������
     * @param n
     * @return
     */
    public static long Fibonacci_Recursion_Edition(long n) {
        if (n < 0) {
            return -1;
        } else if (n == 0 || n == 1) {
            System.out.println("F(" + n + ")=" + 1);
            return 1;
        }
        System.out.println("F(" + n + ")=F(" + (n - 1) + ")+F(" + (n - 2) + ")");
        return Fibonacci_Recursion_Edition(n - 1) + Fibonacci_Recursion_Edition(n - 2);

    }
//    public static void printA(int n, int m, int result) {
//        System.out.println("Ackerman(" + n + "," + m + ")=" + result);
//    }
    /**
     * �޷���ʾΪ�ǵݹ飡
     * ��һ������������һ���������ɺ���������ʱ�������������˫�ݹ麯����        
     * ����-1Ϊ����
     *  Ackerman����
    ? Ackerman(n��m)���Ա���m��ÿһ��ֵ��������һ��������������
    ? M=0ʱ��Ackerman(n,0)=n+2
    ? M=1ʱ��Ackerman(n,1)=Ackerman(Ackerman(n-1,1),0)=Ackerman(n-1,1)+2����A(1,1)=2��
    Ackerman(n,1)=2*n
    ? M=2ʱ��Ackerman(n,2)=Ackerman(Ackerman(n-1,2),1)=2A(n-1,2)����
    Ackerman(1,2)=Ackerman(Ackerman(0,2),1)=Ackerman(1,1)=2����A(n,2)= 2^n ��
    ? M=3ʱ�����ƵĿ����Ƴ�
    ? M=4ʱ��Ackerman(n,4)�������ٶȷǳ��죬������û���ʵ�����ѧʽ��
    ����ʾ��һ������2^(2^(2^...
     * @param n
     * @param m
     * @return
     */
    public static long Ackerman(long n, long m) {
        if (n < 0 || m < 0) {
            return -1;
        }
        //Ackerman(0,m)=1 m>=0
        if (n == 0) {
            if (m >= 0) {
                System.out.println("A(" + n + "," + m + ")=1");
                return 1;
            }
            return -1;
        }

        if (m == 0) {
            //Ackerman(1,0)=2
            if (n == 1) {
                System.out.println("A(" + n + "," + m + ")=2");
                return 2;
            //Ackerman(n,0)=n+2 n>=2
            } else if (n >= 2) {
//                printA(n, m, n+2);
                System.out.println("A(" + n + "," + m + ")=" + (n + 2));
                return n + 2;
            }
            return -1;

        }
        //Ackerman(n,m)=Ackerman(Ackerman(n-1,m),m-1) n,m>=1
        if (n >= 1 && m >= 1) {
            long t = Ackerman(n - 1, m);
//            if (t == -1) {
//                return -1;
//
//            }
            t = Ackerman(t, m - 1);
            System.out.println("A(" + t + "," + (m - 1) + ")=" + t);

            return t;
        }
        return -1;
    }

    /**
     * ȫ���еڶ��ַ���
     * @param array
     * �����������λ��
     * @param n
     */
    public void perm(int[] array, int n) {
        int i;//, t;
        if (n == array.length) {
            for (i = 0; i < array.length; i++) {
                System.out.print(array[i]);
            }
            System.out.println();
            permCount++;
            return;
        }
        for (i = n; i < array.length; i++) {
            swapArray(array, i, n);
            perm(array, n + 1);
            swapArray(array, n, i);
        }

    }
//?????
    public static int permDriver(int[] array) {
        Integer count = new Integer(0);
        System.out.println("begin");
        perm3(array, 0, count);
        System.out.println("end");
        return count;
    }

    private static void perm3(int[] array, int n, Integer count) {
        int i;//, t;

        if (n == array.length) {
            for (i = 0; i < array.length; i++) {
                System.out.print(array[i]);
            }
            count++;
            System.out.println();
            System.out.println(count.intValue());
            return;
        }
        for (i = n; i < array.length; i++) {
            swapArray(array, i, n);
            perm3(array, n + 1, count);
            swapArray(array, n, i);
        }

    }

    /**
     * 
     * @param array
     * @param k
     * @param i
     */
    public static void swapArray(int[] array, int k, int i) {
        int t = array[k];
        array[k] = array[i];
        array[i] = t;
    }

    /**
     * ȫ���е�һ�ַ���
     * @param array
     * @param n
     * @param result
     */
    public void perm1(int[] array, int n, int[] result) {
        int i;
        if (n == array.length) {
            for (i = 0; i < array.length; i++) {
                System.out.print(result[i]);
            }
            System.out.println();
            permCount++;
            return;
        }
        for (i = n; i < array.length; i++) {
//            if (n != i) {
            result[n] = array[i];
            array[i] = array[n];
            perm1(array, n + 1, result);
            array[i] = result[n];
//            }
        }

    }
    public void test() {
        int[] array = {1, 2, 3, 4, 5};
//        int[] result = new int[5];
//        permCount = 0;

//        perm3(array, 0, count);
        
//        System.out.println(permDriver(array));
        
        perm(array, 0);
        System.out.println(permCount);

    //
//        System.out.println(Fibonacci(3));
//        System.out.println(Fibonacci(8));
//        System.out.println(Fibonacci_Recursion_Edition(8));
    //
//        System.out.println(Fibonacci_Recursion_Edition(0));
//        System.out.println(Fibonacci_Recursion_Edition(1));
//        System.out.println(Fibonacci_Recursion_Edition(2));
//        System.out.println(Fibonacci_Recursion_Edition(3));
//        System.out.println(Fibonacci_Recursion_Edition(4));

    //
//        System.out.println(Recursion.Ackerman(1, 0));
//        System.out.println(Recursion.Ackerman(0, 2));
//        System.out.println(Recursion.Ackerman(2, 2));
//        System.out.println(Recursion.Ackerman(3, 0));
//        System.out.println(Recursion.Ackerman(3, 2));
//        System.out.println(Recursion.Ackerman(2, 4));

//        System.out.println(Recursion.Ackerman(3, 3));
    //���㲻��
//        System.out.println(Recursion.Ackerman(3, 4));
    }
}
