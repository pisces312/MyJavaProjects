package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pisces
 */
public class Matrix {

    /**
     * 产生一个单位方阵
     * @param n
     * @return
     */
    public static Matrix createUnitSquareMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 1;
        }
        return new Matrix(matrix, n, n);
    }
    double[][] matrix;
    int row, col;

    public Matrix(double[][] matrix, int row, int col) {
        this.matrix = matrix;
        this.col = col;
        this.row = row;
    }

    public Matrix(int row, int col) {

        this.col = col;
        this.row = row;
        matrix = new double[row][col];
    }

    /**
     * 任意矩阵的乘法
     * @param a
     * @param b
     * @return
     */
    public static Matrix multiplyBasic(Matrix a, Matrix b) {
        double[][] result = null;
        //无法相乘的情况
        if (a.col != b.row) {
            return null;
        }
        result = new double[a.row][b.col];
        double sum = 0;
        for (int i = 0; i < a.row; i++) {

            for (int k = 0; k < b.col; k++) {
                sum = a.matrix[i][0] * b.matrix[0][k];
                for (int j = 1; j < a.col; j++) {
                    sum += a.matrix[i][j] * b.matrix[j][k];
                }
                result[i][k] = sum;
            }
        }
//        double t=
        return new Matrix(result, a.row, b.col);

    }

    public static double[][] substract(double[][] m1, int row1, int col1, double[][] m2, int row2, int col2) {
        if (row1 != row2 || col1 != col2) {
            return null;
        }
        double[][] result = new double[row1][col1];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                result[i][j] = m1[i][j] - m2[i][j];
            }
        }
        return result;
    }

    public static Matrix substract(Matrix m1, Matrix m2) {
        if (m1.row != m2.row || m1.col != m2.col) {
            return null;
        }
        Matrix result = new Matrix(new double[m1.row][m1.row], m1.row, m1.row);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m1.col; j++) {
                result.matrix[i][j] = m1.matrix[i][j] - m2.matrix[i][j];
            }
        }
        return result;
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if (m1.row != m2.row || m1.col != m2.col) {
            return null;
        }
        Matrix result = new Matrix(new double[m1.row][m1.row], m1.row, m1.row);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m1.col; j++) {
                result.matrix[i][j] = m1.matrix[i][j] + m2.matrix[i][j];
            }
        }
        return result;

    }

    public static void add(Matrix m1, Matrix m2, Matrix result) {
        add(m1.matrix, m1.row, m1.col, m2.matrix, m2.row, m2.col, result.matrix, 0, 0);
    }

    /**
     * 存放到result的x y为起点的矩阵
     * @param m1
     * @param row1
     * @param col1
     * @param m2
     * @param row2
     * @param col2
     * @param result
     * @param x
     * @param y
     */
    public static void add(double[][] m1, int row1, int col1, double[][] m2, int row2, int col2, double[][] result, int x, int y) {
        if (row1 != row2 || col1 != col2) {
            return;
        }
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                result[i + x][j + y] = m1[i][j] + m2[i][j];
            }
        }
    }

    /**
     * 2次幂的矩阵的递归乘法
     * @param m1
     * @param m2
     * @return
     */
    public static Matrix multiply2NSquareMatrixDriver(Matrix m1, Matrix m2) {
//        double[][] result = new double[m1.row][m1.col];
//        multiply2NSquareMatrix(m1.matrix, 0, 0, m2.matrix, 0, 0, m1.row, result, 0, 0);
//        return new Matrix(result, m1.row, m1.col);
        return new Matrix(multiply2NSquareMatrix(m1.matrix, 0, 0, m2.matrix, 0, 0, m1.row), m1.row, m1.col);
    }

