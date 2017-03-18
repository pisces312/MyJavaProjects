package headfirst.factory.parameterized.factorymethod;

import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleCheesePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleClamPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStylePepperoniPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleVeggiePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.Pizza;

public class EnumPizzaStore {

    public enum PizzaType {

        cheese, veggie, clam, pepperoni
    };

    public enum PizzaStyle {

        NY, Chicago
    };

    public Pizza createPizza(PizzaStyle style, PizzaType type) {
        Pizza pizza = null;
        switch (style) {
            case NY:
                switch (type) {
                    case cheese:
                        pizza = new NYStyleCheesePizza();
                        break;
                    case veggie:
                        pizza = new NYStyleVeggiePizza();
                        break;
                    case clam:
                        pizza = new NYStyleClamPizza();
                        break;
                    case pepperoni:
                        pizza = new NYStylePepperoniPizza();
                        break;
                }
                break;
            case Chicago:
                switch (type) {
                    case cheese:
                        pizza = new NYStyleCheesePizza();
                        break;
                    case veggie:
                        pizza = new NYStyleVeggiePizza();
                        break;
                    case clam:
                        pizza = new NYStyleClamPizza();
                        break;
                    case pepperoni:
                        pizza = new NYStylePepperoniPizza();
                        break;
                }
                break;
            default:
                return null;

        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
