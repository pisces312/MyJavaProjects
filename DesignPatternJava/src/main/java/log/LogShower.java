package log;


/**
 * 一个用于显示日志的接口,此接口用于LogManager来调用
 * @author hadeslee
 */
public interface LogShower {

    /**
     * 显示日志,由LogManager调用此方法来显示日志
     * 显示日志可以有多种方法,比如可以用列表来显示
     * 也可以用TextArea来显示,还可以用Canvas来显示
     * @param logContent 日志内容
     * @param action 返回的时候要做的动作
     */
    public void showLog(String logContent, BackAction action);

    
}
