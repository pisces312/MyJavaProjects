/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.adjacencymatrix;

import graph.Vertex;

/**
 * 
 * id在图的内部递增
 * 
 * @author Administrator
 */
public class MVertex extends Vertex {

	public int id; // 在图中赋值用，可以作为矩阵存储时的下标，从0开始

	public MVertex(String name, Object value) {
		super(name, value);
	}

	public MVertex(String name) {
		super(name);
	}

	public MVertex(Vertex v) {
		this(v.getName(), v.getValue());
	}

	// 在图中给id赋值，表示下标等唯一标识
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return super.toString() + "(" + id + ")";
	}


//	@Override
//	public Object createEmptyVertex() {
//		MVertex node= new MVertex(new String(getName()));
//		node.setId(id);
//		node.setValue(value);
//		return node;
//	}
}
