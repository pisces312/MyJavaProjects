package config;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class Config {
    static FileInputStream configFile;

    public static void initLogConfig() {
        synchronized (Config.class) {
            if (configFile == null) {
                try {
                    configFile = new FileInputStream("./logging.properties");
                    LogManager.getLogManager().readConfiguration(configFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
