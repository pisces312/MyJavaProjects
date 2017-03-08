/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Greedy.SchedulingAllIntervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import Greedy.IntervalSchedulingProblem.Interval;

/**
 *���仮�֣�����ʼʱ�����
 * @author Administrator
 */
public class IntervalPatition {

    LinkedList<Interval> queue = new LinkedList<Interval>();

    public IntervalPatition() {
        //����С��ʼʱ������      
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
//                return arg0.endTime-arg1.endTime;
                return arg0.getStartTime() > arg1.getStartTime() ? 1 : ((arg0.getStartTime() == arg1.getStartTime()) ? 0 : -1);
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        //��ǩ��0��ʼ
        //tag��ʾ�ĸ���ǩ�Ƿ�ʹ�ã�
        boolean[] tag = new boolean[queue.size()];
//        Arrays.fill(tag, false);//ÿ�ζ�Ҫ���¸�ֵ
        int d = 0;
        for (Interval i : queue) {
            Arrays.fill(tag, false);//ÿ�ζ�Ҫ���¸�ֵ
            for (Interval j : queue) {
                //һ��Ч��
//                if (i.getStartTime() == j.getStartTime()&&i.getEndTime()==j.getEndTime()) {
                if (i == j) {
                    break;
                }
                //��������i���ཻ��
                if (j.getEndTime() > i.getStartTime()) {
//                    System.out.println(j.getEndTime() + "   " + i.getStartTime() + "  " + j.getTag());
                    tag[j.getTag()] = true;//�ཻ��Ϊfalse
                } else {//�ҵ�һ�����뽻�ľ��˳�ѭ����
                }
//                m++;
            }
            //�������Ϊ�����ܸ���
            for (int p = 0; p < tag.length; p++) {
                if (!tag[p]) {
                    i.setTag(p);
                    break;
                }
//                else{//��������Ϊfalse���ǵ�ֻ��Ҫ��ѭ����������ʼ��һ�μ���
//                    tag[0]=false;
//                }
            }
        }
        for (Interval interval : queue) {
            if (interval.getTag() > d) {
                d = interval.getTag();
            }
            System.out.println(interval + " " + interval.getTag());
        }

        System.out.println("��" + (d + 1) + "����Դ����");


    }

    /**
     * ����
     * �Ľ�tag��ǩ����ĳ�ʼ���İ汾
     */
    public void schedule2() {
        Collections.sort(queue, new Comparator<Interval>() {

            public int compare(Interval arg0, Interval arg1) {
//                return arg0.endTime-arg1.endTime;
                return arg0.getStartTime() > arg1.getStartTime() ? 1 : ((arg0.getStartTime() == arg1.getStartTime()) ? 0 : -1);
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        //��ǩ��0��ʼ
        //tag��ʾ�ĸ���ǩ�Ƿ�ʹ�ã�
        boolean[] tag = new boolean[queue.size()];
        Arrays.fill(tag, false);//���¸�ֵ

        for (Interval i : queue) {
            for (Interval j : queue) {
                //һ��Ч��
//                if (i.getStartTime() == j.getStartTime()&&i.getEndTime()==j.getEndTime()) {
                if (i == j) {
                    break;
                }
                //��������i���ཻ��
                if (j.getEndTime() > i.getStartTime()) {
//                    System.out.println(j.getEndTime() + "   " + i.getStartTime() + "  " + j.getTag());
                    tag[j.getTag()] = true;//�ཻ��Ϊfalse
                }//�ҵ�һ�����ཻ�ľ��˳�ѭ��������
            }
            //�������Ϊ�����ܸ���
            int p = 0;
            for (p = 0; p < tag.length; p++) {
                if (tag[p]) {//��������Ϊfalse���ǵ�ֻ��Ҫ��ѭ����������ʼ��һ�μ���
                    tag[p] = false;
                } else {
                    i.setTag(p);
                    break;
                }
            }
            for (; p < tag.length; p++) {
                tag[p] = false;
            }
        }
        int d = 0;
        for (Interval interval : queue) {
            if (interval.getTag() > d) {
                d = interval.getTag();
            }
            System.out.println(interval + " " + interval.getTag());
        }

        System.out.println("��" + (d + 1) + "����Դ����");


    }

    public static void test1() {
        IntervalPatition schedule = new IntervalPatition();

        schedule.addInterval(new Interval("e", 4, 7));
        schedule.addInterval(new Interval("f", 5, 9));
        schedule.addInterval(new Interval("g", 6, 10));
        schedule.addInterval(new Interval("h", 8, 11));

        schedule.addInterval(new Interval("a", 0, 6));
        schedule.addInterval(new Interval("b", 1, 4));
        schedule.addInterval(new Interval("c", 3, 5));
        schedule.addInterval(new Interval("d", 3, 8));
//        schedule.addInterval(new Interval(0, 1));
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
