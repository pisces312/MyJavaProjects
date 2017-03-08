package BehavioralPatterns.Memento.sample1.Originator;

/**
 *  Our test Originator class with Memento class.
 *  Put 2 class into 1 file, so Memento member can be  visited by Originator 
 */
import BehavioralPatterns.Memento.*;
import BehavioralPatterns.Memento.sample1.Memento.TextMemento;

public class TextOriginator {

    private StringBuffer strbuf = new StringBuffer();
    private String state = "";
    private TextMemento memo;

    public TextOriginator() {
        memo = new TextMemento();
    }

    public void addText(String addStr) {
        state = addStr;
        strbuf.append(addStr);
    }

    public void createMemento() {
        memo.addMemo(state);
    }

    public void setMemento() {
        String tmp = memo.getState();
        if (tmp != null) {
            int start = strbuf.length() - tmp.length();
            int end = strbuf.length();
            if (start >= 0) {
                strbuf.delete(start, end);
            } else {
                System.out.println("Get Memo Error !");
            }
        }
    }

    public String getText() {
        return strbuf.toString();
    }
}