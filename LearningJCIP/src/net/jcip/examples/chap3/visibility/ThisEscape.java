package net.jcip.examples.chap3.visibility;

/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape
 * 
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                //ThisEscape's this is escaped to EventListener
                doSomething(e);
            }
        });
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}

