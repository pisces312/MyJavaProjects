package BehavioralPatterns.Interpreter.sample1.NoterminalExpression;

import BehavioralPatterns.Interpreter.sample1.AbstractExpression.BooleanExp;
import BehavioralPatterns.Interpreter.sample1.Context.Context;

/**
 *  A NonterminalExpression
 */
public class OrExp implements BooleanExp {

    private BooleanExp operor1;
    private BooleanExp operor2;

    public OrExp(BooleanExp oper1, BooleanExp oper2) {
        operor1 = oper1;
        operor2 = oper2;
    }

    public boolean evaluate(Context c) {
        return operor1.evaluate(c) ||
                operor2.evaluate(c);
    }

    public BooleanExp copy() {
        return new OrExp(operor1.copy(), operor2.copy());
    }

    public BooleanExp replace(String var, BooleanExp exp) {
        return new OrExp(
                operor1.replace(var, exp),
                operor2.replace(var, exp));
    }
}