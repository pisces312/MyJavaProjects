/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.twod;

/**
 *
 * @author Administrator
 */
public class PointX extends Point implements Comparable {

//    int id;//点的编号

    public PointX(double x, double y) {
        super(x, y);
//        this.id = id;
    }

    public int compareTo(Object o) {
        double xx = ((PointX) o).x;
        if (this.x < xx) {
            return -1;
        }
        if (this.x == xx) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return this.x == ((PointX) obj).x;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
