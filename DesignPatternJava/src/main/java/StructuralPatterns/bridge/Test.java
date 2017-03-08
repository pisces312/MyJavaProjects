package StructuralPatterns.bridge;

import StructuralPatterns.bridge.RefinedAbstraction.TextItalic;
import StructuralPatterns.bridge.RefinedAbstraction.TextBold;
import StructuralPatterns.bridge.abstraction.Text;

/**
 * 桥接模式，适用于有多个实现的情况
 * A test client
 */
public class Test {

    public Test() {
    }

    public static void main(String[] args) {
        Text myText = new TextBold("Mac");
        myText.DrawText("=== A test String ===");

        myText = new TextBold("Linux");
        myText.DrawText("=== A test String ===");

        System.out.println("------------------------------------------");

        myText = new TextItalic("Mac");
        myText.DrawText("=== A test String ===");

        myText = new TextItalic("Linux");
        myText.DrawText("=== A test String ===");
    }
}