package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *�Լ�ʵ�ֵĲ����������
 * @author pisces
 */
public class Random {

    private long seed;
//�����������صĳ���
    public long multiplier = 0x5deece66dl;
    public long adder = 0xbl;
    public long mask = (1l << 48) - 1;
    //Ĭ���õ�ǰʱ����Ϊ����
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
 * ����[0,n-1]֮����������
 * @param n
 * @return
 */
    public int nextInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n ������һ������");

        }
        seed = (seed * multiplier + adder) & mask;
        //�����ӵĸ�16λ������Ժ�,��������16λ��������ȡ�����ӳ�䵽n��
        return ((int) (seed >>> 17) % n);
    }
    /**
     * ����[0,1]֮���ʵ��
     * @return
     */
    public double nextDouble(){
        return nextInt(Integer.MAX_VALUE)/(double)(Integer.MAX_VALUE);
    }
    public void test(){
        
    }
}
