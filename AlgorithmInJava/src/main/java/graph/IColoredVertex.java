package graph;

public interface IColoredVertex extends IVertex{
	public static final int COLOR_WHITE = 0;
	public static final int COLOR_GRAY = 1;
	public static final int COLOR_BLACK = 2;

	/**
	 * color is for visited
	 * 
	 * @return
	 */
	public int getColor();

	public void setColor(int c);
}
