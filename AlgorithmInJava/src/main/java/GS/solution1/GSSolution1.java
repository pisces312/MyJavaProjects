/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution1;

import java.util.HashSet;

/**
 *
 * @author root
 */
public class GSSolution1 {

    String[] women = {"Amy", "Bertha", "Clare", "Diane", "Erika"};
    int[][] menPrefId = {
        {1, 0, 3, 4, 2},
        {3, 1, 0, 2, 4},
        {1, 4, 2, 3, 0},
        {0, 3, 2, 1, 4},
        {1, 3, 0, 4, 2}
    };
    //               0       1         2       3      4
    String[] men = {"Victor", "Wyatt", "Xavier", "Yancey", "Zeus"};
    int[][] womenPrefId = {
        {4, 0, 1, 3, 2},
        {2, 1, 3, 0, 4},
        {1, 2, 3, 4, 0},
        {0, 4, 3, 2, 1},
        {3, 1, 4, 2, 0}
    };
    //������һ��Ҫ����Ů�˵��±�,��ʼΪ0������һ����õ�Ů��
    int[] nextId;
    //Ů�˵�ǰԼ������˵��±�,Ҳ����������
    int[] currentId;
    public static final int FREE = -1;
    public static final int FIRST_WOMAN = 0;
//    public static final int DATE=1;
    int[][] ranking;
    int menNum, womenNum, num;
    //�ü��ϱ������ɵ�����
    HashSet<Integer> freeSet;
//    int[] matchResult;
//    LinkedList<Integer> nextWoman,currentMan;

    public GSSolution1() {
        menNum = men.length;
        womenNum = women.length;
        num = menNum;
        nextId = new int[menNum];
        currentId = new int[womenNum];
//��ӡ�������ȱ�
        for (int i = 0; i < menNum; i++) {
            System.out.print(men[i] + " ");
            for (int j = 0; j < womenNum; j++) {
                System.out.print(women[menPrefId[i][j]] + " ");
            }
            System.out.println();
        }
        System.out.println();
        //��ӡŮ�����ȱ�
        for (int i = 0; i < womenNum; i++) {
            System.out.print(women[i] + " ");
            for (int j = 0; j < menNum; j++) {
                System.out.print(men[womenPrefId[i][j]] + " ");
            }
            System.out.println();
        }
        freeSet = new HashSet<Integer>();
        resolve(womenPrefId, menPrefId);
//        manPriority();
        printResult();
        resolve(menPrefId,womenPrefId);
        printResult();
    }
/**
 * ͨ���趨�������ȱ�ı����˺�Ů��֮����������
 * @param low
 * @param high
 */
    public void resolve(int[][] low, int[][] high) {
        //��������ȫƥ������,����������Ů�������
        for (int i = 0; i < num; i++) {
            //��ʼ��������Ҫ����ϵһ��Ů�˵��±�
            nextId[i] = FIRST_WOMAN;
            //��ʼ����ǰŮ��Լ������˵��±�Ϊ-1
            currentId[i] = FREE;
        }

        //�����������������±�
        ranking = new int[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                //������ǰ�ģ�ֵ��
                ranking[i][low[i][j]] = j;
            }
        }
        for (int i = 0; i < num; i++) {
            freeSet.add(i);
        }
        int m,   m2,   w;
//        Iterator<Integer> freeMenItr=freeSet.iterator();
        //������������˲��Ҹ���������û��������Ů����飬��ִ��ѭ��
        while (!freeSet.isEmpty() && nextId[m = freeSet.iterator().next()] < num) {
            //��w��m�����ȱ���m��û���������������Ů��
            w = high[m][nextId[m]];

            nextId[m]++;
            m2 = currentId[w];
            if (m2 == FREE) {
//                matchResult[m]=w;
                currentId[w] = m;
                //��ʱm��������
                freeSet.remove(m);

            } else {
                //�����ߵķ���ǰ
                if (ranking[w][m2] < ranking[w][m]) {
                    //m��������
                } else {
                    currentId[w] = m;
//                    matchResult[m]=w;
                    freeSet.remove(m);
                    freeSet.add(m2);
                }

            }
        }
    }

    public void manPriority() {
        //��������ȫƥ������,����������Ů�������
        for (int i = 0; i < num; i++) {
            //��ʼ��������Ҫ����ϵһ��Ů�˵��±�
            nextId[i] = FIRST_WOMAN;
            //��ʼ����ǰŮ��Լ������˵��±�Ϊ-1
            currentId[i] = FREE;
        }

        //�����������������±�
        ranking = new int[womenNum][menNum];
        for (int i = 0; i < womenNum; i++) {
            for (int j = 0; j < menNum; j++) {
                //������ǰ�ģ�ֵ��
                ranking[i][womenPrefId[i][j]] = j;
            }
        }
        //��ʼ����������
        for (int i = 0; i < menNum; i++) {
            freeSet.add(i);
        }
        int m,   m2,   w;
//        Iterator<Integer> freeMenItr=freeSet.iterator();
        //������������˲��Ҹ���������û��������Ů����飬��ִ��ѭ��
        while (!freeSet.isEmpty() && nextId[m = freeSet.iterator().next()] < womenNum) {
            //��w��m�����ȱ���m��û���������������Ů��
            w = menPrefId[m][nextId[m]];

            nextId[m]++;
            m2 = currentId[w];
            if (m2 == FREE) {
//                matchResult[m]=w;
                currentId[w] = m;
                //��ʱm��������
                freeSet.remove(m);

            } else {
                //�����ߵķ���ǰ
                if (ranking[w][m2] < ranking[w][m]) {
                    //m��������
                } else {
                    currentId[w] = m;
//                    matchResult[m]=w;
                    freeSet.remove(m);
                    freeSet.add(m2);
                }

            }
        }


    }

    public void printResult() {
        //��ӡ����
//        for (int i = 0; i < womenNum; i++) {
//            System.out.print(women[i] + " ");
//            for (int j = 0; j < menNum; j++) {
//                System.out.print(ranking[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        //��ӡ���
        System.out.println("==============");
        for (int i = 0; i < menNum; i++) {
            System.out.println(men[i] + " " + women[currentId[i]]);
        }
        System.out.println("==============\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new GSSolution1();
    }
}
