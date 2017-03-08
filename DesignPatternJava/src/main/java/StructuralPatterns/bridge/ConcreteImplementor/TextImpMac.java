package StructuralPatterns.bridge.ConcreteImplementor;

import StructuralPatterns.bridge.implementor.TextImp;

/**
 *  The ConcreteImplementor
 */
public class TextImpMac implements TextImp {

    public TextImpMac() {
    }

    public void DrawTextImp() {
        System.out.println("The text has a Mac style !");
    }
}