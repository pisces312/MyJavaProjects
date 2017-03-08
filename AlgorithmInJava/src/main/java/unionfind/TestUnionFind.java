/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unionfind;

import graph.adjacencymatrix.MVertex;
import unionfind.array.ArraySet;
import unionfind.array.ArrayUnionFind;
import unionfind.linkedlist.ArrayLinkedUnionFind;
import unionfind.linkedlist.LinkedUnionFind;

/**
 *
 * @author Administrator
 */
public class TestUnionFind {

    public static void testArraySet() {
        ArraySet<MVertex> set1 = new ArraySet<MVertex>();
        MVertex [] nodes = {new MVertex("1"), new MVertex("2")};
        set1.addElement(nodes[0]);
        set1.addElement(nodes[1]);
        ArrayUnionFind<MVertex> uf = new ArrayUnionFind<MVertex>();
        uf.makeUnionFind(set1);
        System.out.println(uf.find(nodes[0]).equals(uf.find(nodes[1])));

    }

    public static void testArrayLinkedSet() {
        ArrayLinkedUnionFind uf = new ArrayLinkedUnionFind(10);
        System.out.println(uf.simpleFind(3));
        uf.simpleUnion(3, 5);
        System.out.println(uf.simpleFind(3));
        //        uf.weightedUnion(1, 3);
        //        System.out.println(uf.weightedUnion(3, 5));
        System.out.println(uf.weightedUnion(1, uf.weightedUnion(3, 5)));
        System.out.println(uf.simpleFind(1));
        //        uf.print();
        System.out.println(uf.collapsingFind(3));
    }

    public static void testLinkedSet() {
        MVertex [] nodes = {new MVertex("1"), new MVertex("2"), new MVertex("3"), new MVertex("4"), new MVertex("5")};
        LinkedUnionFind<MVertex> uf = new LinkedUnionFind<MVertex>(nodes);
        uf.union(uf.union(nodes[0], nodes[1]), uf.union(nodes[2], nodes[3]));
        //        System.out.println(uf.find(nodes[0]).equals(uf.find(nodes[1])));
        System.out.println(uf.existInSameSet(nodes[0], nodes[4]));
        System.out.println(uf.existInSameSet(nodes[0], nodes[3]));
    }

    public static void main(String [] args) {
        //        testUnionFind();
        //        testLinkedSet();
        //        testArrayLinkedSet();
        //        testArraySet();
        //        HashSet set=new HashSet();

    }
}
