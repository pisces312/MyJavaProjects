/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BehavioralPatterns.Observer.sample1;

/**
 *  �ͻ��˵��ã�����Ԥ��
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
        //    ������������
        earth.addObserver(satellite);

        System.out.println("  ����Ԥ����  ");

        System.out.println("  ------------  ");
        earth.setWeather("  ̨�确���顯�ƽ�  ");
        earth.setWeather("  �󵽱���  ");
        earth.setWeather("  ��������  ");
    }
}

