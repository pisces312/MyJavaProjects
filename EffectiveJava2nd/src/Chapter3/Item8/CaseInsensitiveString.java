package Chapter3.Item8;

import java.util.ArrayList;
import java.util.List;

// Broken - violates symmetry! - Pages 36-37

public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        if (s == null)
            throw new NullPointerException();
        this.s = s;
    }

    // Broken - violates symmetry!
//    @Override
    //    public boolean equals(Object o) {
    //        if (o instanceof CaseInsensitiveString)
    //            return s.equalsIgnoreCase(
    //                    ((CaseInsensitiveString)o).s);
    //        if (o instanceof String) // One-way interoperability!
    //            return s.equalsIgnoreCase((String)o);
    //        //                if (o instanceof String)  // also One-way interoperability!
    //        //                    return ((String) o).equalsIgnoreCase(s);
    //        //        if (o instanceof String)  // also One-way interoperability!
    //        //            return ((String) o).toLowerCase().equals(s.toLowerCase());
    //        //                if (o instanceof String)  // also One-way interoperability!
    //        //                    return ((String) o).toLowerCase().equalsIgnoreCase(s.toLowerCase());
    //        return false;
    //    }
    //This version is correct.
    @Override
    public boolean equals(Object o) {
//        return o instanceof CaseInsensitiveString &&
//                ((CaseInsensitiveString)o).s.equalsIgnoreCase(this.s);
//        return o instanceof CaseInsensitiveString &&
//        s.equalsIgnoreCase(((CaseInsensitiveString)o).s);
        
        return o instanceof CaseInsensitiveString &&
        s.toLowerCase().equals(((CaseInsensitiveString)o).s.toLowerCase());
    }

    public static void main(String [] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        //
        System.out.println(cis.equals(s) + "  " + s.equals(cis));
        //
        List<CaseInsensitiveString> list =
                new ArrayList<CaseInsensitiveString>();
        list.add(cis);
        System.out.println(list.contains(s));
    }
}
