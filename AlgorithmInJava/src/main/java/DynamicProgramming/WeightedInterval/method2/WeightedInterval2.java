/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DynamicProgramming.WeightedInterval.method2;

/**
 *
 * @author Administrator
 */
public class WeightedInterval2 {

    String name;
    int startTime;
    int endTime;
//    int tag;//��ǩ
    int weight;
    public int pOrdinalNum = 0;//默认为0,保存往前找最近的相容的区间的序数，即从1开始
//    public WeightedInterval2 pInterval = null;//保存往前找最近的相容的区间的下标

    public WeightedInterval2(int startTime, int endTime, int weight) {
        this("unknows", startTime, endTime, weight);
//        this.startTime = startTime;
//        this.endTime = endTime;
//        name = "unknown";
    }

    public int getWeight() {
        return weight;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public WeightedInterval2(String name, int startTime, int endTime, int weight) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        if (weight < 0) {
            System.err.print("权值必须大于0");
            System.exit(1);
        }
        this.weight = weight;
//        p = null;
//        p=0;
    }

    public int getLength() {
        return endTime - startTime;
    }
//!!!!3�����

    public boolean isCollideWith(WeightedInterval2 i) {
        if (i.getLength() > getLength()) {//����Ƚϵ����ϳ����򵹹�4
            return i.isCollideWith(this);
        }
        //���������?�ȱȴ�Ƚϵ���䳤�ȶ̵����
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
