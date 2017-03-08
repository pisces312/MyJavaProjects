/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfind.array;

import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class ArrayUnionFind<T> {

    Hashtable<T, T> component = new Hashtable<T, T>();

    //    private ArrayUnionFind() {
    //    }
    public void makeUnionFind(ArraySet<T> set) {
        component.clear();
        Iterator<T> itr = set.getIterator();
        //        component = new int[set.getSize()];
        if (itr.hasNext()) {
            T parent = itr.next();
            component.put(parent, parent);
            while (itr.hasNext()) {
                component.put(itr.next(), parent);
            }

        }

    }
//
//    public static <T> ArraySet<T> union(ArraySet<T> set1, ArraySet<T> set2) {
//        //        Hashtable<Object,Object> flags = new Hashtable<Object,Object>();
//        ArraySet<T> set = new ArraySet<T>();
//        Iterator<T> itr = set1.getIterator();
//        while (itr.hasNext()) {
//            T object = itr.next();
//            set.addElement(object);
//
//        }
//        Iterator<T> itr2 = set2.getIterator();
//        while (itr2.hasNext()) {
//            T object = itr2.next();
//            set.addElement(object);
//
//        }
//        return set;
//
//    }

    public Object find(Object e) {
        return component.get(e);
    }
}
