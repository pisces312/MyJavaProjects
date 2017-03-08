package headfirst.factory.pizzaFactoryMethod.store;

import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleVeggiePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleCheesePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleClamPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStylePepperoniPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.Pizza;

public class NYPizzaStore extends PizzaStore {

	Pizza createPizza(String item) {
		if (item.equals("cheese")) {
			return new NYStyleCheesePizza();
		} else if (item.equals("veggie")) {
			return new NYStyleVeggiePizza();
		} else if (item.equals("clam")) {
			return new NYStyleClamPizza();
		} else if (item.equals("pepperoni")) {
			return new NYStylePepperoniPizza();
		} else return null;
	}
}
