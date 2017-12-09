package nili.colorbit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class ColorByteSender {



    public static void main(String[] args) throws Exception {
//        String path = "src/main/resources/test.txt";
        String path = "C:\\nili\\wallpapers\\简单\\14-120112115F4.jpg";

        generateBitColors(path);


//        System.out.println(get256Radix(256));
//        System.out.println(get256Radix(254));
//       System.out.println(get256Radix(257));
//        System.out.println(get256Radix(510));


    }

    public static void generateBitColors(String path) throws Exception {


        Color[] bitColors = getBitColors(path);

        ColorByteConfig config = new ColorByteConfig();
        ColorByteFrame frame = new ColorByteFrame(config);

        frame.setVisible(true);


        try {
            Thread.sleep(5000);
//            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        state = START_STATE;
        frame.setReady(true);

        int bytePerScreen = config.getBytePerScreen();
        int totalScreen = config.getTotalScreenNum(bitColors.length);
        System.out.println(bytePerScreen);
        System.out.println("Screen: " + totalScreen);
        int i = 0;
        for (; i + bytePerScreen < bitColors.length; i += bytePerScreen) {
            System.out.println("update");
            frame.updateBitColors(bitColors, i, bytePerScreen, totalScreen);
            Thread.sleep(1000);
        }

        if (i < bitColors.length) {
            System.out.println("update");
            frame.updateBitColors(bitColors, i, bitColors.length - i, totalScreen);
        }

        //debug
        int a = 0;
        for (Color c : bitColors) {
            if (a < 20) {
                System.out.println(c);
                ++a;
            } else {
                break;
            }
        }

//        System.out.println(frame.getBounds());
//        for (Component c : frame.getComponents()) {
//            if (c instanceof ColorByteCanvas) {
//                System.out.println(c);
//            }
//        }

        ColorByteCanvas canvas = (ColorByteCanvas) frame.getContentPane().getComponent(0);
        System.out.println(canvas.getLocationOnScreen());
//        System.out.println(canvas.getLocation());


//        System.out.println();


    }

    public static List<Integer> get256Radix(int n) {
        List<Integer> nums = new ArrayList<>();

        //get digits number
        int c = 0;
        int t = n;
        while (t != 0) {
            t /= 256;
            ++c;
        }
        System.out.println(c);
        //
        t = n;
        for (int i = 0; i < c - 1; ++i) {
            nums.add(t / 256);
            t %= 256;
        }
        // lowest
        nums.add(t % 256);
        return nums;
    }



//    public static int[] getRGB(int c, int[] buffer) {
//        if (buffer == null || buffer.length < 3) {
//            buffer = new int[3];
//        }
//        for (int i = 2; i >= 0; --i, c >>= 8) {
//            buffer[i] = c & 0xff;
//        }
//        return buffer;
//    }

    public static Color[] getBitColors(String path) throws Exception {
        File file = new File(path);
        System.out.println(file.exists());
        long len = file.length();
        System.out.println(len);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) len];
        fis.read(bytes);
        Color[] bitColors = new Color[(int) len];
        for (int i = 0; i < len; ++i) {
            bitColors[i] = ColorByte.getColor(bytes[i]);
        }
        return bitColors;

    }



    public static class ColorByteFrame extends JFrame {
        ColorByteCanvas canvas;
        ColorByteConfig config;


        //layout config
        int bitStartX = 16;
        int bitStartY = 61;

        public ColorByteFrame(ColorByteConfig config) {
            this.config = config;

//            setLayout(null);
//            getContentPane().setBounds(0, 0, config.getTotalFrameW(), config.getTotalFrameH());

            canvas = new ColorByteCanvas(config);
//            canvas.setBounds(bitStartX, bitStartY, canvas.getWidth(), canvas.getHeight());
            add(canvas);
//            add("Center", canvas);


//            canvas.setLocation(bitStartX, bitStartY);

//            setBackground(Color.WHITE);


//            setLocationByPlatform(false);
//            setLocationRelativeTo(null);
//            setBounds(0, 0, config.getTotalFrameW(), config.getTotalFrameH());
//            setLocation(0, 0);

            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//            addWindowListener(new WindowAdapter() {
//                public void windowClosing(WindowEvent we) {
//                    System.exit(0);
//                }
//            });

//            setState(INIT_STATE);
        }

        public void updateBitColors(Color[] bitColors, int offset, int len, int totalLen) {
            canvas.setBitColors(bitColors, offset, len, totalLen);
            canvas.repaint();
        }



//        private static final int INIT_STATE = 0;
//        private static final int START_STATE = 0;
//        private volatile int state;
//        public void setState(int state) {
//            this.state = state;
//        }

        private void setReady(boolean ready) {
            canvas.setReady(ready);
        }

    }

    public static class ColorByteCanvas extends Canvas {
        ColorByteConfig config;

        BufferedImage imageBuffer;

        Color[] bitColorBuffer;
        int bitColorOffset;
        int bitColorLen;
        int bitColorTotalLen;

        int screenCount = 0;



        public ColorByteCanvas(ColorByteConfig config) {
            this.config = config;
            imageBuffer = new BufferedImage(config.totalByteWidth, config.totalByteHeight,
                BufferedImage.TYPE_INT_RGB);


        }

        public void setBitColors(Color[] bitColors, int offset, int len, int totalLen) {
            this.bitColorBuffer = bitColors;
            this.bitColorOffset = offset;
            this.bitColorLen = len;
            this.bitColorTotalLen = totalLen;
            ++this.screenCount;
        }


        //Relative 
        //paint on image buffer
        //start from 0,0
        public void paintColorByte(Graphics g) {


            int oneBitWidth = config.colorByteWidth;
            int oneBitHeight = config.colorByteHeight;

            int maxbitXNum = config.maxByteX;
            int maxbitYNum = config.maxByteY;


            int c = bitColorOffset;
            int maxLen = c + bitColorLen;
            System.out.println("maxLen:" + maxLen);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, config.totalByteWidth, config.totalByteHeight);

            boolean flag = false;
            for (int y = 0, bitY = 0; bitY < maxbitYNum && !flag; ++bitY, y += oneBitHeight) {
                for (int x = 0, bitX = 0; bitX < maxbitXNum; ++bitX, x += oneBitWidth) {
                    if (c == maxLen) {
                        flag = true;
                        break;
                    }
//                    System.out.println(c);
                    g.setColor(bitColorBuffer[c]);
//                    System.out.println(bitColors[c]);
//                    System.out
//                        .println(String.format("%d,%d,%d,%d", x, y, oneBitWidth, oneBitHeight));
                    g.fillRect(x, y, oneBitWidth, oneBitHeight);
                    ++c;
                }
            }

        }

        @Override
        public void paint(Graphics g) {

            if (!ready) {
                return;
            }
            //buffer
            if (bitColorBuffer != null) {
                Graphics imageG = imageBuffer.getGraphics();
                paintColorByte(imageG);
                g.drawImage(imageBuffer, 0, 0, Color.WHITE, null);
//                            g.drawImage(imageBuffer, bitStartX, bitStartY, Color.WHITE, null);
            }

            //draw seq string
            g.setColor(Color.BLACK);
            int fontSize = 20;
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g.drawString(String.valueOf(screenCount), 50, config.totalByteHeight + fontSize);



            int d = config.colorByteWidth;
            //draw seq color
            List<Integer> nums = get256Radix(screenCount);
//            System.out.println("Seq: 0,");
            System.out.print(nums);
            for (int i = 0, w = 0; i < nums.size(); ++i, w += d) {
                Color c = ColorByte.getColor(nums.get(i));
                g.setColor(c);
                System.out.println(c);
                g.fillRect(w, config.totalByteHeight, d, d);
            }

            //total screen in next line
            nums = get256Radix(bitColorTotalLen);
//          System.out.println("Seq: 0,");
            System.out.print(nums);
            for (int i = 0, w = 0; i < nums.size(); ++i, w += d) {
                Color c = ColorByte.getColor(nums.get(i));
                g.setColor(c);
                System.out.println(c);
                g.fillRect(w, config.totalByteHeight + d, d, d);
            }

        }

        private volatile boolean ready = false;

        public void setReady(boolean ready) {
            this.ready = ready;
        }


    }



}
