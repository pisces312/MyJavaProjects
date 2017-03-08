package StructuralPatterns.Proxy.sample2;

import javax.swing.*;

import java.awt.*;

public class ProxyDisplay extends JFrame {

    public ProxyDisplay() {
        super("Display proxied image");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p = new JPanel();
        getContentPane().add(p);
        p.setLayout(new BorderLayout());
        String curDir = System.getProperty("user.dir");
        String fileBar = System.getProperty("file.separator");
        String fileName = "elliott.jpg";
        System.out.println(curDir);
        ImageProxy image = new ImageProxy(curDir + fileBar + "src"+fileBar+fileName, 321, 271);
        //ImageProxy image = new ImageProxy("c:\\winnt\\web\\Wallpaper\\Windows 2000.jpg", 321, 271);
        p.add("Center", image);
        p.add("North", new Label("    "));
        p.add("West", new Label("  "));
        setSize(370, 350);
        setVisible(true);
    }

    static public void main(String[] argv) {
        new ProxyDisplay();
    }
}
