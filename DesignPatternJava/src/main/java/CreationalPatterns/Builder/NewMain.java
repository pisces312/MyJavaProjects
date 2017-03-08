/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CreationalPatterns.Builder;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PersonBuilder builder=new PersonFatBuilder();
        builder=new PersonThinBuilder();
        PersonDirector director=new PersonDirector(builder);
        director.createPerson();
        int[][] array={
            {1,2,3,4},
            {1,2,3},
            {1,2}
        };
        print(array);
        int[][] array2=new int[3][4];
        System.arraycopy(array, 0, array2, 0, 3);
        print(array2);
    }
    public static void print(int[][] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
