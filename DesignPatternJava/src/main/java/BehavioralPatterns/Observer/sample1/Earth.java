/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Observer.sample1;

import java.util.Observable;

/**  */
/**
 *  被观察对象：地球
 *
 *    @author    zjun
 *    @version    1.0  create  on  2006-5-18  9:42:45
 */
public class Earth extends Observable {

    private String weather = "  晴朗  ";

    /**  */
    /**
     *    @return    Returns  the  weather.
     */
    public String getWeather() {
        return weather;
    }

    /**  */
    /**
     *    @param    weather

     *                        The  weather  to  set.
     */
    public void setWeather(String weather) {
        this.weather = weather;
        //    设置变化点
        setChanged();
        notifyObservers(weather);
    }
}

