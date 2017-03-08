package 第5条消除过期的对象引用;

import java.util.*;

// Can you spot the "memory leak"?
/**
 * 由数组引起
 * 只要一个类自己管理它的内存，就该警惕内存泄露问题
 * 一旦一个元素被释放，该元素中包含的任何对象应用应该被及时清空
 * @author DELL
 */
public class Stack {

    private Object[] elements;
    private int size = 0;
/**
 * 初始化为定长栈，可能导致收缩后，释放的对象引用不能被及时清空
 * @param initialCapacity
 */
    public Stack(int initialCapacity) {
        this.elements = new Object[initialCapacity];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }
/**
 * 正确的清空对象应用的方法
 * @return
 */
    public Object pop() {
        if (size==0)
            throw new EmptyStackException();
        Object result = elements[--size];
        // Eliminate obsolete reference!!!
        elements[size] = null; 
        return result;
    }

    /**
     * Ensure space for at least one more element, roughly
     * doubling the capacity each time the array needs to grow.
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            Object[] oldElements = elements;
            elements = new Object[2 * elements.length + 1];
            System.arraycopy(oldElements, 0, elements, 0, size);
        }
    }

    public static void main(String[] args) {
        Stack s = new Stack(0);
        for (int i=0; i<args.length; i++)
            s.push(args[i]);
        for (int i=0; i<args.length; i++)
            System.out.println(s.pop());
    }
}
