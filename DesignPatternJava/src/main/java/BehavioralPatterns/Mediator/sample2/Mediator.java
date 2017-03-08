package BehavioralPatterns.Mediator.sample2;/**
 *  An abstract Mediator
 */
public interface Mediator  {
    public void Register(Colleague c, String type);
    /**
     * 也可以不通过被改变的对象本身类型来判断而直接用常量或字符串判断
     * @param type
     */
    public void Changed(String type);
}