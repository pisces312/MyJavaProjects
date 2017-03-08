/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DynamicProgramming.WeightedInterval.method2;

import java.util.Arrays;
import java.util.Comparator;

/**
 *采用数组的形式
 * @author Administrator
 */
public class WeightedIntervalScheduling2 {

    static Comparator<WeightedInterval2> cmp = new Comparator<WeightedInterval2>() {
//

        public int compare(WeightedInterval2 arg0, WeightedInterval2 arg1) {
            return arg0.endTime > arg1.endTime ? 1 : ((arg0.endTime == arg1.endTime) ? 0 : -1);
        }
    };

    private WeightedIntervalScheduling2() {
    }

    public static void calcP(WeightedInterval2[] queue) {
        for (int i = queue.length - 1; i >= 0; i--) {
            WeightedInterval2 a = queue[i];
            for (int j = i - 1; j >= 0; j--) {
                WeightedInterval2 b = queue[j];
                if (!a.isCollideWith(b)) {
                    a.pOrdinalNum = j + 1;//从1开始,存序数
                    break;
                }
            }
        }
    }

    public static void bruteforceSchedule(WeightedInterval2[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        calcP(queue);
        for (WeightedInterval2 i : queue) {
            System.out.println(i + "  " + i.pOrdinalNum);
        }
        System.out.println(computeOpt(queue, queue.length - 1));
    }

    private static int computeOpt(WeightedInterval2[] queue, int index) {
        if (index < 0) {
            return 0;
        }
        return Math.max(queue[index].getWeight() + computeOpt(queue, queue[index].pOrdinalNum - 1), computeOpt(queue, index - 1));

    }

    /**
     * 采用备忘录的方式
     * @param queue
     */
    public static void mSchedule(WeightedInterval2[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        calcP(queue);
        for (WeightedInterval2 i : queue) {
            System.out.println(i + "  " + i.pOrdinalNum);
        }
        //这里多使用一位，作为0，不存在p的情况
        int[] m = new int[queue.length + 1];
        Arrays.fill(m, 0);
        System.out.println(mComputeOpt(queue, queue.length, m));
    }

    private static int mComputeOpt(WeightedInterval2[] queue, int ordinal, int[] m) {
        if (ordinal < 1) {
            return 0;
        }
        if (m[ordinal] == 0) {//没有计算的情况
            m[ordinal] = Math.max(queue[ordinal - 1].getWeight() + mComputeOpt(queue, queue[ordinal - 1].pOrdinalNum, m), mComputeOpt(queue, ordinal - 1, m));
            return m[ordinal];
        }
        return m[ordinal];


    }

    public static void iterativeSchedule(WeightedInterval2[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        calcP(queue);
        for (WeightedInterval2 i : queue) {
            System.out.println(i + "  " + i.pOrdinalNum);
        }

        System.out.println(Arrays.toString(iterativeComputeOpt(queue)));
    }

    /**
     * 非递归迭代的方法，一次性求出m数组
     * @param queue
     * @return
     */
    private static int[] iterativeComputeOpt(WeightedInterval2[] queue) {
        //这里多使用一位，作为0，不存在p的情况对应值0
        int[] m = new int[queue.length + 1];
        m[0] = 0;
        for (int j = 1; j < m.length; j++) {

            m[j] = Math.max(queue[j - 1].getWeight() + m[queue[j - 1].pOrdinalNum], m[j - 1]);
        }
        return m;
    }

    public static void findAllSchedule(WeightedInterval2[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        calcP(queue);
        for (WeightedInterval2 i : queue) {
            System.out.println(i + "  " + i.pOrdinalNum);
        }

//        Arrays.fill(m, 0);
        //必须给定计算好的m
        int[] m = iterativeComputeOpt(queue);
        System.out.println(Arrays.toString(m));
        int r = findSolution(queue, queue.length, m);
        System.out.println(r);

    }

    /**
     * 递归的形式计算m
     * m中使用序号
     * @param queue
     * @param index
     * @param m
     * @return
     */
    private static int findSolution(WeightedInterval2[] queue, int ordinal, final int[] m) {
        if (ordinal < 1) {
            return 0;
        }
//            return Math.max(queue[ordinal - 1].getWeight() + findSolution(queue, queue[ordinal - 1].pOrdinalNum, m), findSolution(queue, ordinal - 1, m));
        if (queue[ordinal - 1].getWeight() + m[queue[ordinal - 1].pOrdinalNum] >= m[ordinal - 1]) {
            return queue[ordinal - 1].getWeight() + findSolution(queue, queue[ordinal - 1].pOrdinalNum, m);
        }
        return findSolution(queue, ordinal - 1, m);


    }

    public static void test1() {
        WeightedInterval2[] i = {
            new WeightedInterval2("a", 0, 6, 2),
            new WeightedInterval2("b", 1, 10, 4),
            new WeightedInterval2("c", 7, 13, 4),
            new WeightedInterval2("d", 2, 16, 7),
            new WeightedInterval2("e", 14, 19, 2),
            new WeightedInterval2("f", 15, 20, 1), //            new WeightedInterval("g", 6, 10, 1),
        //            new WeightedInterval("h", 8, 11, 1)
        };
//        WeightedIntervalScheduling schedule = new WeightedIntervalScheduling();
//        WeightedInterval[] i = {new WeightedInterval("a", 0, 6, 1),
//            new WeightedInterval("b", 1, 4, 1),
//            new WeightedInterval("c", 3, 5, 1),
//            new WeightedInterval("d", 3, 8, 1),
//            new WeightedInterval("e", 4, 7, 1),
//            new WeightedInterval("f", 5, 9, 1),
//            new WeightedInterval("g", 6, 10, 1),
//            new WeightedInterval("h", 8, 11, 1)
//        };
//        bruteforceSchedule(i);
//        mSchedule(i);
//
//        iterativeSchedule(i);
        findAllSchedule(i);

////        schedule.addInterval(new Interval(0, 1));

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test1();
    }
}
