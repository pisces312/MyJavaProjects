package StructuralPatterns.bridge.abstraction;

import StructuralPatterns.bridge.ConcreteImplementor.TextImpMac;
import StructuralPatterns.bridge.ConcreteImplementor.TextImpLinux;
import StructuralPatterns.bridge.implementor.TextImp;

/**
 * 抽象类
 * The Abstract of Text
 */
public abstract class Text {

    public abstract void DrawText(String text);

    /**
     *实现接口的引用
     * @param type
     * @return
     */
    protected TextImp GetTextImp(String type) {
        if (type.equals("Mac")) {
            return new TextImpMac();
        } else if (type.equals("Linux")) {
            return new TextImpLinux();
        } else {
            return new TextImpMac();
        }
    }
}