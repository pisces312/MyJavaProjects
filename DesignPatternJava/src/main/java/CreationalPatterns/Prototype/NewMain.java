/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.Prototype;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * ???实际是new更快
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Resume r1 = null;
        int size=2;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            r1 = new Resume("nili", "male", "23", "njit");
        }
        long end = System.currentTimeMillis();
        System.out.println("new " + (end - begin) + "ms");
        begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            Resume r2 = (Resume) r1.clone();
        }
        end = System.currentTimeMillis();
        System.out.println("clone:" + (end - begin) + "ms");
//        r1.print();
//        r2.print();

    }
}
