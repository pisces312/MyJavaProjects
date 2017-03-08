/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Interpreter.sample1.Context;

import java.util.Hashtable;

/**
 * A Context to record variable value
 * @author pisces312
 */
public class Context {

    private Hashtable context = new Hashtable();

    /**
     * ����һ��������������������ֵ
     * @param name
     * @param val
     */
    public void Assign(String name, boolean val) {
        context.put(name, new Boolean(val));
    }

    /**
     * ��ѯһ�������Ĳ���ֵ
     * @param name
     * @return
     */
    public boolean LookUp(String name) {
        return ((Boolean) context.get(name)).booleanValue();
    }

    public Context() {
    }
}
