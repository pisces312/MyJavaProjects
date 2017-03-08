/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.twod;

/**
 *
 * @author Administrator
 */
public class PointY extends Point implements Comparable {
int p;//同一点在数组X中的坐标
//    int id;

    public PointY(double x, double y, int p) {
        super(x, y);
        this.p=p;
    }

    public int compareTo(Object o) {
        double yy = ((PointY) o).y;
        if (this.y < yy) {
            return -1;
        }
        if (this.y == yy) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return this.y == ((PointY) obj).y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
