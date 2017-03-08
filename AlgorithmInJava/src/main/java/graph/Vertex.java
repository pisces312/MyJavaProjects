package graph;

import graph.adjacencylist.LVertex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the unique id is name
 * 
 * @author pisces312
 * 
 */
public abstract class Vertex implements IVertex {

	protected String name;
	protected Object value;

	public Vertex(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public Vertex(String name) {
		this(name, null);
	}


	public IVertex createEmptyVertex() {
//		System.out.println(getClass());
		Class<? extends IVertex> gc = getClass();
		try {
			Constructor<? extends IVertex> ctor = gc
					.getConstructor(String.class);
			return ctor.newInstance(getName());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		// if (value == null) {
		// if (other.value != null)
		// return false;
		// } else if (!value.equals(other.value))
		// return false;
		return true;
	}


	public int compareTo(IVertex o) {
		int d = name.length() - o.getName().length();
		if (d == 0) {
			return this.name.compareTo(o.getName());
		}
		return d;
	}


	public int compare(IVertex o1, IVertex o2) {
		return o1.compareTo(o2);
	}

	public static void main(String[] args) {
		LVertex v1 = new LVertex("1");
		LVertex v2 = new LVertex("2");
		LVertex v3 = new LVertex("3");
		LVertex v4 = new LVertex(" ");
		LVertex v5 = new LVertex("a");
		LVertex v6 = new LVertex("ab");
		LVertex v7 = new LVertex("0");
		List<LVertex> list = new ArrayList<LVertex>();
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		list.add(v5);
		list.add(v6);
		list.add(v7);
		Collections.sort(list);
		System.out.println(list);
	}
}
