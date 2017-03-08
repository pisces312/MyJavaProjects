package graph.adjacencylist;

import graph.IPathVertex;

public class PLVertex extends LVertex implements IPathVertex {
	public PLVertex(String name) {
		super(name);
	}

	int d;
	IPathVertex precedence;


	public int getDistance() {
		return d;
	}


	public void setDistance(int d) {
		this.d = d;
	}


	public IPathVertex getPrecedence() {
		return precedence;
	}


	public void setPrecedence(IPathVertex vertex) {
		this.precedence = vertex;
	}


	public String toString() {
		if (precedence != null)
			return "<" + super.toString() + ", d:" + d + ", pre:"
					+ precedence.getName() + ">";
		return "<" + super.toString() + ", d:" + d + ">";
	}

	// public List<? extends SLVertex> getTravseOrder() {
	// LinkedList<
	// return null;
	// }

}
