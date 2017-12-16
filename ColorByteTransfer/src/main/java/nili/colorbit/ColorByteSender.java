package nili.colorbit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ColorByteSender {



    public static void main(String[] args) throws Exception {
//        String path = "src/main/resources/test.txt";
        String path = "C:\\nili\\wallpapers\\简单\\14-120112115F4.jpg";

//        String path = "src/main/resources/lena/2.jpg";

//        send(path);
        ColorByteUIConfig config = new ColorByteUIConfig(2500,1600,6);
        config.sendInterval=1500;
        List<BufferedImage> imgList = generateImgFiles(path, config);
        ImageViewer viwer = new ImageViewer();
        viwer.setVisible(true);

        for (BufferedImage img : imgList) {
            long start = System.currentTimeMillis();
            viwer.update(img);
            long cost = System.currentTimeMillis() - start;
            if (config.sendInterval - cost > 0) {
                Thread.sleep(config.sendInterval - cost);
            }
//            try {
//                ImageIO.write(img, "png",
//                    new File("c:\\tmp\\" + System.currentTimeMillis() + ".png"));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
        }

//        System.out.println(get256Radix(256));
//        System.out.println(get256Radix(254));
//       System.out.println(get256Radix(257));
//        System.out.println(get256Radix(510));


    }



    public static BufferedImage generateImg(ColorBytePainter painter, Color[] bitColors, int offset,
        int len, int seq, int total) {
        BufferedImage img = painter.createEmptyImage();
        Graphics g = img.getGraphics();
        painter.paintColorByte(g, bitColors, offset, len);
        painter.paintFooter(g, seq, total);
        return img;
    }

    //without UI
    public static List<BufferedImage> generateImgFiles(String path, ColorByteConfig config)
        throws Exception {
        Color[] bitColors = getBitColors(path);
        ColorBytePainter painter = new ColorBytePainter(config);

        int bytePerScreen = config.getBytePerScreen();
        int totalScreen = config.getTotalScreenNum(bitColors.length);
        System.out.println("Byte/Screen: " + bytePerScreen);
        System.out.println("Total screen: " + totalScreen);

        List<BufferedImage> images = new ArrayList<>();

        int offset = 0;
        int seq = 1;
        for (; offset + bytePerScreen < bitColors.length; offset += bytePerScreen) {
            BufferedImage img =
                generateImg(painter, bitColors, offset, bytePerScreen, seq, totalScreen);
            images.add(img);
        }

        if (offset < bitColors.length) {
            BufferedImage img = generateImg(painter, bitColors, offset, bitColors.length - offset,
                seq, totalScreen);
            images.add(img);
        }
        return images;
    }


    public static void send(String path) throws Exception {


        Color[] bitColors = getBitColors(path);

        ColorByteUIConfig config = new ColorByteUIConfig();
        ColorByteFrame frame = new ColorByteFrame(config);


        frame.setVisible(true);



//        state = START_STATE;
//        frame.setReady(true);

        int bytePerScreen = config.getBytePerScreen();
        int totalScreen = config.getTotalScreenNum(bitColors.length);
        System.out.println(bytePerScreen);
        System.out.println("Screen: " + totalScreen);

        try {
            Thread.sleep(3000);
//            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        int i = 0;
        for (; i + bytePerScreen < bitColors.length; i += bytePerScreen) {
//            System.out.println("update");
            long start = System.currentTimeMillis();
            frame.updateBitColors(bitColors, i, bytePerScreen, totalScreen);
            long cost = System.currentTimeMillis() - start;
            if (cost < config.sendInterval) {
                Thread.sleep(config.sendInterval - cost);
            }
        }

        if (i < bitColors.length) {
//            System.out.println("update");
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
//        System.out.println(c);
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


    public static class ImageViewer extends JFrame {
        BufferedImage image;

        public ImageViewer() {
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        @Override
        public void paint(Graphics g) {
            if (image == null) {
                return;
            }
            Graphics g2 = getContentPane().getGraphics();
            g2.drawImage(image, 0, 0, Color.WHITE, null);

        }

        public void update(BufferedImage image) {
            this.image = image;
            repaint();
        }
    }


    public static class ColorByteFrame extends JFrame {
        ColorByteCanvas canvas;
        ColorByteUIConfig config;

//        private volatile boolean ready = false;
//
//        public void setReady(boolean ready) {
//            this.ready = ready;
//        }


        public ColorByteFrame(ColorByteUIConfig config) {
            this.config = config;
            canvas = new ColorByteCanvas(config);
            add(canvas);
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void updateBitColors(Color[] bitColors, int offset, int len, int totalLen) {
            canvas.setBitColors(bitColors, offset, len, totalLen);
//            if (ready) {
            canvas.repaint();
//            }
        }

    }

    public static class ColorByteCanvas extends Canvas {
        ColorByteUIConfig config;
        ColorBytePainter painter;

        BufferedImage imageBuffer;

        Color[] bitColorBuffer;
        int bitColorOffset;
        int bitColorLen;
        int totalScreen;

        int screenCount = 0;

        public ColorByteCanvas(ColorByteUIConfig config) {
            this.config = config;
            painter = new ColorBytePainter(config);
            imageBuffer = painter.createEmptyImage();

        }

        public void setBitColors(Color[] bitColors, int offset, int len, int totalLen) {
            this.bitColorBuffer = bitColors;
            this.bitColorOffset = offset;
            this.bitColorLen = len;
            this.totalScreen = totalLen;
            ++this.screenCount;
        }

        @Override
        public void paint(Graphics g) {
            if (bitColorBuffer == null) {
                return;
            }
            Graphics imageG = imageBuffer.getGraphics();
            painter.paintColorByte(imageG, bitColorBuffer, bitColorOffset, bitColorLen);
            painter.paintFooter(imageG, screenCount, totalScreen);

            //draw seq/total string
            imageG.setColor(Color.BLACK);
            imageG.setFont(config.font);
            imageG.drawString(screenCount + " / " + totalScreen, 42,
                config.totalByteHeight + config.fontSize);

            g.drawImage(imageBuffer, 0, 0, Color.WHITE, null);
        }
    }


    //paint logic
    static class ColorBytePainter {
        ColorByteConfig config;

        public ColorBytePainter(ColorByteConfig config) {
            this.config = config;
        }


        public BufferedImage createEmptyImage() {
            return new BufferedImage(config.totalByteWidth,
                config.totalByteHeight + 2 * config.colorByteHeight, BufferedImage.TYPE_INT_RGB);
        }


        //Relative 
        //paint on image buffer
        //start from 0,0
        public void paintColorByte(Graphics g, Color[] bitColorBuffer, int bitColorOffset,
            int bitColorLen) {
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
                    g.setColor(bitColorBuffer[c]);
                    g.fillRect(x, y, oneBitWidth, oneBitHeight);
                    ++c;
                }
            }

        }

        public void paintFooter(Graphics g, int seq, int total) {
            paintFooter(g, 0, config.totalByteHeight, seq, total);
        }

        public void paintFooter(Graphics g, final int x, final int y, int seq, int total) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, config.totalByteWidth, 2 * config.colorByteHeight);



            int d = config.colorByteWidth;
            //draw seq color
            List<Integer> nums = get256Radix(seq);
//            System.out.println("Seq: 0,");
//            System.out.print(nums);
            for (int i = 0, w = x; i < nums.size(); ++i, w += d) {
                Color c = ColorByte.getColor(nums.get(i));
                g.setColor(c);
//                System.out.println(c);
                g.fillRect(w, y, d, d);
            }

            //total screen in next line
            nums = get256Radix(total);
//          System.out.println("Seq: 0,");
//            System.out.print(nums);
            for (int i = 0, w = 0; i < nums.size(); ++i, w += d) {
                Color c = ColorByte.getColor(nums.get(i));
                g.setColor(c);
//                System.out.println(c);
                g.fillRect(w, y + d, d, d);
            }
        }
    }



}
