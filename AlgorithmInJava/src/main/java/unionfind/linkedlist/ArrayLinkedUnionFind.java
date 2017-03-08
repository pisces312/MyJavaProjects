/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfind.linkedlist;

/**
 *
 * @author Administrator
 */
public class ArrayLinkedUnionFind {

    int[] parent;
    int n;

    public ArrayLinkedUnionFind(int n) {

        this.n = n;
        parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;

        }
    }

    /**
     * 用以i为根和以j为根的两个不相交集合的并取代它们
     * @param i
     * @param j
     */
    public int simpleUnion(int i, int j) {
        parent[i] = j;
        return j;
    }

    public int simpleFind(int i) {
        while (parent[i] >= 0) {
            i = parent[i];
        }
        return i;
    }

    /**
     * !!i,j必须为根
     * 基于权重规则构造以i和j为根的集合的并，设const(x)是以
     * x为根的树的节点数，则parent[i]=-const(i),parent[j]=-const(j)
     * 利用负数存储计数数据
     * @param i
     * @param j
     */
    public int weightedUnion(int i, int j) {
        int r1=simpleFind(i);
        int r2=simpleFind(j);
        if(r1==r2){//如果在同一个集合中
            return r1;
        }
        int temp = parent[r1] + parent[r2];
        if (parent[r2] > parent[r1]) {//由于是负数，所以树i的节点少
            parent[r1] = r2;
            parent[r2] = temp;
            return r2;
        } else {
            parent[r2] = r1;
            parent[r1] = temp;
            return r1;
        }
    }

    public int collapsingFind(int i) {
        int r;
        for (r = i; parent[r] >= 0; r = parent[r]) {
        }//找到根
        //改变根
        while (i != r) {
            int s = parent[i];
            parent[i] = r;
            i = s;
        }
        return r;
    }
    public void print(){
        for(int i:parent){
            System.out.println(i);
        }
    }
}
