package collection.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetOperationUtils {
    //交集

    public static <T> Set<T> intersection2(Set<T> a, Set<T> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        a.retainAll(b);
        return a;
    }

    public static <T> Set<T> union2(Set<T> a, Set<T> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        a.removeAll(b);
        a.addAll(b);
        return a;
    }

    /** 求并集 */
    public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
        Set<T> setUnion = new HashSet<T>();
        Iterator<T> iterA = setA.iterator();
        while (iterA.hasNext()) {
            setUnion.add(iterA.next());
        }

        Iterator<T> iterB = setB.iterator();
        while (iterB.hasNext()) {
            setUnion.add(iterB.next());
        }
        return setUnion;
    }

    /** 求交集 */
    public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
        Set<T> setIntersection = new HashSet<T>();
        T item;

        Iterator<T> iterA = setA.iterator();
        while (iterA.hasNext()) {
            item = iterA.next();
            if (setB.contains(item)) {
                setIntersection.add(item);
            }
        }
        return setIntersection;
    }

    /** 求差集 */
    public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
        Set<T> setDifference;
        T item;

        if (setA instanceof TreeSet) {
            setDifference = new TreeSet<T>();
        } else {
            setDifference = new HashSet<T>();
        }

        Iterator<T> iterA = setA.iterator();
        while (iterA.hasNext()) {
            item = iterA.next();
            if (!setB.contains(item)) {
                setDifference.add(item);
            }
        }
        return setDifference;
    }

    /**
     * 判断子集操作 判断setA中的每个元素是否也存在于setB中
     */
    public static <T> boolean subset(Set<T> setA, Set<T> setB) {
        return intersection(setA, setB).size() == setA.size();
    }

    /********************************下面是有序集的操作********************************/
    /** 求交集 */
    public static <T extends Comparable<? super T>> TreeSet<T> orderedIntersection(
            Set<T> a, Set<T> b) {
        TreeSet<T> setIntersection = new TreeSet<T>();
        Iterator<T> iterA = a.iterator(), iterB = b.iterator();
        T valueA, valueB;

        valueA = advance(iterA);
        valueB = advance(iterB);
        while (valueA != null && valueB != null) {
            if (valueA.compareTo(valueB) < 0) {
                valueA = advance(iterA);
            } else if (valueB.compareTo(valueA) < 0) {
                valueB = advance(iterB);
            } else {
                setIntersection.add(valueA);
                valueA = advance(iterA);
                valueB = advance(iterB);
            }
        }
        return setIntersection;
    }

    private static <T> T advance(Iterator<T> iter) {
        T value = null;
        if (iter.hasNext()) {
            value = iter.next();
        }
        return value;
    }

    //////////////////////////////////////////////////////////////////////////
    public static <T> void fillCollection(Collection<T> collection, T [] array) {
        for (T str : array) {
            collection.add(str);
        }
    }

    public static <T extends Comparable<T>> void sortCollection(Collection<T> collection) {
        if (collection == null) {
            return;
        }
        List<T> list = new ArrayList<T>(collection);
        Collections.sort(list);
        //        for (T str : array) {
        //            collection.add(str);
        //        }
    }

    public static void test() {
        String [] arrayA = new String[] {"1", "5", "3", "2", "3", "4"};
        String [] arrayB = new String[] {"3", "4", "4", "5", "6", "7"};
        //        String [] arrayA = new String[] {"1", "2", "3", "3", "4", "5"};
        //        String [] arrayB = new String[] {"3", "4", "4", "5", "6", "7"};

        //        List<String> a = new ArrayList<String>();
        //        fillList(a, arrayA);
        //        List<String> b = new ArrayList<String>();
        //        fillList(b, arrayB);
        Set<String> a = new HashSet<String>();
        fillCollection(a, arrayA);
        Set<String> b = new HashSet<String>();
        fillCollection(b, arrayB);
        System.out.println("A: " + a);
        System.out.println("B: " + b);
        //
        //
        Collection<String> union = null;
        union = union2(a, b);
        sortCollection(union);
        //        Collections.sort((List<String>)union);
        System.out.println("Union: " + union);
        //
        //
        //        Collection<String> intersection = CollectionUtils.intersection(a, b);
        //        Collection<String> disjunction = CollectionUtils.disjunction(a, b);
        //        Collection<String> subtract = CollectionUtils.subtract(a, b);
        //
        //        
        //        Collections.sort((List<String>)intersection);
        //        Collections.sort((List<String>)disjunction);
        //        Collections.sort((List<String>)subtract);
        //
        //
        //        System.out.println("Intersection: " +
        //                intersection);
        //        System.out.println("Disjunction: " +
        //                disjunction);
        //        System.out.println("Subtract: " + subtract);

    }

    public static void main(String [] args) {
        test();
        //        System.out.println(new Object[]{1});
    }

}
