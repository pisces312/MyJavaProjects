package StructuralPatterns.Decorator.sample2;
/**
 *  The Concrete Decorator
 */


public class ConcreteDecoratorA extends Decorator {
    public ConcreteDecoratorA(Component c) {
        super(c);
    }
    @Override
    public void PrintString(String s) {
        super.PrintString(s);
        PrintStringLen(s);
    }
    public void PrintStringLen(String s) {
        System.out.println("The length of string is:" + s.length());
    }
}