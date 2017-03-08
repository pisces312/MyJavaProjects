/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Mediator.sample1.ConcreteMediator;

import BehavioralPatterns.Mediator.sample1.Colleague.EntryField;
import BehavioralPatterns.Mediator.sample1.Colleague.ListBox;
import BehavioralPatterns.Mediator.sample1.Colleague.Widget;
import BehavioralPatterns.Mediator.sample1.Mediator.DialogDirector;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author pisces312
 */
public class FontDialogDirector extends JFrame implements DialogDirector {

    ListBox listBox;
    EntryField entryField;

    public void showDialog() {
        setVisible(true);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void widgetChanged(Widget widget) {
        if (widget == listBox) {
            System.out.println("ListBox has changed!");
        } else if (widget == entryField) {
            System.out.println("EntryField has changed!");
        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void createWidgets() {
        setTitle("Mediator");
        listBox = new ListBox(this);
        listBox.addItem("A");
        listBox.addItem("B");
        listBox.addItem("C");
        entryField = new EntryField(this);
        entryField.setText("abcefg");
//        setSize(320, 240);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(listBox);
        getContentPane().add(entryField);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        DialogDirector director=new FontDialogDirector();
        director.createWidgets();
        director.showDialog();
    }
}
