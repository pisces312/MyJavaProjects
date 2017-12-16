package nili.colorbit;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

public class ColorByteReceiver implements Callable<byte[]> {

    public static void main(String[] args) throws Exception {
//        save();



        //SHAWD3134.ASIAPAC.NOM/?bpp=24&PerformanceFlags=0&geometry=2500x1600&domain=asiapac
        //IE 100% zoom
        //Taskbar width will affect
        // remote desktop 2500x1600
        //total w=2248
        //total h=1344
        ColorByteUIConfig config = new ColorByteUIConfig(2500, 1600, 8);
        config.screeShotX = 21;
        config.screeShotY = 263;
//        config.screeShotX = 22;
//        config.screeShotY = 226;
        ColorByteReceiver receiver = new ColorByteReceiver(config);
        receiver.interval = 400;

        byte[] bytes = receiver.getBytes();


        String output = "c:\\tmp\\" + System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(new File(output));
        fos.write(bytes);
        System.out.println("!!Finished!!");
    }

    public static void save() throws Exception {

        Robot robot = new Robot();

//        int x = 0;
//        //!! frame title height
//        int y = 45;
//        int width = 1000;
//        int height = 1000;


        int x = 22;
        //!! frame title height
        int y = 226;
        int width = 2248;
        int height = 1344;
        Rectangle screenShotRect = new Rectangle(x, y, width, height);
        BufferedImage image = robot.createScreenCapture(screenShotRect);
        //
        //
        try {
            ImageIO.write(image, "jpg",
                new File("c:\\tmp\\" + System.currentTimeMillis() + ".jpg"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


    public static void receive() throws Exception {

        ColorByteUIConfig config = new ColorByteUIConfig();
        ColorByteReceiver receiver = new ColorByteReceiver(config);
        receiver.interval = 500;

        byte[] bytes = receiver.getBytes();


        String output = "c:\\tmp\\" + System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(new File(output));
        fos.write(bytes);
//        new Thread(receiver).start();


//        receiveColorByte(config);

//        System.out.println(getNumOf256Radix(Arrays.asList(new Integer[] {1, 0})));
//        
//        System.out.println(getNumOf256Radix(Arrays.asList(new Integer[] {1, 1})));
//        System.out.println(getNumOf256Radix(Arrays.asList(new Integer[] {1, 254})));



    }



    ColorByteUIConfig config;

    Robot robot;

    volatile boolean running = true;

    int interval = 1000;

    Rectangle screenShotRect;


    public ColorByteReceiver(ColorByteUIConfig config) throws Exception {
        this.config = config;
        robot = new Robot();
        //!! frame title height

        screenShotRect =
            new Rectangle(config.screeShotX, config.screeShotY, config.frameW, config.frameH);
    }


    public byte[] getBytes() {
        int maxX = config.maxByteX;
        int maxY = config.maxByteY;
        int totalBytesPerScreen = maxX * maxY;

        ByteArrayOutputStream baos = new ByteArrayOutputStream(totalBytesPerScreen);



//        byte[] buffer = new byte[totalBytesPerScreen];

        //start from 1
        int seq = 1;

        int d = config.colorByteWidth;

        int totalBitWidth = config.totalByteWidth;
        int totalBitHeight = config.totalByteHeight;
        int scansize = totalBitWidth;

        int bottomLines = 2;

        int[] rgbArray = new int[totalBitWidth * (totalBitHeight + bottomLines * d)];



        while (running) {
            long start = System.currentTimeMillis();
            //
            BufferedImage image = robot.createScreenCapture(screenShotRect);
            //
            //
//            try {
//                ImageIO.write(image, "jpg", new File("c:\\tmp\\" + System.currentTimeMillis()+".jpg"));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            //

            //pixel   = rgbArray[offset + (y-startY)*scansize + (x-startX)]; 
            image.getRGB(0, 0, totalBitWidth, totalBitHeight + bottomLines * d, rgbArray, 0,
                scansize);

            // get seq

            Integer seqFromScreen = getNum(rgbArray, totalBitHeight);

            if (seqFromScreen != null) {
                System.out.println("Get seq " + seqFromScreen);
                if (seqFromScreen != seq) {
                    System.out.println("not synced (" + seqFromScreen + "!=" + seq + ")");
                } else {
                    System.out.println(seqFromScreen + " synced!");
                    for (int row = 0, rowPixels = d / 2; row < maxY; ++row, rowPixels += d) {
                        if (!running) {
                            break;
                        }
                        for (int col = 0, colPixels = d / 2; col < maxX; ++col, colPixels += d) {
                            int c = rgbArray[rowPixels * totalBitWidth + colPixels];
                            Color color = new Color(c);
                            // no need total...
                            if (color.equals(Color.WHITE)) {
                                running = false;
                                break;
                            }
//                            System.out.print("(" + color.getRed() + "," + color.getGreen() + ","
//                                + color.getBlue() + ") ");

                            baos.write(ColorByte.getByte(color));
                        }
//                        System.out.println();
                    }
                    ++seq;

                }
            } else {
                System.out.println("not synced (seq==null)");
            }


            // must handle sleep at last

            long cost = System.currentTimeMillis() - start;
            if (cost < interval) {
                try {
                    Thread.sleep(interval - cost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        return baos.toByteArray();

    }


    public Integer getNum(int[] rgbArray, int rowPixels) {
        boolean isSeqGot = false;
        List<Integer> nums = new ArrayList<>();
        int d = config.colorByteWidth;
        for (int colPixels = d / 2; colPixels < config.totalByteWidth; colPixels += d) {
            int c = rgbArray[rowPixels * config.totalByteWidth + colPixels];
            Color color = new Color(c);
            Integer n = ColorByte.getByte(color);
            if (n == null) {
//                System.out.println(color);
                if (nums.size() > 0) {
                    isSeqGot = true;
                }
                break;
            }
            nums.add(n);
        }
        if (isSeqGot) {
            return getNumOf256Radix(nums);
        }
        return null;
    }



    public static int getNumOf256Radix(List<Integer> nums) {
        int n = 0;
        for (int i = nums.size() - 1, d = 1; i >= 0; --i, d *= 256) {
            n += nums.get(i) * d;
        }
        return n;
    }

    @Override
    public byte[] call() throws Exception {

        return getBytes();
    }


}
