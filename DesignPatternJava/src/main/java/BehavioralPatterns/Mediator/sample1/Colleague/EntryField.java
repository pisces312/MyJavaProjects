/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Mediator.sample1.Colleague;

import BehavioralPatterns.Mediator.sample1.Mediator.DialogDirector;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author pisces312
 */
public class EntryField extends Widget implements ActionListener {

    JTextField textField;

    public EntryField(DialogDirector director) {
        super(director);
        setLayout(new BorderLayout());
        textField = new JTextField(20);
        textField.addActionListener(this);

        add(textField);
    }
    public void setText(String text){
        textField.setText(text);
    }

    public void actionPerformed(ActionEvent arg0) {
        changed();
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
