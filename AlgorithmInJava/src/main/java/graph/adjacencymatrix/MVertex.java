/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.adjacencymatrix;

import graph.Vertex;

/**
 * 
 * id��ͼ���ڲ�����
 * 
 * @author Administrator
 */
public class MVertex extends Vertex {

	public int id; // ��ͼ�и�ֵ�ã�������Ϊ����洢ʱ���±꣬��0��ʼ

	public MVertex(String name, Object value) {
		super(name, value);
	}

	public MVertex(String name) {
		super(name);
	}

	public MVertex(Vertex v) {
		this(v.getName(), v.getValue());
	}

	// ��ͼ�и�id��ֵ����ʾ�±��Ψһ��ʶ
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
