/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Greedy.IntervalSchedulingProblem;

/**
 *
 * @author Administrator
 */
public class Interval {

    String name;
    public int startTime;
    public int endTime;
    //��ֹʱ��
    public int deadLine;
    //����
    public int t;
    int tag = -1;//��ǩĬ��Ϊ-1

    public Interval(int startTime, int endTime) {
        this("unknows", startTime, endTime);
//        this.startTime = startTime;
//        this.endTime = endTime;
//        name = "unknown";
    }

    /**
     * ��Ӧ����С�ӳٵ���
     * @param d
     */
    public Interval(String name,int d, int t,boolean flag) {
        this.name=name;
        this.deadLine = d;
        this.t = t;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public Interval(String name, int startTime, int endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        tag = 0;//Ĭ��Ϊ0
    }

    public int getLength() {
        return endTime - startTime;
    }
//!!!!3�����

    public boolean isCollideWith(Interval i) {
        if (i.getLength() > getLength()) {//������Ƚϵ�����ϳ����򵹹���
            return i.isCollideWith(this);
        }
        //�����������ȱȴ��Ƚϵ����䳤�ȶ̵����
        if (startTime <= i.startTime && i.startTime < endTime) {
//            System.out.println("1");
            return true;
        }
        if (startTime < i.endTime && i.endTime <= endTime) {
//            System.out.println("2");
            return true;
        }


        return false;
    }

    @Override
    public String toString() {
        return name + " [" + startTime + "--" + endTime + "]";
    }
}
