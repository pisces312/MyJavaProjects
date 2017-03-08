/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Interpreter.sample1.TerminalExpression;

/**
 * ±‰¡øºÊ÷’Ω·∑˚
 * A variable expression implements BooleanExp
 *  A terminal expression
 */
import BehavioralPatterns.Interpreter.sample1.AbstractExpression.BooleanExp;
import BehavioralPatterns.Interpreter.sample1.Context.Context;

public class VariableExp implements BooleanExp {

    private String name;

    public VariableExp(String _name) {
        name = _name;
    }

    public boolean evaluate(Context c) {
        return c.LookUp(name);
    }

    public BooleanExp copy() {
        return new VariableExp(name);
    }

    public BooleanExp replace(String var, BooleanExp exp) {
        if (var.equals(name)) {
            return exp.copy();
        }
        return new VariableExp(name);
    }
}
