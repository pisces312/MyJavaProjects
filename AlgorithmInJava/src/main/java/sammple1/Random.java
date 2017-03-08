package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *自己实现的产生随机数类
 * @author pisces
 */
public class Random {

    private long seed;
//和随机性能相关的乘数
    public long multiplier = 0x5deece66dl;
    public long adder = 0xbl;
    public long mask = (1l << 48) - 1;
    //默认用当前时间作为种子
    public Random() {
        seed = System.currentTimeMillis();
    }

    public Random(long seed) {
        if (seed == 0) {
            this.seed = System.currentTimeMillis();
        } else {
            this.seed = seed;
        }
    }
/**
 * 产生[0,n-1]之间的随机整数
 * @param n
 * @return
 */
    public int nextInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n 必须是一个正数");

        }
        seed = (seed * multiplier + adder) & mask;
        //新种子的高16位的随机性好,将其右移16位，在利用取余操作映射到n内
        return ((int) (seed >>> 17) % n);
    }
    /**
     * 产生[0,1]之间的实数
     * @return
     */
    public double nextDouble(){
        return nextInt(Integer.MAX_VALUE)/(double)(Integer.MAX_VALUE);
    }
    public void test(){
        
    }
}
