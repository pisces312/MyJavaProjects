/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAlgorithm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pisces312
 */
public class RandomAlgorithm {

    final static double C = 262147;//一个素数
    final static double R0 = 34359738365.0 / 549755813888.0;
//        double c=C;
    double r = R0;
//    File file;
//FileOutputStream fos;
//PrintWriter pw;
    DataOutputStream dos;
    DataInputStream dis;

    public RandomAlgorithm() {
        this(System.currentTimeMillis() % 9000 + 1000);
    }

    public RandomAlgorithm(long seed) {
        String path = "e:/random.txt";
//        System.out.println(path);
        File file = new File(path);
        try {
            file.createNewFile();
//        if(!file.exists()){
//        }
//            pw =new PrintWriter(file);
            FileInputStream fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            FileOutputStream fos = new FileOutputStream(file);

            dos = new DataOutputStream(fos);


//            seed = System.currentTimeMillis()%90000+10000;
            System.out.println(seed);
            for (int i = 0; i < seed; i++) {
                nextDouble();
            }
            dos.writeDouble(r);

        } catch (IOException ex) {
            Logger.getLogger(RandomAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }


//        fos.
//        if(!file.exists()){
//        }
    }

    public double currentDouble() {
        return r;
    }

    public double nextDouble() {
        double temp = r * C;
        r = temp - (long) temp;

        return r;
    }

    public void saveDouble() {
        try {
            dos.writeDouble(r);
        } catch (IOException ex) {
            Logger.getLogger(RandomAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double previousDouble() {
        try {
            return dis.readDouble();
        } catch (IOException ex) {
            Logger.getLogger(RandomAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextDouble();
    }

    public int nextInt(int n) {
        return (int) (nextDouble() * n);
    }

    /**
     * 平均分布
     * @param a
     * @param b
     * @return
     */
    public double averageDistribution(double a, double b) {
        return nextDouble() * (b - a) + a;

    }

    /**
     * 几何分布
     * @param q
     * @return
     */
    public double geometricDistribution(double q) {
        return (Math.log(nextDouble()) / Math.log(q)) + 1.0;//以e为底
    }

    /**
     * 二项式分布
     * @param n
     * @param p
     * @return
     */
    public int binomialDistribution(int n, double p) {
        int i, f = 0;
        for (i = 0; i < n; i++) {
            if (nextDouble() < p) {
                f++;
            }
        }
        return f;
    }
//    public static void main(String[] args) {
//        RandomFrame frame=new RandomFrame();
//        frame.setVisible(true);
//        RandomCanvas canvas=new RandomCanvas();
//        canvas.setVisible(true);

//        double a=1.2;
//        System.out.println("%dabcd"+a);
//        Random r2 = new Random();
//        RandomAlgorithm random = new RandomAlgorithm();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(random.nextInt(10));
//            System.out.println(random.nextDouble());
//            random.saveDouble();
//        }
//        System.out.println("");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(random.hypodispersion(1.1, 1.8));
//            System.out.println(r2.nextInt(10));
//            System.out.println(random.previousDouble());
//        }
//    }
}
