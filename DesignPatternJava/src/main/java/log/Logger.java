package log;


/**
 * 一个日志生成器要实现的接口
 * @author hadeslee
 */
public interface Logger {

    public static final int FINE = 0;
    public static final int INFO = 10;
    public static final int DEBUG = 20;
    public static final int WARNING = 30;
    public static final int ERROR = 40;

    /**
     * 实现的log方法
     * @param level 级别
     * @param info 内容
     */
    public void log(int level, String info);

    /**
     * 得到所有的日志内容
     * @return 内容
     */
    public String getLogContent();

    /**
     * 清除当前的日志
     */
    public void clearLog();
}
