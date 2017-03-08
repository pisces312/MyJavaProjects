/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Observer.sample1;

/**
 *  客户端调用：天气预报
 *
 *    @author    zjun
 *    @version    1.0  create  on  2006-5-18  9:57:19
 */
public class WeatherService {

    /**  */
    /**
     *    @param    args
     */
    public static void main(String[] args) {
        Earth earth = new Earth();

        Satellite satellite = new Satellite();
        //    发射气象卫星
        earth.addObserver(satellite);

        System.out.println("  天气预报：  ");

        System.out.println("  ------------  ");
        earth.setWeather("  台风‘珍珠’逼近  ");
        earth.setWeather("  大到暴雨  ");
        earth.setWeather("  天气炎热  ");
    }
}

