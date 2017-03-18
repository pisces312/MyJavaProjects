package headfirst.factory.parameterized.factorymethod;

import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleVeggiePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleCheesePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStyleClamPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.ChicagoStyleCheesePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.ChicagoStyleVeggiePizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.NYStylePepperoniPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.ChicagoStyleClamPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.ChicagoStylePepperoniPizza;
import headfirst.factory.pizzaFactoryMethod.Pizza.Pizza;

public class DependentPizzaStore {
 
	public Pizza createPizza(String style, String type) {
		Pizza pizza = null;
		if (style.equals("NY")) {
			if (type.equals("cheese")) {
				pizza = new NYStyleCheesePizza();
			} else if (type.equals("veggie")) {
				pizza = new NYStyleVeggiePizza();
			} else if (type.equals("clam")) {
				pizza = new NYStyleClamPizza();
			} else if (type.equals("pepperoni")) {
				pizza = new NYStylePepperoniPizza();
			}
		} else if (style.equals("Chicago")) {
			if (type.equals("cheese")) {
				pizza = new ChicagoStyleCheesePizza();
			} else if (type.equals("veggie")) {
				pizza = new ChicagoStyleVeggiePizza();
			} else if (type.equals("clam")) {
				pizza = new ChicagoStyleClamPizza();
			} else if (type.equals("pepperoni")) {
				pizza = new ChicagoStylePepperoniPizza();
			}
		} else {
			System.out.println("Error: invalid type of pizza");
			return null;
		}
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}
