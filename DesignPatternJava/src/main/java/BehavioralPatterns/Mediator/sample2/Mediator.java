package BehavioralPatterns.Mediator.sample2;/**
 *  An abstract Mediator
 */
public interface Mediator  {
    public void Register(Colleague c, String type);
    /**
     * Ҳ���Բ�ͨ�����ı�Ķ������������ж϶�ֱ���ó������ַ����ж�
     * @param type
     */
    public void Changed(String type);
}