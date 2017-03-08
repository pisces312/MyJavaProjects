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
     * ����iΪ������jΪ�����������ཻ���ϵĲ�ȡ������
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
     * !!i,j����Ϊ��
     * ����Ȩ�ع�������i��jΪ���ļ��ϵĲ�����const(x)����
     * xΪ�������Ľڵ�������parent[i]=-const(i),parent[j]=-const(j)
     * ���ø����洢��������
     * @param i
     * @param j
     */
    public int weightedUnion(int i, int j) {
        int r1=simpleFind(i);
        int r2=simpleFind(j);
        if(r1==r2){//�����ͬһ��������
            return r1;
        }
        int temp = parent[r1] + parent[r2];
        if (parent[r2] > parent[r1]) {//�����Ǹ�����������i�Ľڵ���
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
        }//�ҵ���
        //�ı��
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
