package graph.adjacencylist;

import graph.ICounterVertex;

public class CCPLVertex extends CPLVertex implements ICounterVertex {
	public CCPLVertex(String name) {
		super(name);
	}

	protected int counter;


	public int getCounter() {
		return counter;
	}


	public void setCounter(int c) {
		this.counter = c;

	}


	public int addCounter(int c) {
		counter += c;
		return counter;
	}


	public String toString() {
		return "{" + name + "<" + counter + ">}";
	}
}
