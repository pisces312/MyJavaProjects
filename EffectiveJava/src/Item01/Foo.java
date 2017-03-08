package Item1;

// Page 8 - XXX Should flesh out before putting on web site

import java.util.*;

// Provider framework sketch
public abstract class Foo {
    // Maps String key to corresponding Class object
    private static Map<String,Class<?>> implementations = null;

    // Initializes implementations map the first time it's called
    private static synchronized void initMapIfNecessary() {
        if (implementations == null) {
            implementations = new HashMap<String,Class<?>>();

            // Load implementation class names and keys from
            // Properties file, translate names into Class
            // objects using Class.forName and store mappings.
            // ...
        }
    }

    public static Foo getInstance(String key) {
        initMapIfNecessary();
        Class<?> c = implementations.get(key);
        if (c == null)
            return new DefaultFoo();

        try {
            return (Foo) c.newInstance();
        } catch (Exception e) {
            return new DefaultFoo();
        }
    }

    public static void main(String[] args) {
        System.out.println(getInstance("NonexistentFoo"));
    }
}

class DefaultFoo extends Foo {
}
