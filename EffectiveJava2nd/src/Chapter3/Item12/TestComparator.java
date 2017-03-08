package Chapter3.Item12;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

public class TestComparator {

    /**
     * @param args
     */
    public static void main(String [] args) {
        // TODO Auto-generated method stub
        BigDecimal d1=new BigDecimal("1.0");
        BigDecimal d2=new BigDecimal("1.00");
        System.out.println(d1.compareTo(d2));
        assert(d1.compareTo(d2)==0);
        System.out.println(d1.equals(d2));
        assert(!d1.equals(d2));
        
        HashSet<BigDecimal> hs=new HashSet<BigDecimal>();
        hs.add(d1);
        hs.add(d2);
        System.out.println(hs.size());
        //
        TreeSet<BigDecimal> ts=new TreeSet<BigDecimal>();
        ts.add(d1);
        ts.add(d2);
        System.out.println(ts.size());
        assert(hs.size()>ts.size());
        

    }

}
