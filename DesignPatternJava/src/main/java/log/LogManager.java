package log;

/**
 * 日志管理器,所有的日志通过此日志管理器
 * 进行统一的调用
 * 此类相关的两个接口都有相应的默认实现,当然
 * 也可以替换实现
 * @author hadeslee
 */
public final class LogManager {

    private static Logger log = new LoggerImpl();//具体的日志实现类
    private static LogShower shower = new LogShowerImpl();//日志显示者

    private LogManager() {
    }

    /**
     * 安装自己实现的日志记录器
     * @param log 新的日志记录器
     */
    public static void install(Logger log) {
        LogManager.log = log;
    }

    /**
     * 安装自己实现的日志显示器
     * @param shower 新的日志显示器
     */
    public static void install(LogShower shower) {
        LogManager.shower = shower;
    }

    /**
     * 记录INFO级别的日志
     * @param info 日志内容
     */
    public static void info(String info) {
        log.log(Logger.INFO, info);
    }

    /**
     * 记录DEBUG级别的日志
     * @param info 日志内容
     */
    public static void debug(String info) {
        log.log(Logger.DEBUG, info);
    }

    /**
     * 记录ERROR级别的日志
     * @param info 日志内容
     */
    public static void error(String info) {
        log.log(Logger.ERROR, info);
    }

    /**
     * 记录WARNING级别的日志
     * @param info 日志内容
     */
    public static void warning(String info) {
        log.log(Logger.WARNING, info);
    }

    /**
     * 记录FINE级别的日志
     * @param info 日志的内容
     */
    public static void fine(String info) {
        log.log(Logger.FINE, info);
    }

    /**
     * 显示当前日志管理器的日志
     * @param back 要返回的时候,做的动作
     */
    public static void showLog(BackAction back) {
        shower.showLog(log.getLogContent(), back);
    }

    /**
     * 清除当前日志管理器的日志
     */
    public static void clearLog() {
        log.clearLog();
    }

    static class LogShowerImpl implements LogShower {

        public void showLog(String logContent, final BackAction action) {
            System.out.println(logContent);
        }
    }

    static class LoggerImpl implements Logger {

        private StringBuffer sb;

        public LoggerImpl() {
            sb = new StringBuffer(1024);
        }

        public void log(int level, String info) {
            sb.append(getPrefix()).append("\n").
                    append(getLevelName(level)).append(":").
                    append(info).append("\n");
            System.out.println(info);
        }

        private String getPrefix() {
//            try {
//                Thread.sleep(1200);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//            Calendar calendar = Calendar.getInstance();
//            String date = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
            //
//            String date=System.currentTimeMillis();
//            System.out.println(calendar.get(Calendar.HOUR);
//calendar.set(int year, int month, int date);
//java.util.Date date = calendar.getTime();
            return "[" + Thread.currentThread() + "-" + System.currentTimeMillis() + "]";
//            return "[" + Thread.currentThread() + "-" + new Date().toString() + "]";
//            return "[" + Thread.currentThread() + "-" + Util.getCurrentTime() + "]";
        }

        private String getLevelName(int level) {
            switch (level) {
                case FINE:
                    return "FINE";
                case INFO:
                    return "INFO";
                case DEBUG:
                    return "DEBUG";
                case WARNING:
                    return "WARNING";
                case ERROR:
                    return "ERROR";
                default:
                    return "UNKNOWN";
            }
        }

        public String getLogContent() {
            return sb.toString();
        }

        public void clearLog() {
            sb.delete(0, sb.length());
        }
    }
}
