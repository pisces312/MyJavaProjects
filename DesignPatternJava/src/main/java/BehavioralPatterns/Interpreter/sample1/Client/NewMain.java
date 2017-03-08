/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Interpreter.sample1.Client;

import BehavioralPatterns.Interpreter.sample1.AbstractExpression.BooleanExp;
import BehavioralPatterns.Interpreter.sample1.Context.Context;
import BehavioralPatterns.Interpreter.sample1.NoterminalExpression.AndExp;
import BehavioralPatterns.Interpreter.sample1.NoterminalExpression.NotExp;
import BehavioralPatterns.Interpreter.sample1.TerminalExpression.VariableExp;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Test :
        //         (true and x) and (y and (not x))
        Context context = new Context();
//�ս��������Ϊtrue�ͺ�Ϊfalse���Զ���ı���������
        VariableExp x = new VariableExp("X");
        VariableExp y = new VariableExp("Y");
        VariableExp bTure = new VariableExp("true");
        VariableExp bFalse = new VariableExp("false");
//�������ĵ����ݸ�ֵ
        context.Assign("true", true);
        context.Assign("false", false);
        context.Assign("X", false);
        context.Assign("Y", true);
//����һ���������ʽ
        BooleanExp expression = new AndExp(
                new AndExp(bTure, x),
                new AndExp(y, new NotExp(x)));
        boolean result = expression.evaluate(context);
        System.out.println("The result is:" + result);
    }
}
