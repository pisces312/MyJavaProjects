/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Interpreter.sample1.AbstractExpression;

import BehavioralPatterns.Interpreter.sample1.Context.Context;

/**
 * 表达式的抽象接口
 * 其子类为终结符和非终结符两大类
 * 这里根据布尔运算的规则，分3种运算，2种类型
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
     * 计算结果
     * @param c
     * @return
     */
    public abstract boolean evaluate(Context c);

    public abstract BooleanExp replace(String var, BooleanExp exp);

    public abstract BooleanExp copy();
}