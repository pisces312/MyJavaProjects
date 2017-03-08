package StructuralPatterns.Proxy.sample2;

/**
 *  A Image
 */
import java.awt.MediaTracker;

public class ImageA implements Graphic {
    public ImageA(String _fileName) {
        fileName = _fileName;
        
    }
    public void Draw() {
        

    }
    public MediaTracker tracker;
    private int height, width;
    private String fileName;
    
}