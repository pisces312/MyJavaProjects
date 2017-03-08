package StructuralPatterns.bridge.ConcreteImplementor;

import StructuralPatterns.bridge.implementor.TextImp;

/**
 *  The ConcreteImplementor
 */
public class TextImpLinux implements TextImp {

    public TextImpLinux() {
    }

    public void DrawTextImp() {
        System.out.println("The text has a Linux style !");
    }
}