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
            parent.put(e, e);//初始化为指向自身
        }
    }
    public LinkedUnionFind(Collection<T> elements) {
//      parent=new T[elements.length];
      parent = new Hashtable<T, T>();      
      for (T e : elements) {
          parent.put(e, e);//初始化为指向自身
      }
  }

    public boolean existInSameSet(T t1, T t2) {
        return findEx(t1).equals(findEx(t2));
    }
//改造find方法使其直接指向根！！
    //使用这种方法，最后得到结果是右侧均为相同的父节点！！！

    public T findEx(T e) {
        T last = e;
        T next = parent.get(last);
//直到出现自己到自身的映射
        while (last != next) {
            last = next;
            next = parent.get(last);
        }
        //直接指向根
        if (!e.equals(last)) {
            parent.put(e, last);
        }
        return last;
    }

    public T find(T e) {
        T last = e;
        T next = parent.get(last);
//直到出现自己到自身的映射
        while (last != next) {
            last = next;
            next = parent.get(last);
        }
        return last;
    }

    /**
     * 将一个集合中的两个元素合并到一个集合中去
     *
     * 如以下最终结果，左侧为节点，右侧为指向的父节点，
     * 直到找到一个节点的父节点为本身停止，这个节点就
     * 代表整个一个结合，3->3   2->3  1->4->3
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
        //这里必须先找到t1和t2的祖先！！！！
        parent.put(findEx(t2), findEx(t1));//将根改为他、t1
        print();
        return t1;
    }

    /**
     * 显示hashtable中的内容
     */
    public void print() {
        System.out.println();
        for (T t : parent.keySet()) {
            System.out.println(t + " " + parent.get(t));
        }
        System.out.println();
    }
    //根据映射像和原像之间的关系判断是否是一个独立集合，即左等于右的情况
    public void printEachSet(){
//        int c=0;
//        LinkedUnionFind<T> uf=new LinkedUnionFind(elements);
        for(T t:parent.keySet()){
            System.out.println(t+"属于集合"+findEx(parent.get(t)));
        }
    }
}
