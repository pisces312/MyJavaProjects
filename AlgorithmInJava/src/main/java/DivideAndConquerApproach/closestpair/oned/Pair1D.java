/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.oned;

/**
 *
 * @author Administrator
 */
public class Pair1D {

    public double x,  y;
    public double d;

    public Pair1D(double x, double y) {
        this.x = x;
        this.y = y;
        d=Math.abs(x-y);
    }
}
