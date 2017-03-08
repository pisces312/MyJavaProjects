package com.ibm.nili.test.jsyn; /* Put resulting class into tutorial package. */

import com.softsynth.jsyn.LineOut;
import com.softsynth.jsyn.SineOscillator;
import com.softsynth.jsyn.Synth;
import com.softsynth.jsyn.SynthException;

// must import event handling from AWT 1.1

/**
 * Start or stop an oscillator using JSyn.
 * AWT 1.1 version
 * 
 * @author (C) 1998 Phil Burk, All Rights Reserved <BR>
 *         Modifications for AWT 1.1 by Nick Didkovsky
 */

/* DO: Change TUT_SineWave to match the name of your class. Must match file name! 
<br>
	NOTE: this class implements ItemListener, so it can receive ItemEvents from its Checkbox.  
	Buttons and TextFields, for example, send ActionEvents, so if you want to use those, implement ActionListener.
*/
public class TUT_SineWave
{

    /* Can be run as either an application or as an applet. */
    public static void main(String args[])
    {
        /* DO:Declare Synthesis Objects here */
        //正弦信号发生
        SineOscillator sineOsc;
        //输出
        LineOut lineOut;
        double hz = 440;
        double outputRange = 0.4;
        try
        {
            //启动合成器引擎（初始化环境）
            Synth.startEngine(0);
            /* DO: Your setup code goes here. ******************/
            /* Create unit generators. */
            sineOsc = new SineOscillator();
            sineOsc.frequency.set(hz);
            //tell our oscillator to output numbers in the range of -0.4 to 0.4
            sineOsc.amplitude.set(outputRange);
            lineOut = new LineOut();

            /* Connect oscillator to LineOut so we can hear it. */
            //将输出连接到左右声道
            sineOsc.output.connect(0, lineOut.input, 0);
            sineOsc.output.connect(0, lineOut.input, 1);

            /* Start unit. */
            lineOut.start();
            /* *****************************************/
            sineOsc.start();

            // Sleep for awhile so we can hear the sound.
            Synth.sleepForTicks(2000);
            //            try {
            //                Thread.sleep(2000);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }

            sineOsc.stop();

        } catch (SynthException e) {
            //            SynthAlert.showError(this, e);
        } finally {
            Synth.stopEngine();
        }
    }

}