/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfind.array;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Administrator
 */
public class ArraySet<T> {

//    String name;
//    T[] elements;
//    LinkedList<Integer> set = new LinkedList<Integer>();
    LinkedList<T> set = new LinkedList<T>();

    public ArraySet() {
        
    }

//    public ArraySet(String name) {
//        this.name = name;
//    }

    public void addElement(T t) {
        if(set.contains(t))
            return;
        set.add(t);
    }
    public Iterator<T> getIterator(){
        return set.iterator();
    }
    public int getSize(){
//        Class c=T.class;
        
        return set.size();
    }
//    public Class getT(){
//        return T.class;
//    }

//    public ArraySet(T[] elements) {
////        makeUnionFind(component);
//    }
}
