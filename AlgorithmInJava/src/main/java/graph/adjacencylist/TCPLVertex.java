package graph.adjacencylist;

import graph.ITimeVertex;

public class TCPLVertex extends CPLVertex implements ITimeVertex {

	protected int dTime;
	protected int fTime;

	public TCPLVertex(String name) {
		super(name);
	}


	public void setDiscoveredTime(int t) {
		dTime = t;
	}


	public int getDiscoveredTime() {
		return dTime;
	}


	public void setFinishedTime(int t) {
		fTime = t;

	}


	public int getFinishedTime() {
		return fTime;
	}


	public String toString() {
		if (precedence != null)
			return "{" + name + " <d" + dTime + ",f" + fTime + ","
					+ precedence.getName() + ">}";
		return "{" + name + " <d" + dTime + ",f" + fTime + ">}";
	}


	public boolean isTimeValid() {
		return dTime < fTime;
	}

	/**
	 * Corollary 22.8 (Nesting of descendants�� intervals) Vertex v is a proper
	 * descendant of vertex u in the depth-first forest for a (directed or
	 * undirected) graph G if and only if u.d <v.d <v.f <u.f .
	 * 
	 * @param u
	 * @return
	 */
	public <T extends ITimeVertex> boolean isDescendantOf(T u) {
		if (u == null || equals(u)) {
			return false;
		}
		if (!isTimeValid() || !u.isTimeValid()) {
			return false;
		}
		return u.getDiscoveredTime() < dTime && fTime < u.getFinishedTime();
	}

}
