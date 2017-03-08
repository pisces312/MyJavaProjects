/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfind.linkedlist;

import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class LinkedUnionFind<T> {

    Hashtable<T, T> parent;
//    T[] parent;/*=new T[1]*/;

    public LinkedUnionFind(T[] elements) {
//        parent=new T[elements.length];
        parent = new Hashtable<T, T>(elements.length);
        for (T e : elements) {
            parent.put(e, e);//��ʼ��Ϊָ������
        }
    }
    public LinkedUnionFind(Collection<T> elements) {
//      parent=new T[elements.length];
      parent = new Hashtable<T, T>();      
      for (T e : elements) {
          parent.put(e, e);//��ʼ��Ϊָ������
      }
  }

    public boolean existInSameSet(T t1, T t2) {
        return findEx(t1).equals(findEx(t2));
    }
//����find����ʹ��ֱ��ָ�������
    //ʹ�����ַ��������õ�������Ҳ��Ϊ��ͬ�ĸ��ڵ㣡����

    public T findEx(T e) {
        T last = e;
        T next = parent.get(last);
//ֱ�������Լ��������ӳ��
        while (last != next) {
            last = next;
            next = parent.get(last);
        }
        //ֱ��ָ���
        if (!e.equals(last)) {
            parent.put(e, last);
        }
        return last;
    }

    public T find(T e) {
        T last = e;
        T next = parent.get(last);
//ֱ�������Լ��������ӳ��
        while (last != next) {
            last = next;
            next = parent.get(last);
        }
        return last;
    }

    /**
     * ��һ�������е�����Ԫ�غϲ���һ��������ȥ
     *
     * ���������ս�������Ϊ�ڵ㣬�Ҳ�Ϊָ��ĸ��ڵ㣬
     * ֱ���ҵ�һ���ڵ�ĸ��ڵ�Ϊ����ֹͣ������ڵ��
     * ��������һ����ϣ�3->3   2->3  1->4->3
     * 0->6->1->4->3           6->1->4->3
     * 
     *
     *
     *
     * 3 3
    2 3
    1 4
    0 6
    6 1
    5 6
    4 3

     *
     * @param t1
     * @param t2
     * @return
     */
    public T union(T t1, T t2) {
//        System.out.println("union");
        //����������ҵ�t1��t2�����ȣ�������
        parent.put(findEx(t2), findEx(t1));//������Ϊ����t1
        print();
        return t1;
    }

    /**
     * ��ʾhashtable�е�����
     */
    public void print() {
        System.out.println();
        for (T t : parent.keySet()) {
            System.out.println(t + " " + parent.get(t));
        }
        System.out.println();
    }
    //����ӳ�����ԭ��֮��Ĺ�ϵ�ж��Ƿ���һ���������ϣ���������ҵ����
    public void printEachSet(){
//        int c=0;
//        LinkedUnionFind<T> uf=new LinkedUnionFind(elements);
        for(T t:parent.keySet()){
            System.out.println(t+"���ڼ���"+findEx(parent.get(t)));
        }
    }
}
