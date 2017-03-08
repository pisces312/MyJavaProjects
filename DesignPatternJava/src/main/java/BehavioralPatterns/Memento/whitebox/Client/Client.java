/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Memento.whitebox.Client;

import BehavioralPatterns.Memento.whitebox.Caretaker.Caretaker;
import BehavioralPatterns.Memento.whitebox.Originator.Originator;

/**
 *双重接口的实现,备忘录模式
一、定义：备忘录(memento)模式又叫快照(snapshot)模式或者token模式，主要功能：
备忘录模式是用一个对象来存储另外一个对象的内部状态的快照，实现备忘录模式的关键点是在不破坏封装的
情况下，将一个对象的状态捕捉住，并外部化，存储起来，从而可以在合适的时候，把这个对象还原。
说明：备忘录模式适模式中比较好理解的一个，这里就不举例子，但是备忘录模式是模式中实现比较难，或者说
实现比较巧的，这里主要说说。
二、备忘录模式的实现
1,备忘录模式中的角色
发起人：创建含有内部状态的备忘录对象，并使用备忘录对象存储状态
负责人：负责人保存备忘录对象，但不检查备忘录对象的内容
备忘录：备忘录对象将发起人对象的内部状态存起来，并保正其内容不被发起人对象之外的对象像读取
注意：在备忘录的角色中，定义了他必须对不同的人提供不同的接口，对发起人提供宽接口，对其它任何人提供窄
接口。也许你说我都提供宽接口得了。对这也是备忘录的一种实现，叫做白箱备忘录，不过这种方法的封装没有设计
好，安全性不够好。

 *
 * 下面是白箱备忘录的实现：
 * 白箱的优点：实现简单
白箱的缺点：上边说了，破坏了封装，安全性有些问题。
说明：这里白箱的实现只保存了一个状态，其实是可以保存多个状态的。
 * @author pisces312
 */
public class Client {

    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();

    public static void main(String[] args) {
        o.setState("ON");
        c.saveMemento(o.createMemento());
        o.setState("OFF");
        o.restoreMemento(c.retrieveMemento());
    }
}
