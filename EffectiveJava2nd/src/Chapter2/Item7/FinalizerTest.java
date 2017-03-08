package Chapter2.Item7;

public class FinalizerTest {

    // Finalizer Guardian idiom
    static class Foo {
        // Sole purpose of this object is to finalize outer Foo object
        private final Object finalizerGuardian = new Object() {
                                                   @Override
                                                   protected void finalize() throws Throwable {
                                                       // Finalize outer Foo object
                                                       System.out.println("Foo finalize");
                                                   }
                                               };
        // Remainder omitted
    }

    static class BarBase {

    }
    static class Bar extends BarBase {
        // Manual finalizer chaining
        @Override
        protected void finalize() throws Throwable {
            try {
                System.out.println("Bar finalize");
                // Finalize subclass state
            } finally {
                System.out.println("Bar's parent finalize");
                super.finalize();
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            new Bar().finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new Foo();
    }
}
