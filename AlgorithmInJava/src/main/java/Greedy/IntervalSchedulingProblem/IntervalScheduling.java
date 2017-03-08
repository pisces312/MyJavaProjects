/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Greedy.IntervalSchedulingProblem;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *������ȣ�������ʱ���������
 * @author Administrator
 */
public class IntervalScheduling {
//���ȶ���ֻ��֤�����ײ�Ϊ����ֵ������һ�����򣡣�����

    PriorityQueue<Interval> queue;
//    HashSet<Interval> set;

    public IntervalScheduling() {
//        set=new HashSet<Interval>();
        //��������ʱ������
        queue = new PriorityQueue<Interval>(10, new Comparator<Interval>() {

            public int compare(Interval arg0, Interval arg1) {
//                return arg0.endTime-arg1.endTime;
                return arg0.endTime > arg1.endTime ? 1 : ((arg0.endTime == arg1.endTime) ? 0 : -1);
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

    }

    public void printIntervals() {
        for (Interval i : queue) {
            for (int j = 0; j < i.startTime; j++) {
                System.out.print("    ");
            }
            System.out.print("[=");
            for (int j = 0; j < i.getLength()-1; j++) {
                System.out.print("====");
            }
            System.out.println("=]");

        }
    }

    public void addInterval(Interval i) {
//        queue.add(i);
        queue.offer(i);

//        set.add(i);
    }

    public void schedule() {
        printIntervals();
//        Iterator<Interval> itr = set.iterator();
        Iterator<Interval> itr = queue.iterator();

        while (itr.hasNext()) {
            Interval i = itr.next();
            itr.remove();
            System.out.println(i);
            Iterator<Interval> itr2 = queue.iterator();
            boolean isRemove = false;
            while (itr2.hasNext()) {
                Interval i2 = itr2.next();
                if (i.isCollideWith(i2)) {//ɾ�������ݵ�����
                    System.out.println("remove " + i2);
                    itr2.remove();
                    isRemove = true;
                }
            }
            if (isRemove) {
                itr = queue.iterator();
            }
        }


    }
    /**
     * ���ݽ���ʱ��������ԣ�ʹ�ø��򵥵��ཻ�Ƚ��㷨
     */
    public void schedule2() {
        printIntervals();
//        Iterator<Interval> itr = set.iterator();
        Iterator<Interval> itr = queue.iterator();

        while (itr.hasNext()) {
            Interval i = itr.next();
            itr.remove();
            System.out.println(i);
            Iterator<Interval> itr2 = queue.iterator();
            boolean isRemove = false;
            while (itr2.hasNext()) {
                Interval i2 = itr2.next();
                //��������Ľ���ʱ���ǵ���˳������ֻҪ�жϿ�ʼʱ�伴��
                if (i.endTime>i2.startTime) {//ɾ�������ݵ�����
                    System.out.println("remove " + i2);
                    itr2.remove();
                    isRemove = true;
                }
            }
            if (isRemove) {
                itr = queue.iterator();
            }
        }


    }

    public static void test1() {
        IntervalScheduling schedule = new IntervalScheduling();
        schedule.addInterval(new Interval("a", 0, 6));
        schedule.addInterval(new Interval("b", 1, 4));
        schedule.addInterval(new Interval("c", 3, 5));
        schedule.addInterval(new Interval("d", 3, 8));
        schedule.addInterval(new Interval("e", 4, 7));
        schedule.addInterval(new Interval("f", 5, 9));
        schedule.addInterval(new Interval("g", 6, 10));
        schedule.addInterval(new Interval("h", 8, 11));
//        schedule.addInterval(new Interval(0, 1));
//        schedule.schedule();
        schedule.schedule2();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test1();
    }
}
