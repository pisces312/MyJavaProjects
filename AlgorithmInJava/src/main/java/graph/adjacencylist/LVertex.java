/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.adjacencylist;

import graph.Edge;
import graph.IVertex;
import graph.Vertex;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Administrator
 */
public class LVertex extends Vertex {
	public static final Logger logger = Logger.getLogger(LVertex.class
			.getPackage().getName());
	public static final String CLASS_NAME = LVertex.class.getName();
	public int index;
	private LinkedList<Edge> outAdjacencyList = new LinkedList<Edge>();

	public LVertex(String name) {
		super(name);
	}

	public void clear() {
		outAdjacencyList.clear();
		// don't set to null
	}

	public int getEdgeNum() {
		return outAdjacencyList.size();
	}

	public LinkedList<Edge> getOutEdges() {
		return outAdjacencyList;
	}

	public void setOutEdgeNodes(LinkedList<Edge> outEdgeNodes) {
		if (outEdgeNodes == null) {
			this.outAdjacencyList = null;
			this.outAdjacencyList = new LinkedList<Edge>();
		} else {
			this.outAdjacencyList = outEdgeNodes;
		}
	}
	public List<Edge> getExistedEdge(Edge edge, boolean isAllowMultiEdge) {
		if (edge == null) {
			return null;
		}
		List<Edge> list = new LinkedList<Edge>();
		for (Edge e : outAdjacencyList) {
			if (e.equals(edge)) {
				list.add(e);
				if (!isAllowMultiEdge) {
					return list;
				}
			}
		}
		return null;
	}

	public List<Edge> getExistedEdge(LVertex node, boolean isAllowMultiEdge) {
		if (node == null) {
			return null;
		}
		List<Edge> list = new LinkedList<Edge>();
		for (Edge<? extends LVertex> e : outAdjacencyList) {
			if (isTargetVertex(e, node)) {
				list.add(e);
				if (!isAllowMultiEdge) {
					return list;
				}
			}
		}
		return null;
	}

	public IVertex getTargetVertex(Edge e) {
		if (e == null) {
			return null;
		}
		if (e.isDirected) {
			return e.dest;
		}
		if (e.dest.equals(this)) {
			return e.src;
		}
		if (e.src.equals(this)) {
			return e.dest;
		}
		return null;

	}

	public boolean isTargetVertex(Edge<? extends LVertex> e, LVertex tgtNode) {
		if (e.isDirected) {
			return e.dest.equals(tgtNode);
		}
		if (e.src.equals(tgtNode) && e.dest.equals(this)) {
			return true;
		}
		return e.dest.equals(tgtNode) && e.src.equals(this);
	}

	public boolean isConnectedTo(LVertex node) {
		List<Edge> list = getExistedEdge(node, false);
		return list != null && !list.isEmpty();
	}

	/**
	 * TODO not support multiple graph yet
	 * 
	 * @param node
	 * @return
	 */
	public Integer getCost(LVertex node) {
		List<Edge> list = getExistedEdge(node, false);
		if (list != null && !list.isEmpty()) {
			return list.get(0).cost;
		}
		return null;
	}

	public boolean removeAllEdgeNode(LVertex node, Integer cost,
			boolean isAllowMultipleEdge) {
		return removeEdgeNode(node, cost, true, isAllowMultipleEdge, true);
	}

	public boolean removeAllEdgeNode(LVertex node, boolean isAllowMultipleEdge) {
		return removeEdgeNode(node, null, false, isAllowMultipleEdge, true);
	}

	public boolean removeEdgeNode(LVertex endVertex, Integer cost,
			boolean isConsiderCost, boolean isAllowMultipleEdge,
			boolean isRemoveAll) {
		final String method = "removeEdgeNode";
		logger.entering(CLASS_NAME, method);
		if (!isAllowMultipleEdge) {
			if (!isRemoveAll) {
				logger.logp(Level.WARNING, CLASS_NAME, method,
						"if not allow multiple edge, it musts remove all ");
			}
			isRemoveAll = true;
		} else {
			if (!isConsiderCost) {
				logger.logp(Level.WARNING, CLASS_NAME, method,
						"if allow multiple edge, must consider cost");
			}
			isConsiderCost = true;
		}
//		logger.logp(Level.FINEST, CLASS_NAME, method,
//				"begin to check");
		boolean flag = false;
		Iterator<Edge> itr = outAdjacencyList.iterator();
//		System.out.println(outAdjacencyList.size());
//		System.out.println(itr.hasNext());
		while (itr.hasNext()) {
			Edge e = itr.next();
//			logger.logp(Level.FINEST, CLASS_NAME, method,
//					"checking "+e);
			if (!isTargetVertex(e, endVertex)) {
				continue;
			}
			// //////////////////////////////////////////////
			if (isConsiderCost) {
				if (e.cost != cost) {
					continue;
				}
			}
			itr.remove();
//			System.out.println("removed");
			flag = true;
			if (isAllowMultipleEdge) {
				if (!isRemoveAll) {
					return true;
				}
			} else {
				return true;
			}
		}
		logger.exiting(CLASS_NAME, method);
		return flag;
	}

	// public boolean addEdgeNode(LVertex node, Integer cost) {
	// return addEdgeNode(node, cost, false);
	// }

	/**
	 * the Edge must be copied
	 * 
	 * @param newCopiedEdge
	 * @param isAllowMultipleEdge
	 * @return
	 */
	public Edge addEdgeNode(Edge newCopiedEdge, boolean isAllowMultipleEdge) {
		final String method = "addEdgeNode";
		if (newCopiedEdge == null) {
			return null;
		}
		// this code is to avoid duplicated edge
		if (!isAllowMultipleEdge) {
			List<Edge> e = getExistedEdge(newCopiedEdge, false);
			if (e != null && !e.isEmpty()) {
				// has duplicate
				logger.logp(Level.FINER, CLASS_NAME, method, "duplicate edge "
						+ e.get(0));
				return e.get(0);
			}
		}
		outAdjacencyList.add(newCopiedEdge);
		return newCopiedEdge;
	}

	public void print() {
		System.out.print(this + " [");
		for (Edge<?> e : outAdjacencyList) {
			System.out.print(" ->");
			if (!e.isDirected) {
				if (e.src.equals(this)) {
					System.out.print(e.dest);
				} else {
					System.out.print(e.src);
				}
			} else {
				System.out.print(e.dest);
			}
		}
		System.out.println(" ]");
	}
}
