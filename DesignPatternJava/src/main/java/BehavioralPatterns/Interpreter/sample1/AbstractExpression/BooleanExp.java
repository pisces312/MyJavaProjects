/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Interpreter.sample1.AbstractExpression;

import BehavioralPatterns.Interpreter.sample1.Context.Context;

/**
 * ���ʽ�ĳ���ӿ�
 * ������Ϊ�ս���ͷ��ս��������
 * ������ݲ�������Ĺ��򣬷�3�����㣬2������
 * The interface of our BooleanExp Interpreter
 *  BooleanExp definition is:
 *      BooleanExp ::= VariableExp | Constant | OrExp | AndExp
 *                               | NotExp | '(' BooleanExp ')'
 *      AndExp ::= BooleanExp 'and' BooleanExp
 *      OrExp ::= BooleanExp 'or' BooleanExp
 *      NotExp ::= BooleanExp 'not' BooleanExp
 *      Constant ::= 'true' | 'false'
 *      VariableExp ::= 'A' | 'B' | ... | 'Z'
 */
public interface BooleanExp {

    /**
     * ������
     * @param c
     * @return
     */
    public abstract boolean evaluate(Context c);

    public abstract BooleanExp replace(String var, BooleanExp exp);

    public abstract BooleanExp copy();
}