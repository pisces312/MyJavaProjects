package Chapter4.Item13;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UnmodifiableListTest {
    static class Thing {
        int i;

        public Thing(int n) {
            i = n;
        }

        public void setValue(int n) {
            i = n;
        }
    }

    // Potential security hole! 
    public static final Thing [] VALUES = {new Thing(1), new Thing(2)};

    public static final Thing [] values() {
        return VALUES.clone();
    }

    public static final List<Thing> unmodifiableList =
                                                             Collections.unmodifiableList(Arrays.asList(VALUES));

    /**
     * Exception in thread "main" java.lang.UnsupportedOperationException
     * at java.util.Collections$UnmodifiableList.add(Collections.java:1092)
     * at
     * Chapter4.Item13.UnmodifiableListTest.main(UnmodifiableListTest.java:35)
     * 
     * @param args
     */
    public static void main(String [] args) {
        // TODO Auto-generated method stub
        //will throw exception
        unmodifiableList.add(0, new Thing(3));
        //        Thing t = unmodifiableList.get(0);
        //        t.setValue(2);
    }

}
