package headfirst.factory.pizzaFactoryMethod.store;

import headfirst.factory.pizzaFactoryMethod.Pizza.Pizza;

public abstract class PizzaStore {

    /**
     * 工厂模式中只创建一个商品
     * @param item
     * @return
     */
    abstract Pizza createPizza(String item);

    /**
     * 可声明为final，避免子类覆盖
     * @param type
     * @return
     */
    public final Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        System.out.println("--- Making a " + pizza.getName() + " ---");
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
