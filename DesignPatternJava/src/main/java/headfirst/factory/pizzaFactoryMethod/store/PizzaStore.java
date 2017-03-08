package headfirst.factory.pizzaFactoryMethod.store;

import headfirst.factory.pizzaFactoryMethod.Pizza.Pizza;

public abstract class PizzaStore {

    /**
     * ����ģʽ��ֻ����һ����Ʒ
     * @param item
     * @return
     */
    abstract Pizza createPizza(String item);

    /**
     * ������Ϊfinal���������า��
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
