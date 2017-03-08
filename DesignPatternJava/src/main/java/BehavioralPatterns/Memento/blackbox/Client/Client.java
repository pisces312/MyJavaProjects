/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.blackbox.Client;

import BehavioralPatterns.Memento.blackbox.Memento.*;
import BehavioralPatterns.Memento.blackbox.Caretaker.Caretaker;
import BehavioralPatterns.Memento.blackbox.Originator.Originator;

/**
 *Client必须和NarrowMemento在同一个包中
 * 双接口的实现，宽窄接口（黑箱)
如何实现宽窄接口呢，内部类也许是个好方法。我们把备忘录类设计"成发起人"的内部类，但这样还有的问题是同一
package中的其它类也能访问到，为了解决这个问题，我们可以把"备忘录"的方法设计成私有的方法，这样就
可以保正封装，又保正发起人能访问到。
 * @author pisces312
 */
public class Client {

    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();

    public static void main(String[] args) {
        //use wide interface
        o.setState("On");
        c.saveMemento(o.createMemento());
        o.setState("Off");
        o.restoreMemento(c.retrieveMemento());
        //use narrow interface
        NarrowMemento memento = o.getNarrowMemento();
        memento.narrowMethod();

    }
}
