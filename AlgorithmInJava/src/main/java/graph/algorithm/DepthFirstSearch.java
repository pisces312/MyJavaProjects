package graph.algorithm;

import graph.Edge;
import graph.Edge.EdgeType;
import graph.Graph;
import graph.IColoredVertex;
import graph.IPathVertex;
import graph.ITimeVertex;
import graph.algorithm.GraphAlgorithm.TraverseFunc;
import graph.algorithm.GraphAlgorithm.TreeGenerator;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DepthFirstSearch {
	public static final Logger logger = Logger.getLogger(DepthFirstSearch.class
			.getPackage().getName());
	public static final String CLASS_NAME = DepthFirstSearch.class.getName();

	private DepthFirstSearch() {
	}

	// private final static Comparator<? super ITimeVertex> finishTimeComparator
	// = new Comparator<ITimeVertex>() {
	//
	// @Override
	// public int compare(ITimeVertex o1, ITimeVertex o2) {
	// return o2.getFinishedTime() - o1.getFinishedTime();
	// }
	// };
	// public static <T extends Vertex & ITimeVertex> List<T> topSort(Graph<T>
	// g) {
	// if (g == null || !g.isDirectedGraph()) {
	// return null;
	// }
	// List<T> list = new LinkedList<T>();
	// dfsUsingStackWithTime(g, null);
	// List<T> nodes = g.getVertexes();
	// PriorityQueue<T> queue = new PriorityQueue<T>(nodes.size(),
	// finishTimeComparator);
	// queue.addAll(nodes);
	// while (!queue.isEmpty()) {
	// list.add(queue.poll());
	// }
	// return list;
	// }

	/**
	 * by stack
	 * 
	 * it's use 2 colors, so the visit order is from the tree's root to child
	 * 
	 * 
	 * @param g
	 * @param p
	 */
	public static <T extends IPathVertex & IColoredVertex> void dfsUsingStackBy2Color(
			Graph<T> g, TraverseFunc<T> p) {
		// final String method = "dfsUsingStackBy2Color";
		if (g == null) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
		}
		for (T node : nodes) {
			dfsUsingStackBy2Color(g, node, p);
		}
	}

	// public static <T extends IPathVertex & IColoredVertex> Graph<T>
	// getDFSTree(
	// final Graph<T> g, T s, final Class<? extends Graph> gc,
	// final Class<T> c) {
	// TreeGenerator generator = new TreeGenerator(g, gc, c);
	// dfsUsingStackBy2Color(g, s, generator);
	// return generator.getTree();
	// }

	/**
	 * must initialize
	 * 
	 * @param g
	 * @param node
	 * @param p
	 * @return tree
	 */
	public static <T extends IPathVertex & IColoredVertex> void dfsUsingStackBy2Color(
			Graph<T> g, T node, TraverseFunc<T> p) {
		final String method = "dfsUsingStackBy2Color";
		if (node.getColor() == IColoredVertex.COLOR_WHITE) {
			Stack<T> stack = new Stack<T>();
			stack.push(node);
			logger.logp(Level.FINEST, CLASS_NAME, method, "push a new root "
					+ node);
			while (!stack.isEmpty()) {
				T u = stack.peek();
				u.setColor(IColoredVertex.COLOR_BLACK);
				if (p != null) {
					p.visit(u);
				}
				logger.logp(Level.FINEST, CLASS_NAME, method, "peek " + u);
				Collection<T> adjNodes = g.getAdjVertexes(u);
				boolean hasChange = false;
				for (T v : adjNodes) {
					if (v.getColor() == IColoredVertex.COLOR_WHITE) {
						logger.logp(Level.FINEST, CLASS_NAME, method, "push "
								+ v);
						stack.push(v);
						v.setPrecedence(u);
						hasChange = true;
						break;
					}
				}
				if (!hasChange) {
					u = stack.pop();
					logger.logp(Level.FINEST, CLASS_NAME, method, "pop " + u);
				}
			}
		}
	}

	/**
	 * 
	 * @return forest
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> List<Graph<T>> dfs(
			Graph<T> g) {
		if (g == null || !g.isDirectedGraph()) {
			return null;
		}
		List<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
		}
		List<Graph<T>> forest = new LinkedList<Graph<T>>();
		// traverse in topologically sorted order for G^T
		for (T node : nodes) {
			// System.out.println("dfs from " + node);
			TreeGenerator<T> generator = new TreeGenerator<T>(g);
			DepthFirstSearch.dfsUsingStackBy2Color(g, node, generator);
			Graph<T> tree = generator.getTree();
			if (!tree.isEmpty()) {
				forest.add(tree);
			}
		}
		return forest;
	}

	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> void dfsUsingStackWithTime(
			Graph<T> g, TraverseFunc<T> p) {
		final String method = "dfsUsingStackWithTime";
		if (g == null || !g.isDirectedGraph()) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
			node.setDiscoveredTime(0);
			node.setFinishedTime(0);
		}
		int time = 0;
		for (T node : nodes) {
			if (node.getColor() == IColoredVertex.COLOR_WHITE) {
				Stack<T> stack = new Stack<T>();
				stack.push(node);
				++time;
				node.setDiscoveredTime(time);
				node.setColor(IColoredVertex.COLOR_GRAY);
				logger.logp(Level.FINEST, CLASS_NAME, method,
						"push a new root " + node);
				while (!stack.isEmpty()) {
					T u = stack.peek();
					logger.logp(Level.FINEST, CLASS_NAME, method, "peek " + u);
					boolean isFound = false;
					Collection<T> adjNodes = g.getAdjVertexes(u);
					for (T v : adjNodes) {
						if (v.getColor() == IColoredVertex.COLOR_WHITE) {
							++time;
							v.setDiscoveredTime(time);
							v.setColor(IColoredVertex.COLOR_GRAY);
							v.setPrecedence(u);
							stack.push(v);
							logger.logp(Level.FINEST, CLASS_NAME, method,
									"push " + v);
							// tree edge
							Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
							if (e != null) {
								e.type = Edge.EdgeType.TREE_EDGE;
							}
							logger.logp(Level.FINEST, CLASS_NAME, method,
									Edge.EdgeType.TREE_EDGE.name() + ":" + u
											+ "->" + v);
							isFound = true;
							break;
						} else if (v.getColor() == IColoredVertex.COLOR_GRAY) {
							if (g.isDirectedGraph()) {
								Edge<T> e = g.getExistedEdge(u, v,
										g.getCost(u, v));
								if (e != null) {
									e.type = Edge.EdgeType.BACK_EDGE;
								}
								logger.logp(Level.FINEST, CLASS_NAME, method,
										Edge.EdgeType.BACK_EDGE.name() + ":"
												+ u + "->" + v);
							} else {
								if (v.equals(u)) {
									Edge<T> e = g.getExistedEdge(u, v,
											g.getCost(u, v));
									if (e != null) {
										e.type = Edge.EdgeType.BACK_EDGE;
									}
									logger.logp(Level.FINEST, CLASS_NAME,
											method,
											Edge.EdgeType.BACK_EDGE.name()
													+ ":" + u + "->" + v);
								}
							}

						} else if (v.getColor() == IColoredVertex.COLOR_BLACK) {
							if (g.isDirectedGraph()) {
								/**
								 * show that such an edge (u,v) is a forward
								 * edge if u.d <v.d and a cross edge if u.d
								 * >v.d.
								 */
								if (u.getDiscoveredTime() < v
										.getDiscoveredTime()) {
									// !!when check from bottom to top, ignore
									// the tree edge
									if (!u.equals(v.getPrecedence())) {
										Edge<T> e = g.getExistedEdge(u, v,
												g.getCost(u, v));
										if (e != null) {
											e.type = Edge.EdgeType.FORWARD_EDGE;
										}
										logger.logp(
												Level.FINEST,
												CLASS_NAME,
												method,
												Edge.EdgeType.FORWARD_EDGE
														.name()
														+ ":"
														+ u
														+ "->" + v);
									}
								} else {
									Edge<T> e = g.getExistedEdge(u, v,
											g.getCost(u, v));
									if (e != null) {
										e.type = Edge.EdgeType.CROSS_EDGE;
									}
									logger.logp(Level.FINEST, CLASS_NAME,
											method,
											Edge.EdgeType.CROSS_EDGE.name()
													+ ":" + u + "->" + v);
								}
							} else {
								Edge<T> e = g.getExistedEdge(u, v,
										g.getCost(u, v));
								if (e != null) {
									e.type = Edge.EdgeType.BACK_EDGE;
								}
								logger.logp(Level.FINEST, CLASS_NAME, method,
										Edge.EdgeType.BACK_EDGE.name() + ":"
												+ u + "->" + v);
							}
						}
					}
					if (!isFound) {
						++time;
						stack.pop();
						u.setFinishedTime(time);
						u.setColor(IColoredVertex.COLOR_BLACK);
						logger.logp(Level.FINEST, CLASS_NAME, method, "pop "
								+ u);
						if (p != null) {
							p.visit(u);
						}
					}
				}
			}
		}
	}

	/**
	 * 22.3-3 two colors dfs
	 * 
	 * @param g
	 * @param func
	 */
	public static <T extends IPathVertex & IColoredVertex> void dfsBy2ColorWithoutTime(
			Graph<T> g, TraverseFunc<T> func) {
		final String method = "dfsBy2Color";
		if (g == null) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
		}
		Iterator<T> itr = nodes.iterator();
		while (itr.hasNext()) {
			T u = itr.next();
			if (u.getColor() == IColoredVertex.COLOR_WHITE) {
				dfsVisitBy2ColorWithoutTime(g, u, func);
				itr.remove();
			}
		}
	}

	private static <T extends IPathVertex & IColoredVertex> void dfsVisitBy2ColorWithoutTime(
			Graph<T> g, T u, TraverseFunc<T> p) {
		final String method = "dfsVisitBy2ColorWithoutTime";
		Collection<T> adjNodes = g.getAdjVertexes(u);
		u.setColor(IColoredVertex.COLOR_BLACK);
		if (p != null) {
			p.visit(u);
		}
		for (T v : adjNodes) {
			if (v.getColor() == IColoredVertex.COLOR_WHITE) {
				v.setPrecedence(u);
				dfsVisitBy2ColorWithoutTime(g, v, p);
			}
		}

	}

	/**
	 * don't need to set start node
	 * 
	 * distinguish directed and undirected
	 * 
	 * O(V+E)
	 * 
	 * For undirected, don't have b->w and w->b,all others can be tree or back
	 * type
	 * 
	 * 
	 * @param g
	 * @param func
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> void dfsBy3Color(
			Graph<T> g, TraverseFunc<T> func) {
		final String method = "dfsByColor";
		if (g == null) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
		}
		/**
		 * 1)w->w: tree, back, forward, and cross
		 */
		int[] time = new int[1];
		for (T u : nodes) {
			if (u.getColor() == IColoredVertex.COLOR_WHITE) {
				dfsVisitBy3Color(g, u, time, func);
			} else if (u.getColor() == IColoredVertex.COLOR_BLACK) {
				logger.logp(Level.FINEST, CLASS_NAME, method, u
						+ " has been visited");
			} else {
				System.err.println("can't be gray");
			}
		}
	}

	/**
	 * a. a tree edge or forward edge if and only if u.d <v.d <v.f <u.f ,
	 * 
	 * b. a back edge if and only if v.d < u.d <u.f <v.f ,and
	 * 
	 * c. a cross edge if and only if v.d <v.f <u.d <u.f .
	 * 
	 * @param g
	 * @param u
	 * @param time
	 * @param list
	 * @param p
	 */
	private static <T extends IPathVertex & ITimeVertex & IColoredVertex> void dfsVisitBy3Color(
			Graph<T> g, T u, int[] time, TraverseFunc<T> p) {
		final String method = "dfsVisit";
		++time[0];
		u.setDiscoveredTime(time[0]);
		/**
		 * w->w
		 * 
		 * 2)w->g
		 * 
		 * 3)w->b
		 */
		// can be ignored here
		u.setColor(IColoredVertex.COLOR_GRAY);
		Collection<T> adjNodes = g.getAdjVertexes(u);
		/**
		 * 4)g->w
		 * 
		 * 5)g->g
		 */
		for (T v : adjNodes) {
			if (v.getColor() == IColoredVertex.COLOR_WHITE) {
				v.setPrecedence(u);
				// tree edge
				Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
				if (e != null) {
					e.type = Edge.EdgeType.TREE_EDGE;
				}
				logger.logp(Level.FINEST, CLASS_NAME, method,
						Edge.EdgeType.TREE_EDGE.name() + ":" + u + "->" + v);

				dfsVisitBy3Color(g, v, time, p);
				// Assert.assertTrue(u.getDiscoveredTime() <
				// v.getDiscoveredTime()
				// && v.getDiscoveredTime() < v.getFinishedTime()
				// && v.getFinishedTime() < u.getFinishedTime());
			} else if (v.getColor() == IColoredVertex.COLOR_GRAY) {
				if (g.isDirectedGraph()) {
					Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
					if (e != null) {
						e.type = Edge.EdgeType.BACK_EDGE;
					}
					logger.logp(Level.FINEST, CLASS_NAME, method,
							Edge.EdgeType.BACK_EDGE.name() + ":" + u + "->" + v);
				} else {
					if (v.equals(u)) {
						Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
						if (e != null) {
							e.type = Edge.EdgeType.BACK_EDGE;
						}
						logger.logp(Level.FINEST, CLASS_NAME, method,
								Edge.EdgeType.BACK_EDGE.name() + ":" + u + "->"
										+ v);
					}
				}

			} else if (v.getColor() == IColoredVertex.COLOR_BLACK) {
				if (g.isDirectedGraph()) {
					/**
					 * show that such an edge (u,v) is a forward edge if u.d
					 * <v.d and a cross edge if u.d >v.d.
					 */
					if (u.getDiscoveredTime() < v.getDiscoveredTime()) {
						Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
						if (e != null) {
							e.type = Edge.EdgeType.FORWARD_EDGE;
						}
						logger.logp(Level.FINEST, CLASS_NAME, method,
								Edge.EdgeType.FORWARD_EDGE.name() + ":" + u
										+ "->" + v);
					} else {
						Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
						if (e != null) {
							e.type = Edge.EdgeType.CROSS_EDGE;
						}
						logger.logp(Level.FINEST, CLASS_NAME, method,
								Edge.EdgeType.CROSS_EDGE.name() + ":" + u
										+ "->" + v);
					}
				} else {
					Edge<T> e = g.getExistedEdge(u, v, g.getCost(u, v));
					if (e != null) {
						e.type = Edge.EdgeType.BACK_EDGE;
					}
					logger.logp(Level.FINEST, CLASS_NAME, method,
							Edge.EdgeType.BACK_EDGE.name() + ":" + u + "->" + v);
				}
			}
		}
		/**
		 * 6 g->b
		 */
		u.setColor(IColoredVertex.COLOR_BLACK);
		/**
		 * 7) b->b
		 * 
		 * 8) b->g
		 */
		++time[0];
		u.setFinishedTime(time[0]);
		if (p != null) {
			p.visit(u);
		}

	}

	/**
	 * 
	 * @param g
	 * @return
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> String createParenthesisStructure(
			Graph<T> g) {
		if (g == null || g.isEmpty()) {
			return "";
		}
		dfsBy3Color(g, null);
		List<T> nodes = g.getVertexes();
		Integer[] p = new Integer[nodes.size() * 2 + 1];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.size(); ++i) {
			T node = nodes.get(i);
			p[node.getDiscoveredTime()] = i;
			p[node.getFinishedTime()] = i;
		}
		LinkedList<Integer> stack = new LinkedList<Integer>();
		for (int i = 1; i < p.length; ++i) {
			Integer peekId = stack.peekLast();
			if (p[i] != peekId) {
				sb.append('(');
				sb.append(nodes.get(p[i]).getName());
				stack.addLast(p[i]);
			} else {
				while (!stack.isEmpty()) {
					int s = stack.pollLast();
					sb.append(nodes.get(s).getName());
					sb.append(')');
					++i;
				}
				--i;
			}
		}
		// sb.ap
		return sb.toString();
	}

	public static <T extends IPathVertex & ITimeVertex> String createParenthesisStructure(
			List<T> nodes) {
		if (nodes == null || nodes.isEmpty()) {
			return "";
		}
		boolean[] bracketFlags = new boolean[nodes.size() * 2 + 1];
		int[] nodeIdx = new int[nodes.size() * 2 + 1];
		// boolean leftFlag = false;
		// boolean rightFlag = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.size(); ++i) {
			T node = nodes.get(i);
			bracketFlags[node.getDiscoveredTime()] = false;
			bracketFlags[node.getFinishedTime()] = true;
			nodeIdx[node.getDiscoveredTime()] = i;
			nodeIdx[node.getFinishedTime()] = i;
		}
		for (int i = 1; i < nodeIdx.length; ++i) {
			if (bracketFlags[i]) {
				sb.append(nodes.get(nodeIdx[i]).getName());
				sb.append(')');
			} else {
				sb.append('(');
				sb.append(nodes.get(nodeIdx[i]).getName());
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param g
	 * @return
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> String createParenthesisStructure2(
			Graph<T> g) {
		if (g == null || g.isEmpty()) {
			return "";
		}
		dfsBy3Color(g, null);

		// O(V)
		List<T> nodes = g.getVertexes();
		return createParenthesisStructure(nodes);

	}

	/**
	 * must be directed
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> boolean isSinglyConnected(
			Graph<T> g) {
		if (g == null || !g.isDirectedGraph()) {
			return false;
		}
		//
		Collection<T> nodes = g.getVertexes();
		for (T u : nodes) {
			int[] time = new int[1];
			Collection<T> nodes2 = g.getVertexes();
			for (T node : nodes2) {
				node.setColor(IColoredVertex.COLOR_WHITE);
				node.setPrecedence(null);
				node.setDiscoveredTime(0);
				node.setFinishedTime(0);
			}
			// System.out.println("checking " + u);
			dfsVisitBy3Color(g, u, time, null);
			// g.print();
			Collection<Edge<T>> list = g.getEdges();
			for (Edge<T> e : list) {
				if (e.type.equals(EdgeType.FORWARD_EDGE)
						|| e.type.equals(EdgeType.CROSS_EDGE)) {
					return false;
				}
			}
		}

		return true;
	}

}
