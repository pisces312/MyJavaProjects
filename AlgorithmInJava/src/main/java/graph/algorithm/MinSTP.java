package graph.algorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import graph.adjacencymatrix.AdjacencyMatrixGraph;
import graph.adjacencymatrix.MVertex;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

import unionfind.linkedlist.LinkedUnionFind;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *��С������
 * @author Administrator
 */
public class MinSTP {

    //    public static <T extends Vertex> void kruskal(Graph<T> g) {
    //        Collection<Edge<T>> edges = g.getEdges();
    //        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>(edges.size(), new Comparator<Edge<T>>() {
    //
    //            @Override
    //            public int compare(Edge<T> o1, Edge<T> o2) {
    //                return o1.cost < o2.cost ? -1 : (o1.cost == o2.cost ? 0 : 1);
    //            }
    ////                throw new UnsupportedOperationException("Not supported yet.");
    //            });
    //        queue.addAll(edges);
    //        ArrayLinkedUnionFind uf = new ArrayLinkedUnionFind(g.getNodeNum());
    //        //???
    ////        HashSet<VNode> set = new HashSet<VNode>(nodes.length);
    //        while (!queue.isEmpty()) {
    //            Edge<T> e = queue.poll();
    //            if (uf.collapsingFind(e.src.id) != uf.collapsingFind(e.dest.id)) {
    //                uf.weightedUnion(e.src.id, e.dest.id);
    //                System.out.println(e);
    //            }
    //        //�ж�·���󣡣�������if (set.add(e.u) || set.add(e.v)) {
    //        }
    //    }

    public static <T extends Vertex> void kruskal(Graph<T> g) {
        Collection<Edge<T>> edges = g.getEdges();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>(edges.size(), new Comparator<Edge<T>>() {


            public int compare(Edge<T> o1, Edge<T> o2) {
                return o1.cost < o2.cost ? -1 : (o1.cost == o2.cost ? 0 : 1);
            }
            //                throw new UnsupportedOperationException("Not supported yet.");
        });
        queue.addAll(edges);
        LinkedUnionFind<T> uf = new LinkedUnionFind<T>(g.getVertexes());

        while (!queue.isEmpty()) {
            Edge<T> e = queue.poll();
            if (!uf.existInSameSet(e.src, e.dest)) {
                //                System.out.println("not in the same set");
                System.out.println(e);
                uf.union(e.src, e.dest);
                //                System.out.println(uf.union(e.u, e.v));

            }
            //�ж�·���󣡣�������if (set.add(e.u) || set.add(e.v)) {
        }
    }

    public static <T extends Vertex> void prim(Graph<T> g, T startNode) {
        Collection<T> nodes = g.getVertexes();
        LinkedUnionFind<T> uf = new LinkedUnionFind<T>(nodes);

        T u2 = null, v2 = null;
        T u = startNode;
        while (true) {
            int min = Integer.MAX_VALUE;
            for (T v : nodes) {
                if (g.isConnected(u, v) && !uf.existInSameSet(u, v)) {
                    int cost = g.getCost(u, v);
                    if (cost < min) {
                        min = cost;
                        u2 = u;
                        v2 = v;
                    }
                }
            }
            if (min != Integer.MAX_VALUE) {
                uf.union(u2, v2);
                System.out.println(u2 + "->" + v2 + "  " + min);
                u = v2;
            } else {
                break;
            }
        }
    }

    public static AdjacencyMatrixGraph<MVertex> topUnDirectedConnectedGraph() {
        AdjacencyMatrixGraph<MVertex> g=new AdjacencyMatrixGraph<MVertex>(false);
        MVertex [] nodes = new MVertex[7];
        for (int i = 0; i < 7; i++) {
            nodes[i] = new MVertex(String.valueOf(i));
        }
        g.addEdge(nodes[0], nodes[1], 2);
        g.addEdge(nodes[0], nodes[4], 5);
        g.addEdge(nodes[0], nodes[6], 1);
        g.addEdge(nodes[1], nodes[2], 7);
        g.addEdge(nodes[1], nodes[4], 3);
        g.addEdge(nodes[1], nodes[5], 7);

        g.addEdge(nodes[2], nodes[3], 1);

        g.addEdge(nodes[4], nodes[5], 4);
        g.addEdge(nodes[4], nodes[6], 6);
        g.addEdge(nodes[5], nodes[6], 2);
        return g;
    }

    public static void main(String [] args) {
        Graph<MVertex> g = topUnDirectedConnectedGraph();
        MVertex[] nodes=g.getVertexesArray(new MVertex[0]);
        MinSTP.prim(g,nodes[0]);
        //        stp.prim();
        //        stp.prim2();
        //        topUnConnectedGraph(g);
    }
}
