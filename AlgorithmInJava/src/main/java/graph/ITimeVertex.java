package graph;

public interface ITimeVertex extends IVertex {
	public void setDiscoveredTime(int t);

	public int getDiscoveredTime();

	public void setFinishedTime(int t);

	public int getFinishedTime();
	
	public boolean isTimeValid();
}
