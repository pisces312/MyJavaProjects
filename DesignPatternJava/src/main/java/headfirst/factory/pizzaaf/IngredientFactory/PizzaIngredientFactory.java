package headfirst.factory.pizzaaf.IngredientFactory;

import headfirst.factory.pizzaaf.ingredient.Pepperoni;
import headfirst.factory.pizzaaf.ingredient.Dough;
import headfirst.factory.pizzaaf.ingredient.Sauce;
import headfirst.factory.pizzaaf.ingredient.Veggies;
import headfirst.factory.pizzaaf.ingredient.Clams;
import headfirst.factory.pizzaaf.ingredient.Cheese;
import headfirst.factory.pizzaaf.*;

public interface PizzaIngredientFactory {
 
	public Dough createDough();
	public Sauce createSauce();
	public Cheese createCheese();
	public Veggies[] createVeggies();
	public Pepperoni createPepperoni();
	public Clams createClam();
 
}
