/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BehavioralPatterns.Mediator.sample1.Mediator;

import BehavioralPatterns.Mediator.sample1.Colleague.Widget;

/**
 *
 * @author pisces312
 */
public interface DialogDirector {
    public void showDialog();
    public void widgetChanged(Widget widget);
    public void createWidgets();
}
