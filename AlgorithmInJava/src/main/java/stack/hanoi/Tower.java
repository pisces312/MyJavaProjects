package stack.hanoi;

import java.util.Stack;

public class Tower {
    static Tower[] towers = new Tower[3];

    static void printAll() {
        for (int i = 0; i < towers.length; ++i) {
            towers[i].print();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 5;

        //3 towers
        for (int i = 0; i < towers.length; i++)
            towers[i] = new Tower(i);
        //Initialize
        for (int i = n; i >= 1; i--)
            towers[0].add(i);
        printAll();
        //Core
        towers[0].moveDisks(n, towers[2], towers[1]);
    }

    ///////////////////////////////////////////////
    private Stack<Integer> disks;
    private int index;

    public Tower(int i) {
        disks = new Stack<Integer>();
        index = i;
    }

    public int index() {
        return index;
    }

    public void add(int d) {
        //Only can add smaller disck
        if (!disks.isEmpty() && d >= disks.peek()) {
            System.out.println("Error placing disk " + d);
        } else {
            disks.push(d);
        }
    }

    public void moveTopTo(Tower t) {
        int top = disks.pop();
        t.add(top);
        System.out.println("Move disk " + top + " from " + index() + " to " + t.index());

        //For debug
        printAll();

    }

    public void print() {
        System.out.print("Tower " + index() + ": ");
        for (int i = disks.size() - 1; i >= 0; i--) {
            System.out.print("    " + disks.get(i));
        }
        System.out.println();
    }

    public void moveDisks(int n, Tower destination, Tower buffer) {
        if (n > 0) {
            moveDisks(n - 1, buffer, destination);
            moveTopTo(destination);
            buffer.moveDisks(n - 1, destination, this);
        }
    }
}
