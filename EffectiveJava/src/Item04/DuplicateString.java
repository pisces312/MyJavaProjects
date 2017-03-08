/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package 第4条避免创建重复的对象;

/**
 *
 * @author DELL
 */
public class DuplicateString {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        long end;
        int times=10000;
        /**
         * 每执行一次就要创建新的String对象
         * 1.5以后不能使用new！！
         */
//        for(int i=0;i<times;i++)
//        String s=new String("silly");
//                String s=new String('s');
        /**
         * 每次执行都是第一次创建的对象
         */
        String s2="No longer silly";
    }
}
