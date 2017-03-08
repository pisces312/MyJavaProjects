package headfirst.factory.pizzaaf.IngredientFactory;

import headfirst.factory.pizzaaf.ingredient.ReggianoCheese;
import headfirst.factory.pizzaaf.ingredient.Clams;
import headfirst.factory.pizzaaf.ingredient.RedPepper;
import headfirst.factory.pizzaaf.ingredient.Cheese;
import headfirst.factory.pizzaaf.ingredient.ThinCrustDough;
import headfirst.factory.pizzaaf.ingredient.MarinaraSauce;
import headfirst.factory.pizzaaf.ingredient.Garlic;
import headfirst.factory.pizzaaf.ingredient.Veggies;
import headfirst.factory.pizzaaf.ingredient.SlicedPepperoni;
import headfirst.factory.pizzaaf.ingredient.Dough;
import headfirst.factory.pizzaaf.ingredient.Onion;
import headfirst.factory.pizzaaf.ingredient.FreshClams;
import headfirst.factory.pizzaaf.ingredient.Mushroom;
import headfirst.factory.pizzaaf.ingredient.Sauce;
import headfirst.factory.pizzaaf.ingredient.Pepperoni;
import headfirst.factory.pizzaaf.*;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
 
	public Dough createDough() {
		return new ThinCrustDough();
	}
 
	public Sauce createSauce() {
		return new MarinaraSauce();
	}
 
	public Cheese createCheese() {
		return new ReggianoCheese();
	}
 
	public Veggies[] createVeggies() {
		Veggies veggies[] = { new Garlic(), new Onion(), new Mushroom(), new RedPepper() };
		return veggies;
	}
 
	public Pepperoni createPepperoni() {
		return new SlicedPepperoni();
	}

	public Clams createClam() {
		return new FreshClams();
	}
}
