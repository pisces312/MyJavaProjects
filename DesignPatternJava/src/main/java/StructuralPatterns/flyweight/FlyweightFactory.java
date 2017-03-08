/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.flyweight;

import java.util.Hashtable;

/**
 *
 * @author pisces312
 */
public class FlyweightFactory {

    private Hashtable<String, Flyweight> flyweights = new Hashtable<String, Flyweight>();

    public FlyweightFactory() {
        //一般不在初始化时创建实例
//        flyweights.put("x", new ConcreateFlyweight());
//        flyweights.put("y", new ConcreateFlyweight());
//        flyweights.put("z", new ConcreateFlyweight());
    }

    public Flyweight getFlyweight(String key) {
        //需要时创建
        if (flyweights.containsKey(key)) {
            return flyweights.get(key);
        }
        return flyweights.put(key, new ConcreateFlyweight());
    }
}
