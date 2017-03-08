package BehavioralPatterns.Interpreter.sample1.NoterminalExpression;

import BehavioralPatterns.Interpreter.sample1.AbstractExpression.BooleanExp;
import BehavioralPatterns.Interpreter.sample1.Context.Context;

/**
 *  A NonterminalExpression
 */
public class AndExp implements BooleanExp {
    private BooleanExp operand1;
    private BooleanExp operand2;
    
    public AndExp(BooleanExp oper1, BooleanExp oper2) {
        operand1 = oper1;
        operand2 = oper2;
    }

    public boolean evaluate(Context c) {
        return operand1.evaluate(c) &&
                    operand2.evaluate(c);
    }

    public BooleanExp copy() {
        return new AndExp(operand1.copy(), operand2.copy());
    }

    public BooleanExp replace(String var, BooleanExp exp) {
        return new AndExp(
                    operand1.replace(var, exp),
                    operand2.replace(var, exp)

                    );
    }
}