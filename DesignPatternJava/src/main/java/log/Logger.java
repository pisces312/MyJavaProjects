package log;


/**
 * һ����־������Ҫʵ�ֵĽӿ�
 * @author hadeslee
 */
public interface Logger {

    public static final int FINE = 0;
    public static final int INFO = 10;
    public static final int DEBUG = 20;
    public static final int WARNING = 30;
    public static final int ERROR = 40;

    /**
     * ʵ�ֵ�log����
     * @param level ����
     * @param info ����
     */
    public void log(int level, String info);

    /**
     * �õ����е���־����
     * @return ����
     */
    public String getLogContent();

    /**
     * �����ǰ����־
     */
    public void clearLog();
}
