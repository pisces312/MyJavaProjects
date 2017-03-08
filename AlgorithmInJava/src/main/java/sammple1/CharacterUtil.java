package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *关于ASCII等字符的一些运算技巧和工具
 *
 * @author pisces
 */
public class CharacterUtil {
//????

    public static boolean isChineseCharacter(char c) {

        int count = 100;
        for (int i = 0; i < 65536; i++) {

            System.out.print((char) i);
            if (i % count == 0) {
                System.out.println();
            }
        }

        /**
        \u4E00-\u9FA5 
        \uF900-\uFA2D
         * */
        System.out.println(Integer.toBinaryString("大".toCharArray()[0]));
        System.out.println(c);
        System.out.println(Integer.toBinaryString(c));
        System.out.println(Integer.toHexString(c));
        return (c & 0x88) == 0x88;
    }

    /**
     *快速大小写转换,b7b6b5b4 b3b2b1b0
     * 通过改变b5即可改变大小写
     */
    public static char changeCharCase(char c) {
        return (char) (c ^ 0x20);
//        return ' ';
    }

    public static boolean isCharNumber(char n) {
        return ((n & 0xf0) == 0x30) && ((n & 0xf) <= 0x9);
    }
}
