package net.jcip.examples.chap1;

import net.jcip.annotations.*;

/**
 * UnsafeSequence
 *
 * @author Brian Goetz and Tim Peierls
 */
//race condition issue
@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
