package Chapter3.Item12;

import java.util.*;

public class WordList {
    public static void main(String[] args) {
        String str[]={"a","b","c","d"};
        
        
        Set<String> s = new TreeSet<String>();
        Collections.addAll(s, str);
        System.out.println(s);
    }
}
