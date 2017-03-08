package graph;


public interface IPathVertex extends IVertex {

	public static final int UNREACHABLE = Integer.MAX_VALUE;

	public int getDistance();

	public void setDistance(int d);

	public IPathVertex getPrecedence();

	// public <T extends IShortestPathVertex> T getPrecedence();
	public void setPrecedence(IPathVertex vertex);
	// public <T extends IShortestPathVertex> void setPrecedence(T vertex);

}
