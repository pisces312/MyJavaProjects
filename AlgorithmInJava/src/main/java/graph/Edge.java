package graph;

import java.util.Comparator;

/**
 * T is for Vertex type
 * 
 * 1.Undirected non-weighted edge: cost=1, don't distinguish src and dest
 * 2.Undirected weighted edge: don't distinguish src and dest 3.Directed
 * non-weighted edge: cost=1, don't distinguish src and dest 4.Directed weighted
 * edge
 * 
 * @author nili66china@163.com
 */
public class Edge<T extends IVertex> implements Comparable<T>,
		Comparator<T> {
	/**
	 * 1. Tree edges are edges in the depth-�0�3rst forest G. Edge .u; / is a
	 * tree edge if  was �0�3rst discovered by exploring edge .u; /.
	 * 
	 * 2. Back edges are those edges .u; / connecting a vertex u to an ancestor
	 *  in a depth-�0�3rst tree. We consider self-loops, which may occur in
	 * directed graphs, to be back edges.
	 * 
	 * 3. Forward edges are those nontree edges .u; / connecting a vertex u to
	 * a de- scendant  in a depth-�0�3rst tree.
	 * 
	 * 4. Cross edges are all other edges. They can go between vertices in the
	 * same depth-�0�3rst tree, as long as one vertex is not an ancestor of the
	 * other, or they can go between vertices in different depth-�0�3rst trees.
	 * 
	 * @author pisces312
	 * 
	 */
	public static enum EdgeType {
		GENERAL_EDGE, TREE_EDGE, BACK_EDGE, FORWARD_EDGE, CROSS_EDGE
	};

	public final T src, dest;
	// the cost may be changed for implementation
	public Integer cost;
	public final boolean isDirected;

	public EdgeType type;

	public Edge(T src, T dest, Integer cost, boolean isDirected) {
		this.src = src;
		this.dest = dest;
		this.cost = cost;
		this.isDirected = isDirected;
		this.type = EdgeType.GENERAL_EDGE;
	}

	// public Edge(Edge<T> e){
	// this(e.src,e.dest,e.cost,e.isDirected);
	// }

	// public Edge() {
	// }

	// public void init(T src, T dest, Integer cost, boolean isDirected) {
	// this.src = src;
	// this.dest = dest;
	// this.cost = cost;
	// this.isDirected = isDirected;
	// }

	// public Edge(T src, T dest, int cost) {
	// this.src = src;
	// this.dest = dest;
	// this.cost = cost;
	// }
	/*
	 * for non-weighted graph, the cost is 1
	 */
	public Edge(T u, T v, boolean isDirected) {
		this(u, v, null, isDirected);
	}

	@Override
	public String toString() {
		String op = "->";
		if (!isDirected) {
			op = "<->";
		}
		String typeStr = type.name() + ": ";
		if (type.equals(EdgeType.GENERAL_EDGE)) {
			typeStr = "";
		}
		if (cost == null) {
			return typeStr + src + op + dest;
		}
		return typeStr + src + op + dest + " : " + String.valueOf(cost);
	}

	/**
	 * other is not null
	 * 
	 * @param other
	 * @return
	 */
	private boolean equalsForDirectedWithoutCost(Edge<?> other) {
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (isDirected != other.isDirected)
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		return true;
	}

	/**
	 * other is not null
	 * 
	 * @param other
	 * @return
	 */
	private boolean equalsForUnDirectedWithoutCost(Edge<?> other) {
		if (equalsForDirectedWithoutCost(other)) {
			return true;
		}
		if (dest == null) {
			if (other.src != null)
				return false;
		} else if (!dest.equals(other.src))
			return false;
		if (isDirected != other.isDirected)
			return false;
		if (src == null) {
			if (other.dest != null)
				return false;
		} else if (!src.equals(other.dest))
			return false;
		return true;
	}

	/**
	 * !!!
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int hashCode1 = ((cost == null) ? 0 : cost.hashCode());
		int hashCode2 = ((dest == null) ? 0 : dest.hashCode());
		int hashCode3 = ((src == null) ? 0 : src.hashCode());

		if (!isDirected) {
			// sort
			if (hashCode2 > hashCode3) {
				int t = hashCode2;
				hashCode2 = hashCode3;
				hashCode3 = t;
			}

		}
		// System.out.println(hashCode1+","+hashCode2+","+hashCode3);
		result = prime * result + hashCode1;
		result = prime * result + hashCode2;
		result = prime * result + (isDirected ? 1231 : 1237);
		result = prime * result + hashCode3;
		return result;
	}

	// !!! can not judge if the edge is equal if existed multiple edge
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		//
		if (isDirected) {
			return equalsForDirectedWithoutCost(other);
		}
		boolean flag = equalsForUnDirectedWithoutCost(other);
		// System.out.println(flag);
		return flag;

	}


	public int compareTo(T o) {
		return hashCode() - o.hashCode();
	}


	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}

	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	//
	// Edge<?> other = (Edge<?>)obj;
	//
	// boolean directedEqualFlag = equalsForDirected(other);
	// if (isDirected) {
	// return directedEqualFlag;
	// }
	// if (directedEqualFlag) {
	// return true;
	// }
	// if (src.equals(other.dest) && dest.equals(other.src) && other.cost ==
	// cost) {
	// return true;
	// }
	// return false;
	// }
	//
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + cost;
	// if (isDirected) {
	// result = prime * result + ((src == null) ? 0 : src.hashCode());
	// result = prime * result + ((dest == null) ? 0 : dest.hashCode());
	// return result;
	// }
	// result = prime * result + ((src == null) ? 0 : dest.hashCode());
	// result = prime * result + ((dest == null) ? 0 : src.hashCode());
	// return result;
	//
	// }
}
