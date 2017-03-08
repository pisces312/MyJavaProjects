/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.IntegerMultiple;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class IntegerMultiple {
//这里限制最大输入整数为32位

    public static long multiple1(long a, long b) {
        long mask = 0xffffffffL;
//        mask = 0xffL;
        //判断乘积符号
        boolean isNegative = false;
        //按照正数计算
        if (Long.signum(a) < 0) {
            a = Math.abs(a);
            a &= mask;
            isNegative = !isNegative;
        }
        if (Long.signum(b) < 0) {
            b = Math.abs(b);
            b &= mask;
            isNegative = !isNegative;
        }
//        System.out.println(isNegative);
//        return 0;
        long r = recursiveMultiple(a, b, mask, Long.bitCount(mask));
        if (isNegative) {
            return -r;
        }
        return r;

    }

    /**
     * 掩码必须是2的幂
     *
     * @param n
     * @return
     */
    public static long getFitMask(long n) {
//        int c = Long.numberOfLeadingZeros(n);
//        if (c % 2 != 0) {
//            c--;
//            if (c < 0) {
//                c = 0;
//            }
//        }
//        return 0xffffffffffffffffL>>>c;

        int c = 64 - Long.numberOfLeadingZeros(n);
        if (c > 16) {
            return 0xffffffffL;
        }
        if (c > 8) {
            return 0xffff;
        }
        if (c > 4) {
            return 0xff;
        }
        return 0xf;
    }
    //取最短的宽度

    public static long multipleEx(long a, long b) {
        long mask = 0xffffffffL;
//        mask = 0xffL;
        //判断乘积符号
        boolean isNegative = false;
        //按照正数计算
        if (Long.signum(a) < 0) {
            a = Math.abs(a);
            a &= mask;
            isNegative = !isNegative;
        }
        if (Long.signum(b) < 0) {
            b = Math.abs(b);
            b &= mask;
            isNegative = !isNegative;
        }
        long ma = getFitMask(a);
        long mb = getFitMask(b);
        mask = (ma > mb ? ma : mb);
//        System.out.println(isNegative);
//        return 0;
        System.out.println(Long.bitCount(mask));
        long r = recursiveMultiple(a, b, mask, Long.bitCount(mask));
        if (isNegative) {
            return -r;
        }
        return r;
    }

    /**
     * 对正整数进行递归计算
     * @param a
     * @param b
     * @param mask
     * @param len
     * @return
     */
    private static long recursiveMultiple(long a, long b, long mask, int len) {
        System.out.println("a=" + a + "  b=" + b);
        if (a < 10 && b < 10) {//降到个位
            return a * b;
        }
//        if (len == 0) {
//            System.out.println("!!!!!!!!!len=0");
//            return 1;
//        }
        int oldLen = len;
        len >>>= 1;
        mask >>>= len;
//        long w0 = mask + 1;
        long x0 = a & mask;
        long x1 = a >> len;
        long y0 = b & mask;
        long y1 = b >> len;
        System.out.println("x1=" + x1 + " x0=" + x0 + " y1=" + y1 + " y0=" + y0 + "  len=" + len + "   mask=" + Long.toBinaryString(mask));

        long p = recursiveMultiple(x1 + x0, y1 + y0, mask, len);
        //下面一定是一半的掩码
        long x1y1 = recursiveMultiple(x1, y1, mask, len);
        long x0y0 = recursiveMultiple(x0, y0, mask, len);
        return (x1y1 << oldLen) + ((p - x1y1 - x0y0) << len) + x0y0;
//        return (x1y1 | w1) + ((p - x1y1 - x0y0) | w0) + x0y0;




    }
    //利用大整数类测试结果是否正确

    public static void checkResult(long a, long b) {
        BigDecimal b1 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        BigDecimal r = b1.multiply(b2);
        System.out.println(r);

    }

    public static void main(String[] args) {
        //!!!!!必须加L,否则被认为是整型！！！
//        long mask=0x0ffffffffL;
//        mask=0x0fffffff;
//        mask>>>=32;
//        System.out.println(Long.toBinaryString(mask));
//        mask>>>=16;
//        System.out.println(Long.bitCount(mask));
//        System.out.println(Long.toBinaryString(mask));
//        System.out.println(multiple1(10, 10));
//        System.out.println(multiple1(-10, 10));
//        System.out.println(multiple1(-3, -5));
//        System.out.println(multiple1(1234, 1000));
//        System.out.println(multiple1(70, -80));
        long a = 1245154455L, b = -7954315653L;
        a = 1245154455L; b = -795431565L;
//        a = 1234;
//        b = 2345;
//        System.out.println(Long.numberOfLeadingZeros(a));
//        System.out.println(Long.bitCount(a));
//        System.out.println(multiple1(a, b));
        System.out.println(multipleEx(a, b));
        checkResult(a, b);
    }
}
