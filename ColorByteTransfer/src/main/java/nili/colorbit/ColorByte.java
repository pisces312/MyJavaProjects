package nili.colorbit;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Cannot be white
 * 
 * white means end
 *
 */
public class ColorByte {

    //For sender
    private static final Color[] bitColors = new Color[256];

    //For receiver
    private static final Map<Color, Integer> colorBitMap = new HashMap<>(512);

    private static int colorDelta = 42;

    static {
        boolean flag = false;
        int c = 0;
        //adjust
        int d = colorDelta;
        for (int r = 0; r < 256; r += d) {
            if (flag) {
                break;
            }
            for (int g = 0; g < 256; g += d) {
                if (flag) {
                    break;
                }

                for (int b = 0; b < 256; b += d) {
                    bitColors[c] = new Color(r, g, b);
                    colorBitMap.put(bitColors[c], c);
                    ++c;
                    if (c == 256) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (c < 256) {
            System.err.println("less than 256");
            System.exit(1);
        }


        //4*8*8=256
//        int c = 0;
//        int d = 32;
//        for (int r = 0; r < 128; r += d) {
//            for (int g = 0; g < 256; g += d) {
//                for (int b = 0; b < 256; b += d) {
//                    bitColors[c] = new Color(r, g, b);
//                    colorBitMap.put(bitColors[c], c);
//                    ++c;
//                }
//            }
//        }
    }

    public static Color getColor(byte b) {
        int i = b & 0xff;
        return bitColors[i];
    }

    public static Color getColor(int b) {
        return bitColors[b];
    }

    public static int getCloest(int n, int mod, int e) {
        //r<=mod here
        int r = n % mod;
        if (mod - r <= e) {//41,83
            return mod - r + n;
        }
        if (r <= e) {//1->1,43->1,85->1
            return n - r;
        }
        return n;

    }

    public static Integer getByte(Color c) {
        int e = 6;
        // correction
        int r = getCloest(c.getRed(), colorDelta, e);
        int g = getCloest(c.getGreen(), colorDelta, e);
        int b = getCloest(c.getBlue(), colorDelta, e);

        Color newColor = new Color(r, g, b);
        Integer i = colorBitMap.get(newColor);
        if (i == null) {
            System.out.println("--orig color: " + c);
            System.out.println("--corrected color: " + newColor);
        }
        return i;

    }

    public static void printColors() {
        for (Color c : bitColors) {
            System.out.println(c + ":" + getByte(c));
        }
        colorBitMap.keySet().stream().sorted((c1, c2) -> {
            if (c1.getRed() == c2.getRed()) {
                if (c1.getGreen() == c2.getGreen()) {
                    return c1.getBlue() - c2.getBlue();
                }
                return c1.getGreen() - c2.getGreen();
            }
            return c1.getRed() - c2.getRed();
        }).forEach(c -> {
            System.out.println(c + ":" + colorBitMap.get(c));
        });

//        System.out.println(colorBitMap);

        for (int i = 0; i < 256; ++i) {
            System.out.println(getColor(i) + ":" + i);
        }

    }

    public static void main(String[] args) {
//        ColorBit cb = new ColorBit();
//        ColorByte.printColors();



        assert (getCloest(38, 42, 3) == 38);
        assert (getCloest(39, 42, 3) == 42);
        assert (getCloest(40, 42, 3) == 42);
        assert (getCloest(41, 42, 3) == 42);
        assert (getCloest(42, 42, 3) == 42);
        assert (getCloest(43, 42, 3) == 42);
        assert (getCloest(44, 42, 3) == 42);
        assert (getCloest(45, 42, 3) == 42);
        assert (getCloest(46, 42, 3) == 46);

        assert (getCloest(80, 42, 3) == 80);
        assert (getCloest(81, 42, 3) == 84);
        assert (getCloest(82, 42, 3) == 84);
        assert (getCloest(83, 42, 3) == 84);
        assert (getCloest(84, 42, 3) == 84);
        assert (getCloest(85, 42, 3) == 84);
        assert (getCloest(86, 42, 3) == 84);
        assert (getCloest(87, 42, 3) == 84);
        assert (getCloest(88, 42, 3) == 88);

    }

}