//    public static double[][] multiply2NSquareMatrix(double[][] m1, int x1, int y1, double[][] m2, int x2, int y2, int len, double[][] result, int x3, int y3) {
//
////        double[][] result = new double[len][len];
//        //递归出口
//        if (len == 2) {
////            result = new double[len][len];
//            result[x3][y3] = m1[x1][y1] * m2[x2][y2] + m1[x1][y1 + 1] * m2[x2 + 1][y2];
//            result[x3][y3 + 1] = m1[x1][y1] * m2[x2][y2 + 1] + m1[x1][y1 + 1] * m2[x2 + 1][y2 + 1];
//            result[x3 + 1][y3] = m1[x1 + 1][y1] * m2[x2][y2] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2];
//            result[x3 + 1][y3 + 1] = m1[x1 + 1][y1] * m2[x2][y2 + 1] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2 + 1];
//
//        } else {
//            int halfLen = len / 2;
//            int quarterLen = halfLen;
////            result=new double[len][len];
//            double[][] a = null, b = null;
//
//            //0 0
//            a = multiply2NSquareMatrix(m1, x1, y1, m2, x2, y2, halfLen);
//            //1 2
//            b = multiply2NSquareMatrix(m1, x1, y1 + quarterLen, m2, x2 + quarterLen, y2, halfLen);
//            add(a, halfLen, halfLen, b, halfLen, halfLen, result, x3, y3);
////            System.out.println("add");
////            printMatrix(new Matrix(result,len,len));
////            System.out.println();
//            //0 1
//            a = multiply2NSquareMatrix(m1, x1, y1, m2, x2, y2 + quarterLen, halfLen);
//            //1,3
//            b = multiply2NSquareMatrix(m1, x1, y1 + quarterLen, m2, x2 + quarterLen, y2 + quarterLen, halfLen);
//            add(a, halfLen, halfLen, b, halfLen, halfLen, result, x3, y3 + halfLen);
//            //2,0
//            a = multiply2NSquareMatrix(m1, x1 + quarterLen, y1, m2, x2, y2, halfLen);
//            //3,2
//            b = multiply2NSquareMatrix(m1, x1 + quarterLen, y1 + quarterLen, m2, x2 + quarterLen, y2, halfLen);
//            add(a, halfLen, halfLen, b, halfLen, halfLen, result, x3 + halfLen, y3);
//            //2,1
//            a = multiply2NSquareMatrix(m1, x1 + quarterLen, y1, m2, x2, y2 + quarterLen, halfLen);
//            //3,3
//            b = multiply2NSquareMatrix(m1, x1 + quarterLen, y1 + quarterLen, m2, x2 + quarterLen, y2 + quarterLen, halfLen);
//            add(a, halfLen, halfLen, b, halfLen, halfLen, result, x3 + halfLen, y3 + halfLen);
//        }
//        if (len == 2) {
//            System.out.println("2阶");
//            printMatrix(new Matrix(result, len, len));
//        } else {
//            System.out.println("高阶");
//            printMatrix(new Matrix(result, len, len));
//        }
//        System.out.println();
//        return result;
//    }
    /**
     * 正确
     * @param m1
     * @param x1
     * @param y1
     * @param m2
     * @param x2
     * @param y2
     * @param len
     * @return
     */
    public static double[][] multiply2NSquareMatrix(double[][] m1, int x1, int y1, double[][] m2, int x2, int y2, int len) {
        int i, j;
        double[][] result = new double[len][len];
        //递归出口
        if (len == 2) {
            result[0][0] = m1[x1][y1] * m2[x2][y2] + m1[x1][y1 + 1] * m2[x2 + 1][y2];
            result[0][1] = m1[x1][y1] * m2[x2][y2 + 1] + m1[x1][y1 + 1] * m2[x2 + 1][y2 + 1];
            result[1][0] = m1[x1 + 1][y1] * m2[x2][y2] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2];
            result[1][1] = m1[x1 + 1][y1] * m2[x2][y2 + 1] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2 + 1];

        } else {
            int halfLen = len / 2;
            double[][] a = null, b = null;
            //0 0
            a = multiply2NSquareMatrix(m1, x1, y1, m2, x2, y2, halfLen);
            //1 2
            b = multiply2NSquareMatrix(m1, x1, y1 + halfLen, m2, x2 + halfLen, y2, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, 0, 0);
//            System.out.println("add");
//            printMatrix(new Matrix(result,len,len));
//            System.out.println();
            //0 1
            a = multiply2NSquareMatrix(m1, x1, y1, m2, x2, y2 + halfLen, halfLen);
            //1,3
            b = multiply2NSquareMatrix(m1, x1, y1 + halfLen, m2, x2 + halfLen, y2 + halfLen, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, 0, halfLen);
            //2,0
            a = multiply2NSquareMatrix(m1, x1 + halfLen, y1, m2, x2, y2, halfLen);
            //3,2
            b = multiply2NSquareMatrix(m1, x1 + halfLen, y1 + halfLen, m2, x2 + halfLen, y2, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, halfLen, 0);
            //2,1
            a = multiply2NSquareMatrix(m1, x1 + halfLen, y1, m2, x2, y2 + halfLen, halfLen);
            //3,3
            b = multiply2NSquareMatrix(m1, x1 + halfLen, y1 + halfLen, m2, x2 + halfLen, y2 + halfLen, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, halfLen, halfLen);
        }
        if (len == 2) {
            System.out.println("2阶");
            printMatrix(new Matrix(result, len, len));
        } else {
            System.out.println("高阶");
            printMatrix(new Matrix(result, len, len));
        }
        System.out.println();
        return result;
    }

    /**
     * 正确
     * @param m1
     * @param x1
     * @param y1
     * @param m2
     * @param x2
     * @param y2
     * @param len
     * @return
     */
    public static double[][] multiplyStrassen(double[][] m1, int x1, int y1, double[][] m2, int x2, int y2, int len) {
        int i, j;
        double[][] result = new double[len][len];
        //递归出口
        if (len == 2) {
            result[0][0] = m1[x1][y1] * m2[x2][y2] + m1[x1][y1 + 1] * m2[x2 + 1][y2];
            result[0][1] = m1[x1][y1] * m2[x2][y2 + 1] + m1[x1][y1 + 1] * m2[x2 + 1][y2 + 1];
            result[1][0] = m1[x1 + 1][y1] * m2[x2][y2] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2];
            result[1][1] = m1[x1 + 1][y1] * m2[x2][y2 + 1] + m1[x1 + 1][y1 + 1] * m2[x2 + 1][y2 + 1];

        } else {
            int halfLen = len / 2;
            double[][] a = null, b = null;
            Matrix[] m=new Matrix[7];
            
            //0 0
            a = multiplyStrassen(m1, x1, y1, m2, x2, y2, halfLen);
            //1 2
            b = multiplyStrassen(m1, x1, y1 + halfLen, m2, x2 + halfLen, y2, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, 0, 0);
//            System.out.println("add");
//            printMatrix(new Matrix(result,len,len));
//            System.out.println();
            //0 1
            a = multiplyStrassen(m1, x1, y1, m2, x2, y2 + halfLen, halfLen);
            //1,3
            b = multiplyStrassen(m1, x1, y1 + halfLen, m2, x2 + halfLen, y2 + halfLen, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, 0, halfLen);
            //2,0
            a = multiplyStrassen(m1, x1 + halfLen, y1, m2, x2, y2, halfLen);
            //3,2
            b = multiplyStrassen(m1, x1 + halfLen, y1 + halfLen, m2, x2 + halfLen, y2, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, halfLen, 0);
            //2,1
            a = multiplyStrassen(m1, x1 + halfLen, y1, m2, x2, y2 + halfLen, halfLen);
            //3,3
            b = multiplyStrassen(m1, x1 + halfLen, y1 + halfLen, m2, x2 + halfLen, y2 + halfLen, halfLen);
            add(a, halfLen, halfLen, b, halfLen, halfLen, result, halfLen, halfLen);
        }
        if (len == 2) {
            System.out.println("2阶");
            printMatrix(new Matrix(result, len, len));
        } else {
            System.out.println("高阶");
            printMatrix(new Matrix(result, len, len));
        }
        System.out.println();
        return result;
    }

    /**
     * 分割方阵,对半分
     * 0左上，1右上
     * 2左下，3右下
     * @param m
     * @return
     */
    public static Matrix[] splitMatrix(Matrix m) {
        Matrix[] result = new Matrix[4];
        int len = m.row / 2;
        double[][] array = null;
        int row = 0, col = 0;
        for (int k = 0; k < result.length; k++) {
            array = new double[len][len];
            switch (k) {
                case 0:
                    row = 0;
                    col = 0;
                    break;
                case 1:
                    row = 0;
                    col = len;
                    break;
                case 2:
                    row = len;
                    col = 0;
                    break;
                case 3:
                    row = len;
                    col = len;
            }
            for (int i = 0; i < len; i++) {
//                row = i * len;
                for (int j = 0; j < len; j++) {
                    array[i][j] = m.matrix[row + i][col + j];
                }

            }
            result[k] = new Matrix(array, len, len);
        }
        return result;
    }

    public static void printMatrix(Matrix m) {
        for (int i = 0; i < m.row; i++) {
            for (int j = 0; j < m.col; j++) {
                System.out.print(m.matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void test() {

//        printMatrix(matrix);
    }
}
