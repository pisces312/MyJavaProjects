package headfirst.factory.pizzaaf.store;

import headfirst.factory.pizzaaf.IngredientFactory.ChicagoPizzaIngredientFactory;
import headfirst.factory.pizzaaf.IngredientFactory.PizzaIngredientFactory;
import headfirst.factory.pizzaaf.Pizza.CheesePizza;
import headfirst.factory.pizzaaf.Pizza.ClamPizza;
import headfirst.factory.pizzaaf.Pizza.PepperoniPizza;
import headfirst.factory.pizzaaf.Pizza.Pizza;
import headfirst.factory.pizzaaf.Pizza.VeggiePizza;

public class ChicagoPizzaStore extends PizzaStore {

    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory =
                new ChicagoPizzaIngredientFactory();

        if (item.equals("cheese")) {

            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("Chicago Style Cheese Pizza");

        } else if (item.equals("veggie")) {

            pizza = new VeggiePizza(ingredientFactory);
            pizza.setName("Chicago Style Veggie Pizza");

        } else if (item.equals("clam")) {

            pizza = new ClamPizza(ingredientFactory);
            pizza.setName("Chicago Style Clam Pizza");

        } else if (item.equals("pepperoni")) {

            pizza = new PepperoniPizza(ingredientFactory);
            pizza.setName("Chicago Style Pepperoni Pizza");

        }
        return pizza;
    }
}
