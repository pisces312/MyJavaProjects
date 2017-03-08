/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Greedy.OptimalSchedulesInMinimizingLateness;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import Greedy.IntervalSchedulingProblem.Interval;

/**
 *��С�ӳ��е����ŵ���
 * ����ֹʱ��d������˳��
 * @author Administrator
 */
public class OptimalSchedulesInMinimizingLateness {

    LinkedList<Interval> queue = new LinkedList<Interval>();

    public OptimalSchedulesInMinimizingLateness() {
    }

    public void addInterval(Interval i) {
        queue.offer(i);
    }

    /**
     * ��������ȷ�Ľⷨ
     */
    public void schedule() {
        Collections.sort(queue, new Comparator<Interval>() {

            public int compare(Interval arg0, Interval arg1) {
                return arg0.deadLine > arg1.deadLine ? 1 : ((arg0.deadLine == arg1.deadLine) ? 0 : -1);
            }
        });
        int f = 0;
        for (Interval i : queue) {
            i.startTime = f;
            f += i.t;
            i.endTime = f;
             System.out.println(i + " " + i.deadLine);
        }
    }

    public static void test1() {
        OptimalSchedulesInMinimizingLateness schedule = new OptimalSchedulesInMinimizingLateness();



        schedule.addInterval(new Interval("a", 2, 1, true));
        schedule.addInterval(new Interval("b", 5, 3, true));
        schedule.addInterval(new Interval("c", 6, 4, true));
        schedule.addInterval(new Interval("d", 1, 1, true));
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
