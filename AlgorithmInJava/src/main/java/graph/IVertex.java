package graph;

import java.util.Comparator;

public interface IVertex extends Comparable<IVertex>, Comparator<IVertex>,
		Cloneable {
	public String getName();
	public IVertex createEmptyVertex();

	/**
	 * will only create a vertex with same name
	 * 
	 * @return
	 */
	// public Object createEmptyVertex();
	// public <T> T copyVertex();
}
