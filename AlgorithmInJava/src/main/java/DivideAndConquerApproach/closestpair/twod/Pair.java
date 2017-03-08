/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.twod;

/**
 *
 * @author Administrator
 */
public class Pair {

    Point a, b;
    double dist;

    public Pair(Point a, Point b, double dist) {
        this.a = a;
        this.b = b;
        this.dist = dist;
    }

    public Pair(Point a, Point b) {
        this(a, b, dist(a, b));
    }

    public void setPair(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public static double dist(Point u, Point v) {
        //这里不用取绝对值，平法就可以消去
        double dx = u.x - v.x;
        double dy = u.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return a + " , " + b + "      " + dist;
    }
}
