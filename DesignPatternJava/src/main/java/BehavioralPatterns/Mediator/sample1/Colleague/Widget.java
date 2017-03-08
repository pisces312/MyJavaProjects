/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Mediator.sample1.Colleague;

import BehavioralPatterns.Mediator.sample1.Mediator.DialogDirector;
import javax.swing.JPanel;

/**
 *
 * @author pisces312
 */
public abstract class Widget extends JPanel{
    DialogDirector director;

    public Widget(DialogDirector director) {
        this.director = director;
    }
    public void changed(){
        director.widgetChanged(this);
    }

}
