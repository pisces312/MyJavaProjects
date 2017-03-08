package graph.adjacencylist;

import graph.IColoredVertex;

public class CPLVertex extends PLVertex implements IColoredVertex {

	private int color = COLOR_WHITE;

	public CPLVertex(String name) {
		super(name);
	}


	public int getColor() {
		return color;
	}


	public void setColor(int c) {
		color = c;
	}

}
