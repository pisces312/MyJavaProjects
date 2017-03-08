/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *∫√”√
 * @author pisces312
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
public class FindInJar {
    public String className;

    public ArrayList jarFiles = new ArrayList();

    public FindInJar() {
    }

    public FindInJar(String className) {
        this.className = className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List findClass(String dir, boolean recurse) {
        searchDir(dir, recurse);
        return this.jarFiles;
    }

    protected void searchDir(String dir, boolean recurse) {
        try {
            File d = new File(dir);
            if (!d.isDirectory()) {
                return;
            }
            File[] files = d.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (recurse && files[i].isDirectory()) {
                    searchDir(files[i].getAbsolutePath(), true);
                } else {
                    String filename = files[i].getAbsolutePath();
                    if (filename.endsWith(".jar")||filename.endsWith(".zip")) {
                        ZipFile zip = new ZipFile(filename);
                        Enumeration entries = zip.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            String thisClassName = getClassName(entry);
                            if (thisClassName.equals(this.className) || thisClassName.equals(this.className + ".class")) {
                                this.jarFiles.add(filename);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getFilenames() {
        return this.jarFiles;
    }

    protected String getClassName(ZipEntry entry) {
        StringBuilder name = new StringBuilder(entry.getName().replace('/','.'));
        return name.toString();
    }

    public static void main(String args[]) {
//        String className="org.geotools.data.FileDataStoreFinder";
//        String srcPath="E:\\javalib\\geotools-2.7-RC1\\";

                String className="org.geotools.geometry.DirectPosition2D";
        String srcPath="E:\\javalib\\geotools-2.7-RC1\\";
        if(args.length>2){
            className=args[1];
            srcPath=args[2];
        }
        //
        //
        //
        FindInJar findInJar = new FindInJar(className);
        List jarFiles = findInJar.findClass(srcPath, true);
        if (jarFiles.isEmpty()) {
            System.out.println("Not Found");
        } else {
            for (int i = 0; i < jarFiles.size(); i++) {
                System.out.println(jarFiles.get(i));
            }
        }
    }
}
