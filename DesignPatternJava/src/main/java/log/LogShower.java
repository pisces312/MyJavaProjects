package log;


/**
 * һ��������ʾ��־�Ľӿ�,�˽ӿ�����LogManager������
 * @author hadeslee
 */
public interface LogShower {

    /**
     * ��ʾ��־,��LogManager���ô˷�������ʾ��־
     * ��ʾ��־�����ж��ַ���,����������б�����ʾ
     * Ҳ������TextArea����ʾ,��������Canvas����ʾ
     * @param logContent ��־����
     * @param action ���ص�ʱ��Ҫ���Ķ���
     */
    public void showLog(String logContent, BackAction action);

    
}
