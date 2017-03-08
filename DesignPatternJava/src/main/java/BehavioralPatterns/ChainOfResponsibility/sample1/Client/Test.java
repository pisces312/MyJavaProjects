/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.ChainOfResponsibility.sample1.Client;

import BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler.Manager;
import BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler.Others;
import BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler.Programmer;
import BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler.ProjectManager;
import BehavioralPatterns.ChainOfResponsibility.sample1.ConcreteHandler.QA;

/**
 *
 * @author pisces312
 */
public class Test {

    public static void main(String[] args) {
        Manager aManager = new Manager();
        ProjectManager aPM = new ProjectManager();
        Programmer aProgrammer = new Programmer();
        QA aQA = new QA();
        Others others = new Others();

        aManager.setNextChain(aPM);
        aPM.setNextChain(aProgrammer);
        aProgrammer.setNextChain(aQA);
        aQA.setNextChain(others);

//        aManager.sendToChain("Get Project");
//        aManager.sendToChain("Design");
//        aManager.sendToChain("Coding");
//        aManager.sendToChain("Test");
//        aManager.sendToChain("Kill La Deng !");
        //
        //���������һ�������ߴ���һ�������ܴ�������񣬸ô����߻ᰴ�����������佻����һ��
        aManager.sendToChain("Design");
//        aManager.sendToChain("Design");
//        aManager.sendToChain("Coding");
//        aManager.sendToChain("Test");
//        aManager.sendToChain("Kill La Deng !");
    }
}
