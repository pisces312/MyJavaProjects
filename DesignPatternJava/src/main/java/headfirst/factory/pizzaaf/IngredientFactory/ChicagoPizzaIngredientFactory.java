package headfirst.factory.pizzaaf.IngredientFactory;

import headfirst.factory.pizzaaf.ingredient.PlumTomatoSauce;
import headfirst.factory.pizzaaf.ingredient.SlicedPepperoni;
import headfirst.factory.pizzaaf.ingredient.Spinach;
import headfirst.factory.pizzaaf.ingredient.Dough;
import headfirst.factory.pizzaaf.ingredient.Clams;
import headfirst.factory.pizzaaf.ingredient.MozzarellaCheese;
import headfirst.factory.pizzaaf.ingredient.Pepperoni;
import headfirst.factory.pizzaaf.ingredient.FrozenClams;
import headfirst.factory.pizzaaf.ingredient.Sauce;
import headfirst.factory.pizzaaf.ingredient.Cheese;
import headfirst.factory.pizzaaf.ingredient.Eggplant;
import headfirst.factory.pizzaaf.ingredient.BlackOlives;
import headfirst.factory.pizzaaf.ingredient.ThickCrustDough;
import headfirst.factory.pizzaaf.ingredient.Veggies;
import headfirst.factory.pizzaaf.*;

public class ChicagoPizzaIngredientFactory
        implements PizzaIngredientFactory {

    public Dough createDough() {
        return new ThickCrustDough();
    }

    public Sauce createSauce() {
        return new PlumTomatoSauce();
    }

    public Cheese createCheese() {
        return new MozzarellaCheese();
    }

    public Veggies[] createVeggies() {
        Veggies veggies[] = {new BlackOlives(),
            new Spinach(),
            new Eggplant()};
        return veggies;
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clams createClam() {
        return new FrozenClams();
    }
}
