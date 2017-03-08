package StructuralPatterns.Decorator.sample2;
/**
 *  A simple test
 */
public class Test1  {
    public static void main(String[] args) {
        Component myComponent = new ConcreteComponent();
        myComponent.PrintString("component");
        Decorator myDecorator = new ConcreteDecoratorA(myComponent);
        myDecorator.PrintString("decorator");
    }
}