package net.jcip.examples.chap3.visibility;

/**
 * Holder
 * <p/>
 * Class at risk of failure if not properly published
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }
//In other threads, two "n" may different
    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}
