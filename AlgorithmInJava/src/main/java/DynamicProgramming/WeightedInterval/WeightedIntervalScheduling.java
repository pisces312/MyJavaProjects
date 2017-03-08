/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DynamicProgramming.WeightedInterval;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class WeightedIntervalScheduling {

    static Comparator<WeightedInterval> cmp = new Comparator<WeightedInterval>() {
//

        public int compare(WeightedInterval arg0, WeightedInterval arg1) {
//                return arg0.endTime-arg1.endTime;
            return arg0.endTime > arg1.endTime ? 1 : ((arg0.endTime == arg1.endTime) ? 0 : -1);
//                throw new UnsupportedOperationException("Not supported yet.");
        }
    };
//WeightedInterval[] queue;
//    LinkedList<WeightedInterval> queue = new LinkedList<WeightedInterval>();

    private WeightedIntervalScheduling() {
//        queue = new PriorityQueue<WeightedInterval>(10, new Comparator<WeightedInterval>() {
//
//            public int compare(WeightedInterval arg0, WeightedInterval arg1) {
////                return arg0.endTime-arg1.endTime;
//                return arg0.endTime > arg1.endTime ? 1 : ((arg0.endTime == arg1.endTime) ? 0 : -1);
////                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        });
    }

//    public void addInterval(WeightedInterval i) {
////        queue.add(i);
//        queue.offer(i);
//
////        set.add(i);
//    }
    public static void bruteforceSchedule(WeightedInterval[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        for (int i = queue.length - 1; i >= 0; i--) {
            WeightedInterval a = queue[i];
            for (int j = i - 1; j >= 0; j--) {
                WeightedInterval b = queue[j];
                if (!a.isCollideWith(b)) {
                    a.pIndex = j;
//                    a.p = b;
                    break;
                }
            }
        }
        for (WeightedInterval i : queue) {
            System.out.println(i + "  " + i.pIndex);
        }
        System.out.println(computeOpt(queue, queue.length - 1));
    }

    private static int computeOpt(WeightedInterval[] queue, int j) {
        if (j == -1) {
            return 0;
        }
        return Math.max(queue[j].getWeight() + computeOpt(queue, queue[j].pIndex), computeOpt(queue, j - 1));

    }

    /**
     * 采用备忘录的方式
     * @param queue
     */
    public static void mSchedule(WeightedInterval[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        for (int i = queue.length - 1; i >= 0; i--) {
            WeightedInterval a = queue[i];
            for (int j = i - 1; j >= 0; j--) {
                WeightedInterval b = queue[j];
                if (!a.isCollideWith(b)) {
                    a.pIndex = j;
//                    a.p = b;
                    break;
                }
            }
        }
        for (WeightedInterval i : queue) {
            System.out.println(i + "  " + i.pIndex);
        }
        //这里多使用一位，作为0，不存在p的情况
        int[] m = new int[queue.length];
        Arrays.fill(m, -1);
        System.out.println(mComputeOpt(queue, queue.length - 1, m));
    }

    private static int mComputeOpt(WeightedInterval[] queue, int j, int[] m) {
        if (j < 0) {
            return 0;
        }
        if (m[j] == -1) {
            m[j] = Math.max(queue[j].getWeight() + mComputeOpt(queue, queue[j].pIndex, m), mComputeOpt(queue, j - 1, m));
            return m[j];
        }
        return m[j];


    }

    public static void iterativeSchedule(WeightedInterval[] queue) {
        //排序
        Arrays.sort(queue, cmp);
        //先计算p
        for (int i = queue.length - 1; i >= 0; i--) {
            WeightedInterval a = queue[i];
            for (int j = i - 1; j >= 0; j--) {
                WeightedInterval b = queue[j];
                if (!a.isCollideWith(b)) {
                    a.pIndex = j;
//                    a.p = b;
                    break;
                }
            }
        }
        for (WeightedInterval i : queue) {
            System.out.println(i + "  " + i.pIndex);
        }

        System.out.println(Arrays.toString(iterativeComputeOpt(queue)));
    }

    /**
     * 非递归迭代的方法，一次性求出m数组
     * @param queue
     * @return
     */
    private static int[] iterativeComputeOpt(WeightedInterval[] queue) {
        //这里多使用一位，作为0，不存在p的情况对应值0
        int[] m = new int[queue.length];

        for (int j = 0; j < m.length; j++) {
            int mPrevious = 0;
            if (queue[j].pIndex >= 0) {//考虑-1的情况
                mPrevious = m[queue[j].pIndex];
            }
            int m2 = 0;
            if (j - 1 >= 0) {
                m2 = m[j - 1];
            }
            m[j] = Math.max(queue[j].getWeight() + mPrevious, m2);
        }
        return m;
    }

    public static int findSolution(WeightedInterval[] queue, int j, int[] m) {
        if (j < 0) {
            return 0;
        }

        if (queue[j].getWeight() + m[queue[j].pIndex] >= m[j - 1]) {
            return findSolution(queue, queue[j].pIndex, m);
        }
        return findSolution(queue, j - 1, m);

    }

    public static void test1() {
        WeightedInterval[] i = {
            new WeightedInterval("a", 0, 6, 2),
            new WeightedInterval("b", 1, 10, 4),
            new WeightedInterval("c", 7, 13, 4),
            new WeightedInterval("d", 2, 16, 7),
            new WeightedInterval("e", 14, 19, 2),
            new WeightedInterval("f", 15, 20, 1), //            new WeightedInterval("g", 6, 10, 1),
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
//        bruteforceScheduleUsingIndex(i);
//        mSchedule(i);

        iterativeSchedule(i);

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
