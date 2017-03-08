package StructuralPatterns.bridge.RefinedAbstraction;

import StructuralPatterns.bridge.abstraction.Text;
import StructuralPatterns.bridge.implementor.TextImp;

/**
 *  The RefinedAbstraction
 */

public class TextBold extends Text {

    private TextImp imp;

    public TextBold(String type) {
        //ͨ������ģʽ��ô��������ʵ��
        imp = GetTextImp(type);
    }

    public void DrawText(String text) {
        System.out.println(text);
        System.out.println("The text is bold text!");
        imp.DrawTextImp();
    }
}