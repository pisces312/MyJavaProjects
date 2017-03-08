package graph.algorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import graph.adjacencylist.AdjacencyListGraph;
import graph.adjacencylist.LVertex;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

import unionfind.linkedlist.LinkedUnionFind;

/**
 *
 * @author Administrator
 */
public class Clustering<T extends Vertex> {

    Graph<T> g;

    public Clustering(Graph<T> g) {
        this.g = g;
    }

    public void kruskalForClustering(int n) {
        Collection<T> nodes = g.getVertexes();
        if (n > nodes.size()) {
            return;
        }
        int end = nodes.size() - n;
        int c = 0;
        Collection<Edge<T>> edges = g.getEdges();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>(edges.size(), new Comparator<Edge<T>>() {


            public int compare(Edge<T> o1, Edge<T> o2) {
                return o1.cost < o2.cost ? -1 : (o1.cost == o2.cost ? 0 : 1);
            }
        });
        queue.addAll(edges);
        LinkedUnionFind<T> uf = new LinkedUnionFind<T>(nodes);

        while (!queue.isEmpty() && c < end) {
            Edge<T> e = queue.poll();
            if (!uf.existInSameSet(e.src, e.dest)) {
                //                System.out.println("not in the same set");
                System.out.println(e);
                uf.union(e.src, e.dest);
                c++;
                //                System.out.println(uf.union(e.u, e.v));

            }
            //�ж�·���󣡣�������if (set.add(e.u) || set.add(e.v)) {
        }
        uf.printEachSet();
    }

    ///////////////////////////////////////////////////////////////////////////////////

    private static AdjacencyListGraph topUnDirectedConnectedGraphForTest(AdjacencyListGraph g) {

        LVertex [] nodes = new LVertex[7];
        //        String[] str = {
        //            "d", "s", "g", "a", "o", "w", "m"
        //        };
        for (int i = 0; i < 7; i++) {
            //            nodes[i] = new VNode(str[i]);
            nodes[i] = new LVertex(String.valueOf(i));
            //            nodes[i].setId(i);
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

    //    private static LinkedListGraph topUnDirectedConnectedGraphForTest(LinkedListGraph g) {
    //
    //        LVertex [] nodes = new LVertex[7];
    //        //        String[] str = {
    //        //            "d", "s", "g", "a", "o", "w", "m"
    //        //        };
    //        for (int i = 0; i < 7; i++) {
    //            //            nodes[i] = new VNode(str[i]);
    //            nodes[i] = new LVertex(String.valueOf(i));
    //            //            nodes[i].setId(i);
    //        }
    //        g.initGraph(nodes, false);
    //        g.addEdge(nodes[0], nodes[1], 2);
    //        g.addEdge(nodes[0], nodes[4], 5);
    //        g.addEdge(nodes[0], nodes[6], 1);
    //        g.addEdge(nodes[1], nodes[2], 7);
    //        g.addEdge(nodes[1], nodes[4], 3);
    //        g.addEdge(nodes[1], nodes[5], 7);
    //
    //        g.addEdge(nodes[2], nodes[3], 1);
    //
    //        g.addEdge(nodes[4], nodes[5], 4);
    //        g.addEdge(nodes[4], nodes[6], 6);
    //        g.addEdge(nodes[5], nodes[6], 2);
    //        return g;
    //    }

    public static void main(String [] args) {
        //        Graph g = new AdjacentGraph();
        AdjacencyListGraph g = new AdjacencyListGraph(false);
        topUnDirectedConnectedGraphForTest(g);
        Clustering<LVertex> c = new Clustering<LVertex>(g);
        c.kruskalForClustering(3);
        c.kruskalForClustering(4);

        //        stp.prim();
        //        stp.prim2();
        //        topUnConnectedGraph(g);
    }
}
