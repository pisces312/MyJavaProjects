package log;

/**
 * ��־������,���е���־ͨ������־������
 * ����ͳһ�ĵ���
 * ������ص������ӿڶ�����Ӧ��Ĭ��ʵ��,��Ȼ
 * Ҳ�����滻ʵ��
 * @author hadeslee
 */
public final class LogManager {

    private static Logger log = new LoggerImpl();//�������־ʵ����
    private static LogShower shower = new LogShowerImpl();//��־��ʾ��

    private LogManager() {
    }

    /**
     * ��װ�Լ�ʵ�ֵ���־��¼��
     * @param log �µ���־��¼��
     */
    public static void install(Logger log) {
        LogManager.log = log;
    }

    /**
     * ��װ�Լ�ʵ�ֵ���־��ʾ��
     * @param shower �µ���־��ʾ��
     */
    public static void install(LogShower shower) {
        LogManager.shower = shower;
    }

    /**
     * ��¼INFO�������־
     * @param info ��־����
     */
    public static void info(String info) {
        log.log(Logger.INFO, info);
    }

    /**
     * ��¼DEBUG�������־
     * @param info ��־����
     */
    public static void debug(String info) {
        log.log(Logger.DEBUG, info);
    }

    /**
     * ��¼ERROR�������־
     * @param info ��־����
     */
    public static void error(String info) {
        log.log(Logger.ERROR, info);
    }

    /**
     * ��¼WARNING�������־
     * @param info ��־����
     */
    public static void warning(String info) {
        log.log(Logger.WARNING, info);
    }

    /**
     * ��¼FINE�������־
     * @param info ��־������
     */
    public static void fine(String info) {
        log.log(Logger.FINE, info);
    }

    /**
     * ��ʾ��ǰ��־����������־
     * @param back Ҫ���ص�ʱ��,���Ķ���
     */
    public static void showLog(BackAction back) {
        shower.showLog(log.getLogContent(), back);
    }

    /**
     * �����ǰ��־����������־
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
