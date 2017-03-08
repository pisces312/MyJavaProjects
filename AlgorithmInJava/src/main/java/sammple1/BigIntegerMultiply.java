package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *n位二进制大整数乘法
 * @author pisces
 */
public class BigIntegerMultiply {
//存储整个整数
//    byte[] X, Y;
//    byte[] A, B, C, D;
    //
//    long longX,longY;
//    long longA,longB,longC,longD;
    /**
     *第一个版本,使用字符串处理，
     * 同为n位的二进制大整数的乘法
     * 并且n必须为偶数
     * 分两段的情况
     * @param x
     * @param y
     */
    public long multiply1(long x, long y) {
        long A = 0, B = 0, C = 0, D = 0;
        //返回最高位如，100 返回64
//        System.out.println(Long.highestOneBit(x));
        String xStr = Long.toBinaryString(x);
        String yStr = Long.toBinaryString(y);
        int xLen = xStr.length();
        int yLen = yStr.length();
        int halfBitCount = xStr.length() / 2;
        if (xLen != yLen) {
            return -1;
        }
        //自动填0使两个整数的位数相同且为偶数
//        if (xLen > yLen) {
////            xLen-yLen
//        } else if (xLen < yLen) {
//
//        }
//
//        if (xStr.length() != yStr.length()) {
//            return -1;
//        }

        if (MathUtil.isOdd(xStr.length())) {
            return -1;
        }
        System.out.println(xStr);
        System.out.println(yStr);


        System.out.println(halfBitCount);
        A = getAFromBinaryString(xStr, halfBitCount);
        B = getBFromBinaryString(xStr, halfBitCount);
        C = getAFromBinaryString(yStr, halfBitCount);
        D = getBFromBinaryString(yStr, halfBitCount);
        System.out.println(A + " " + B + " " + C + " " + D);

//        int bitCount = Long.bitCount(x);
//        Long.toBinaryString(x)
        long t1 = A * C, t2 = B * D;
//        System.out.println(t1 + " " + t2);
//        System.out.println(t1 << xStr.length());
//        System.out.println(4<<1);
        return (t1 << xStr.length()) + (((A - B) * (D - C) + t1 + t2) << halfBitCount) + t2;
    }

    /**
     * 自定义位数n,n必须为偶数，如果原数不够则在前面补零,有位数限制！！
     * @param x
     * @param y
     * @param n
     * @return
     */
    public long multiply2(long x, long y, int n) {
        if (n > Long.bitCount(Long.MAX_VALUE) || MathUtil.isOdd(n)) {
            return -1;

        }
        int halfBitCount = n / 2;
        long A = x >>> halfBitCount, B = getSubLong(x, halfBitCount), C = y >>> halfBitCount, D = getSubLong(y, halfBitCount);
        System.out.println(A + " " + B + " " + C + " " + D);
        long t1 = A * C, t2 = B * D;
        return (t1 << n) + (((A - B) * (D - C) + t1 + t2) << halfBitCount) + t2;
    }
    //从后n位截取整数    
    public long getSubLong(final long x, final int n) {
        long result = 0;
        int key = 0x1;
        for (int i = 0; i < n; i++) {
            result += (key & x);
            key <<= 1;
        }
        return result;
    }

    public long getAFromBinaryString(String str, int len) {
        return Long.parseLong(str.substring(0, len), 2);
    }

    public long getBFromBinaryString(String str, int len) {
        return Long.parseLong(str.substring(len), 2);
    }
    //在前面添加零
//public String add0(int n){
//    
//}
    /**
     * 读入任意长度的整数！输入为二进制
     * @param x
     * @param y
     * @param n
     * @return
     */
    public long multiply3(byte[] binaryX, byte[] binaryY, int n) {
        binaryX = getDecFromBin(binaryX);
        binaryY = getDecFromBin(binaryY);
        return -1;
    }

    public byte[] getDecFromBin(byte[] x) {
        if (x == null || x.length == 0) {
            return null;
        }
        int resultLen = x.length / 2;
        if (resultLen == 0) {
            resultLen = 1;
        }

//        System.out.println(resultLen);
        //十进制数最大长度不超过二进制的位数的一半
        //只有二进制数为1位时，十进制为1位
        byte[] result = new byte[resultLen];
//        int decBitCount = 0;
        int key = 0x1;
        int i;
        int t = 0, p = x.length - 1;
        for (i = x.length - 1; i >= 0; i--) {
            t = x[i] * key;
            p = result.length - 1;
            //当该位不为0时才加到result中
            while (t > 0) {
                result[p] += (byte) (t % 10);
                if (result[p] > 9) {
                    result[p] -= 10;
                    result[p - 1]++;
                }
                p--;
                t /= 10;
            }
//            if (decBitCount < result.length - 1 - p) {
//                decBitCount = result.length - p;
//            }
            key <<= 1;
        }
//        System.out.println(decBitCount);
        for (i = 0; i < result.length; i++) {
            if (result[i] != 0) {
//                System.out.println(result.length);
//                System.out.println(i);
//                System.out.println(result.length - i);
                result = changeByteSize(result, result.length - i);
                break;
            }
        }
//        Long.
//        System.out.println(sum);
        return result;
    }

    /**
     * 更改数组大小
     * @param x
     * @param size
     * @return
     */
    public byte[] changeByteSize(byte[] x, int size) {
        if (size < 0 || x == null) {
            return null;
        }
        //长度不变的情况
        if (size == x.length) {
            return x;
        }
        byte[] result = new byte[size];
        System.arraycopy(x, x.length - size, result, 0, size);
        return result;
    }

    /**
     * 扩展数组大小,以乘2方式,
     * @param x
     * @return
     */
    public byte[] expandByte(byte[] x) {
        if (x == null || x.length == 0) {
            return null;
        }
        byte[] result = new byte[x.length * 2];
        System.arraycopy(x, 0, result, result.length - x.length, x.length);
        return result;
    }

    /**
     * 将一个数组表示的数移位操作，正表示左移，负表示右移
     * @param x
     * @param n
     * @return
     */
    public byte[] bitMove(byte[] x, int n) {
        int i;
        byte[] result = x;
        if (n != 0) {
            result = new byte[x.length + n];
        }
        //左移需要增加位数
        if (n > 0) {
            System.arraycopy(x, 0, result, 0, x.length);
            for (i = x.length; i < result.length; i++) {
                result[i] = 0;
            }
        } else if (n < 0) {
            System.arraycopy(x, 0, result, 0, result.length);
        }
        return result;
    }

    public void printByte(byte[] x) {
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i]);
        }
        System.out.println();
    }

    public void test() {
        //保证都是n位
//        System.out.println(multiply1(41, 40));
        System.out.println(multiply2(112, 10, 10));
//        byte[] x = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
//        printByte(x);
//        printByte(bitMove(x, 2));
//        printByte(getDecFromBin(x));
//        byte[] y = expandByte(x);
//        printByte(y);
//
//        printByte(changeByteSize(y, 2));

    //不能有前缀0！！是8进制
//        multiply2(00100, 200);
//        multiply2(0100, 200);
    }
}
