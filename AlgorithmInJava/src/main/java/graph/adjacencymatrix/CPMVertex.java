package graph.adjacencymatrix;

import graph.IColoredVertex;

public class CPMVertex extends PMVertex implements IColoredVertex {
	private int color = COLOR_WHITE;

	public CPMVertex(String name) {
		super(name);
	}


	public int getColor() {
		return color;
	}


	public void setColor(int c) {
		color = c;
	}
}
