/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.twod;

import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class Point {

    public final static Comparator<Point> cmpX = new Comparator<Point>() {

        public int compare(Point o1, Point o2) {

            if (o1.x < o2.x) {
                return -1;
            }
            if (o1.x == o2.x) {
                return 0;
            }
            return 1;
        }
    };
    public final static Comparator<Point> cmpY = new Comparator<Point>() {

        public int compare(Point o1, Point o2) {

            if (o1.y < o2.y) {
                return -1;
            }
            if (o1.y == o2.y) {
                return 0;
            }
            return 1;
        }
    };
    double x, y;
    public int xOrderIndex;//用于记录

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    @Override
    public String toString() {
        return "(" + x + " , " + y + ")";
    }
}
