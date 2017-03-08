/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Greedy.SchedulingAllIntervals;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Greedy.IntervalSchedulingProblem.Interval;

/**
 *���仮�֣�����ʼʱ�����
 * @author Administrator
 */
public class IntervalPatitionUsingPriorityQueue {

    PriorityQueue<Interval> queue;

    public IntervalPatitionUsingPriorityQueue() {
        //����С��ʼʱ������
        queue = new PriorityQueue<Interval>(10, new Comparator<Interval>() {

            public int compare(Interval arg0, Interval arg1) {
//                return arg0.endTime-arg1.endTime;
                return arg0.getStartTime() > arg1.getStartTime() ? 1 : ((arg0.getStartTime() == arg1.getStartTime()) ? 0 : -1);
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void addInterval(Interval i) {
//        queue.add(i);
        queue.offer(i);

//        set.add(i);
    }

    public void schedule() {
//        Iterator<Interval> itr = queue.iterator();
        LinkedList<Interval> scheduled = new LinkedList();//������Ⱥõ�����
        int d = 0, tag;//��һ��dΪ0
        for (Interval i : queue) {

            System.out.println("process " + i);
            for (tag = 1; tag <= d; tag++) {//��ÿһ����бȽ�
                boolean isCollide = false;
                for (Interval interval : scheduled) {//���Ѿ�����ǩ�������м���Ƿ�����ײ
                    if (interval.getTag() == tag) {
                        if (i.isCollideWith(interval)) {//����ײ������һ���Ƿ���ײ
                            isCollide = true;
                            System.out.println(i + " collide with " + interval);
                            break;
                        }
                    }
                }
                if (!isCollide) {//��ò��ǩ������ͻ����ò�ı�ǩ
                    break;
                }
            }
            if (tag > d) {//Ҫʹ���±�ǩ�����
                d = tag;
            }
            i.setTag(tag);//ÿһ������һ����һ����ǩ��֮��Ӧ
            scheduled.add(i);
        }
        for (Interval interval : scheduled) {
            System.out.println(interval + " " + interval.getTag());
        }
        System.out.println("��" + d + "����Դ����");


    }

    public static void test1() {
        IntervalPatitionUsingPriorityQueue schedule = new IntervalPatitionUsingPriorityQueue();
        schedule.addInterval(new Interval("a", 0, 6));
        schedule.addInterval(new Interval("b", 1, 4));
        schedule.addInterval(new Interval("c", 3, 5));
        schedule.addInterval(new Interval("d", 3, 8));
        schedule.addInterval(new Interval("e", 4, 7));
        schedule.addInterval(new Interval("f", 5, 9));
        schedule.addInterval(new Interval("g", 6, 10));
        schedule.addInterval(new Interval("h", 8, 11));
//        schedule.addInterval(new Interval(0, 1));
        schedule.schedule();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test1();
    }
}
