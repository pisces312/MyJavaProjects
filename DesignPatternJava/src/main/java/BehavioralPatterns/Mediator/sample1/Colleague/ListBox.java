/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Mediator.sample1.Colleague;

import BehavioralPatterns.Mediator.sample1.Mediator.DialogDirector;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 *
 * @author pisces312
 */
public class ListBox extends Widget implements MouseListener{
    JScrollPane scrollPane;
    JList list;
    DefaultListModel listModel;

    public ListBox(DialogDirector director) {
        super(director);
        setLayout(new BorderLayout());
        listModel=new DefaultListModel();
        listModel.addElement("a");
        listModel.addElement("B");
        listModel.addElement("c");
        list=new JList(new String[]{"a","b","c"});
//        list=new JList(listModel);
        list.addMouseListener(this);
        scrollPane=new JScrollPane(list);
        add(scrollPane);
        
    }
    public void addItem(String str){
        listModel.addElement(str);
        list.setModel(listModel);
        validate();
    }
    public void removeItem(int index){
        listModel.remove(index);
        list.setModel(listModel);
        validate();
    }

//    public void actionPerformed(ActionEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    public void mouseClicked(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent arg0) {
        changed();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }


}
