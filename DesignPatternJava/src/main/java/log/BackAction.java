package log;


/**
 * 内部的一个静态接口,实现此接口以供LogShower在
 * 点击了返回之后,要做的事情
 */
public interface BackAction {

    /**
     * 点击返回之后要做的事情
     */
    public void back();
}