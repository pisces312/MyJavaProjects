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

    private static final Color[] bitColors = new Color[256];

    private static final Map<Color, Integer> colorBitMap = new HashMap<>(512);


    static {


        //
        boolean flag = false;
        int c = 0;
        //adjust
        int d = 42;
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
        if(c<256) {
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

    public static Integer getByte(Color c) {
        return colorBitMap.get(c);
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
        ColorByte.printColors();

    }

}
