/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DivideAndConquerApproach.closestpair.twod;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class ClosestPair2D {

    

    public ClosestPair2D(Point[] nodes) {
    }

    public static Pair findPair(PointX[] x) {
        if (x.length < 2) {
            return null;
        }

        Arrays.sort(x);//根据x轴排序

        System.out.println(Arrays.toString(x));
        PointY[] y = new PointY[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = new PointY(x[i].x, x[i].y, i);//!!!!   i为在数组x中的坐标
        }
        Arrays.sort(y);//根据y轴排序
        System.out.println(Arrays.toString(y));
        PointY[] z = new PointY[x.length];
        return cloesetPair(x, y, z, 0, x.length - 1);
//        return null;
    }

    private static Pair cloesetPair(final PointX[] x, PointY[] y, PointY[] z, int startIndex, int endIndex) {
        if (endIndex - startIndex == 1) {//两点
            return new Pair(x[startIndex], x[endIndex]);
        }
        if (endIndex - startIndex == 2) {//三点
            double d1 = Pair.dist(x[startIndex], x[startIndex + 1]);
            double d2 = Pair.dist(x[startIndex + 1], x[endIndex]);
            double d3 = Pair.dist(x[startIndex], x[endIndex]);
            //三个数只需要比较3次
            if (d1 <= d2 && d1 <= d3) {//d1最小的情况
                return new Pair(x[startIndex], x[startIndex + 1], d1);
            }
            if (d2 <= d3) {//d1一定不是最小，则在d2和d3中比较即可
                return new Pair(x[endIndex], x[startIndex + 1], d2);
            }
            return new Pair(x[startIndex], x[endIndex], d3);
        }
        //多余3点
        //分组
        int middleIndex = (startIndex + endIndex) / 2;
        int front = startIndex, back = middleIndex + 1;
        for (int i = startIndex; i <= endIndex; i++) {
            if (y[i].p > middleIndex) {//x的下标大于m则放到后面一组中  !!!!
                z[back++] = y[i];
            } else {
                z[front++] = y[i];
            }
        }
//        System.out.println(Arrays.toString(z));
        //递归求解
        //best最终存放两侧最短距离的点对
        Pair best = cloesetPair(x, z, y, startIndex, middleIndex);
        Pair right = cloesetPair(x, z, y, middleIndex + 1, endIndex);
        if (right.dist < best.dist) {
            best = right;
        }
        //重构数组 将两个数组合并到后一个数组
        merge((Comparable[]) z, y, startIndex, middleIndex, endIndex);
        //d矩形条内的点置于z中
        int k = startIndex;
        for (int i = startIndex; i <= endIndex; i++) {
            //即与中间值比较
            if (Math.abs(x[middleIndex].x - y[i].x) < best.dist) {
                z[k++] = y[i];//这里z第二次赋值,共有r-L+1
            }
        }
        //对范围内的集合z[l...k-1]进行搜索
        for (int i = startIndex; i < k; i++) {
            for (int j = i + 1; j < k && Math.abs(z[j].y - z[i].y) < best.dist; j++) {
                double dp = Pair.dist(z[i], z[j]);
                if (dp < best.dist) {
                    best.setPair(x[z[i].p], x[z[j].p]);
                    best.setDist(dp);
                }

            }
        }
        return best;
    }
    //合并c[l...m]和c[m+1...r]到d[l...r]

    public static void merge(Comparable[] c, Comparable[] d, int start, int middle, int end) {
        int i = start, j = middle + 1, k = start;
        while ((i <= middle) && (j <= end)) {
            if (c[i].compareTo(c[j]) <= 0) {
                d[k++] = c[i++];
            } else {
                d[k++] = c[j++];
            }

        }
        if (i > middle) {
            for (int q = j; q <= end; q++) {
                d[k++] = c[q];
            }
        } else {
            for (int q = i; q <= middle; q++) {
                d[k++] = c[q];
            }
        }
    }
//第二种方法

    public static Pair findPairEx(Point[] x) {
        if (x.length < 2) {
            return null;
        }

        Arrays.sort(x, Point.cmpX);//根据x轴排序
        System.out.println(Arrays.toString(x));
        for (int i = 0; i < x.length; i++) {
            x[i].xOrderIndex = i;
        }
        Arrays.sort(x, Point.cmpY);//再根据y轴排序
        System.out.println(Arrays.toString(x));
//        PointY[] z = new PointY[x.length];
        Point[] temp = new Point[x.length];
        return cloesetPair2(x, temp, 0, x.length - 1);
//        return null;
    }

    private static Pair cloesetPair2(Point[] x, Point[] temp, int startIndex, int endIndex) {
        if (endIndex - startIndex == 1) {//两点
            return new Pair(x[startIndex], x[endIndex]);
        }
        if (endIndex - startIndex == 2) {//三点
            double d1 = Pair.dist(x[startIndex], x[startIndex + 1]);
            double d2 = Pair.dist(x[startIndex + 1], x[endIndex]);
            double d3 = Pair.dist(x[startIndex], x[endIndex]);
            //三个数只需要比较3次
            if (d1 <= d2 && d1 <= d3) {//d1最小的情况
                return new Pair(x[startIndex], x[startIndex + 1], d1);
            }
            if (d2 <= d3) {//d1一定不是最小，则在d2和d3中比较即可
                return new Pair(x[endIndex], x[startIndex + 1], d2);
            }
            return new Pair(x[startIndex], x[endIndex], d3);
        }
        //多余3点
        //根据x坐标分组分组
        int middleIndex = (startIndex + endIndex) / 2;
        int front = startIndex, back = middleIndex + 1;
        for (int i = startIndex; i <= endIndex; i++) {
            if (x[i].xOrderIndex > middleIndex) {//x的下标大于m则放到后面一组中  !!!!
                temp[back++] = x[i];
            } else {
                temp[front++] = x[i];
            }
        }
//        System.out.println(Arrays.toString(z));
        //递归求解
        //best最终存放两侧最短距离的点对
        Pair best = cloesetPair2(x, temp, startIndex, middleIndex);
        Pair right = cloesetPair2(x, temp, middleIndex + 1, endIndex);
        if (right.dist < best.dist) {
            best = right;
        }
        //重构数组 将两个数组合并到后一个数组
        merge(temp, x, Point.cmpY, startIndex, middleIndex, endIndex);
        //d矩形条内的点置于z中
        int k = startIndex;
        for (int i = startIndex; i <= endIndex; i++) {
            //即与中间值比较
            if (Math.abs(x[middleIndex].x - x[i].x) < best.dist) {
                temp[k++] = x[i];//这里z第二次赋值,共有r-L+1
            }
        }
        //对范围内的集合z[l...k-1]进行搜索
        for (int i = startIndex; i < k; i++) {
            for (int j = i + 1; j < k && Math.abs(temp[j].y - temp[i].y) < best.dist; j++) {
                double dp = Pair.dist(temp[i], temp[j]);
                if (dp < best.dist) {
                    best.setPair(x[temp[i].xOrderIndex], x[temp[j].xOrderIndex]);
                    best.setDist(dp);
                }

            }
        }
        return best;
    }

    public static void merge(Point[] c, Point[] d, Comparator cmp, int start, int middle, int end) {
        int i = start, j = middle + 1, k = start;
        while ((i <= middle) && (j <= end)) {
            if (cmp.compare(c[i], c[j]) <= 0) {//c[i].y<=c[j].y) {
                d[k++] = c[i++];
            } else {
                d[k++] = c[j++];
            }

        }
        if (i > middle) {
            for (int q = j; q <= end; q++) {
                d[k++] = c[q];
            }
        } else {
            for (int q = i; q <= middle; q++) {
                d[k++] = c[q];
            }
        }
    }

    /**
     * 第二种方法只需要两个函数
     * @param x
     * @return
     */
    public static Pair findPairEx2(Point[] x) {
        if (x.length < 2) {
            return null;
        }

        Arrays.sort(x, Point.cmpX);//根据x轴排序
        System.out.println(Arrays.toString(x));
        for (int i = 0; i < x.length; i++) {
            x[i].xOrderIndex = i;
        }
        Arrays.sort(x, Point.cmpY);//再根据y轴排序
        System.out.println(Arrays.toString(x));
        //辅助数组，一次创建，在递归中多次使用
        Point[] temp = new Point[x.length];
        return closesetPairEx2(x, temp, 0, x.length - 1);
//        return null;
    }
//寻找的过程不改变输入
//这时传入的src为经过y坐标排序的数组

    private static Pair closesetPairEx2(final Point[] src, Point[] temp, int xStartIndex, int xEndIndex) {
        if (xEndIndex - xStartIndex == 1) {//两点
            return new Pair(src[xStartIndex], src[xEndIndex]);
        }
        if (xEndIndex - xStartIndex == 2) {//三点
            double d1 = Pair.dist(src[xStartIndex], src[xStartIndex + 1]);
            double d2 = Pair.dist(src[xStartIndex + 1], src[xEndIndex]);
            double d3 = Pair.dist(src[xStartIndex], src[xEndIndex]);
            //三个数只需要比较3次
            if (d1 <= d2 && d1 <= d3) {//d1最小的情况
                return new Pair(src[xStartIndex], src[xStartIndex + 1], d1);
            }
            if (d2 <= d3) {//d1一定不是最小，则在d2和d3中比较即可
                return new Pair(src[xEndIndex], src[xStartIndex + 1], d2);
            }
            return new Pair(src[xStartIndex], src[xEndIndex], d3);
        }
        //多余3点
        //分组
        int xMiddleIndex = (xStartIndex + xEndIndex) / 2;
        //递归求解
        //best最终存放两侧最短距离的点对
        Pair best = closesetPairEx2(src, temp, xStartIndex, xMiddleIndex);
        Pair right = closesetPairEx2(src, temp, xMiddleIndex + 1, xEndIndex);
        if (right.dist < best.dist) {
            best = right;
        }
        //d矩形条内的点置于temp中
        int count = xStartIndex;//记录在矩形内的点数
        for (int i = xStartIndex; i <= xEndIndex; i++) {
            //即与中间值比较            
            if (Math.abs(src[xMiddleIndex].x - src[i].x) < best.dist) {
                //temp是按y递增的顺序排列的
                temp[count++] = src[i];//这里z第二次赋值,共有r-L+1
            }
        }
//        System.out.println("temp " + Arrays.toString(temp));
//        System.out.println("src "+Arrays.toString(src));
        //!!!!这里可以再优化

        //对范围内的集合z[l...k-1]进行搜索

        //!!!有问题
//        double t = best.dist + best.dist / 2;//控制搜索在当前点的上三行之内,???精度会造成影响？？
        //过多的控制搜索范围反而会增加时间复杂度!!
//        int maxIndex=count;
//        int maxIndex=count;
        for (int cur = xStartIndex; cur < count; cur++) {
            //这里可以控制在7个内进行判断！！！！

//            int maxIndex = count - 1;
//            if (maxIndex - cur > 7) {
//                maxIndex = cur + 7;//最多只对大于cur的y坐标的7个点进行判断
//            }
            //将对7的控制写在for语句中最多执行两次判断
            for (int toCmp = cur + 1; toCmp < count && toCmp <= cur + 7/*toCmp <= maxIndex*/; toCmp++) {
//                按照y的顺序，从小到达比较，只需比较y比它大的另一侧即可！！！
//                if ((temp[cur].xOrderIndex <= xMiddleIndex && temp[toCmp].xOrderIndex >= xMiddleIndex) || (temp[cur].xOrderIndex >= xMiddleIndex && temp[toCmp].xOrderIndex <= xMiddleIndex)) {
                //比较大于等于cur的y坐标的点，控制搜索在当前点的上三行之内
//                boolean flag = (temp[cur].y <= temp[toCmp].y) && ((temp[toCmp].y <= temp[cur].y + t));
                //只对在水平或垂直方向上的距离小于d的点判断
//                boolean flag = Math.abs(temp[toCmp].y - temp[cur].y) < best.dist && Math.abs(temp[toCmp].x - temp[cur].x) < best.dist;
                double dx = Math.abs(temp[toCmp].y - temp[cur].y);
                double dy = Math.abs(temp[toCmp].x - temp[cur].x);
                if (dx < best.dist && dy < best.dist) {
                    double dp = Math.sqrt(dx * dx + dy * dy);//Pair.dist(temp[cur], temp[toCmp]);
                    if (dp < best.dist) {
                        best.setPair(temp[cur], temp[toCmp]);
                        best.setDist(dp);
                    }
                }
//                }

            }
        }
        return best;
    }

    public static void main(String[] args) {
//        PointX[] x = {new PointX(2.0, 2.0), new PointX(-2.0, 2.0), new PointX(0, 0), new PointX(4, 6)};
//        PointX[] x = {new PointX(4.0, 2.0), new PointX(-2.0, 2.0), new PointX(0, 0), new PointX(4, 6), new PointX(-5, 2.5),
//            new PointX(12.0, 8.0), new PointX(-1.0, 7.0), new PointX(0, 10), new PointX(-4, 6), new PointX(3.5, 2.5)
//        };
        Point[] x = {new Point(2.0, 2.0), new Point(-2.0, 2.0), new Point(0, 0), new Point(4, 6), new Point(-5, 2.5),
            new Point(12.0, 8.0), new Point(-1.0, 7.0), new Point(0, 10), new Point(-4, 6), new Point(3.5, 2.5), new Point(0.8, 1.2)
        };
//                Point[] x = {new Point(2.0, 2.0), new Point(-2.0, 2.0), new Point(0, 0), new Point(4, 6), new Point(-5, 2.5),
//            new Point(12.0, 8.0), new Point(-1.0, 7.0), new Point(0, 10), new Point(-4, 6), new Point(3.5, 2.5)
//        };
//        PointX[] x = {new PointX(2.0, 2.0), new PointX(-2.0, 2.0), new PointX(0, 0)};
//        PointX[] x = {new PointX(1.0, 2.0), new PointX(-1.0, 2.0),new PointX(-1.5,2)};
//        PointX[] x = {new PointX(1.0, 2.0), new PointX(-1.0, 2.0)};
//        Pair p = findPair(x);
////        Pair p = findPairEx(x);
//        System.out.println(p);
//        System.out.println();
        Pair p = findPairEx2(x);
        System.out.println(p);
//        testOne();
//        Node[] nodes = {new Node(1, 2), new Node(2, 3)};
//        ClosestPair cp = new ClosestPair(nodes);
    }
}
