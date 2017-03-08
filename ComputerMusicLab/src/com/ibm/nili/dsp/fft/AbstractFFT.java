package com.ibm.nili.dsp.fft;

/**
 * the FFT sample size is 2^N, so it may be different from the actually sample
 * size
 * 
 * @author nili66china
 * 
 */
public abstract class AbstractFFT {
    protected float [] real;
    protected float [] imag;
    protected float [] result;

    protected float [] sintable;
    protected float [] costable;
    protected int []   bitReverse;
    protected int      sampleSizeExp;
    protected int      sampleSizeHalf;
    protected int      sampleSize;
    /**
     * MYINY equals Spectrum.Y0,Spectrum.logY0
     * 
     * eliminate the small factors
     */
    protected float    MINY;

    public int getFFTResultSize() {
        return sampleSizeHalf;
    }

    /**
     * �?�设FFT之�?��?点n用�?数a+bi表示，那么这个�?数的模就是An=根�?�a*a+b*b，相�?就是Pn=atan2(b,a)。
     * 
     * @param pSample
     * @return
     */
    public float [] calculateModuleSquared(float [] pSample) {
        fftCalculation(pSample);
        /*
         * 输出模的平方:
         * for(i = 1; i <= j; i++)
         *  realIO[i-1] = real[i] * real[i] +  imag[i] * imag[i];
         * 
         * 如果FFT�?�用于频谱显示,�?�以"淘汰"幅值较�?的而�?少浮点乘法�?算. MINY的值
         * 和Spectrum.Y0,Spectrum.logY0对应.
         */
        float sinv = MINY;
        float cosv = -MINY;
        float tmpr, tmpi;
        for (int i = sampleSizeHalf; i >= 0; i--) {
            tmpr = real[i];
            tmpi = imag[i];
            if (tmpr > cosv && tmpr < sinv && tmpi > cosv && tmpi < sinv)
                result[i - 1] = 0;
            else {
                result[i - 1] = tmpr * tmpr + tmpi * tmpi;
            }
        }
        return pSample;
    }

    /**
     * �?�设原始信�?�的峰值为A，那么FFT的结果的�?个点（除了第一个点直�?分�?之外）的模值就是A的N/2�?
     * 
     * @param pSample
     * @return
     */
    public float [] calculateMagnitude(float [] pSample) {
        fftCalculation(pSample);
        float sinv = MINY;
        float cosv = -MINY;
        float tmpr, tmpi;
        for (int i = sampleSizeHalf; i > 0; i--) {
            tmpr = real[i];
            tmpi = imag[i];
            if (tmpr > cosv && tmpr < sinv && tmpi > cosv && tmpi < sinv)
                result[i - 1] = 0;
            else {
                result[i - 1] = (float)(2 * Math.sqrt(tmpr * tmpr + tmpi * tmpi) / (double)sampleSize);
            }
        }
        result[0] = Math.abs(real[0]) / sampleSize;
        return result;
    }

    protected abstract void fftCalculation(float [] pSample);

    public static float getMinValve(int sampleSize) {
        return (float)((sampleSize << 2) * Math.sqrt(2));
    }

    /**
     * �?点n所表示的频率为：Fn=(n-1)*Fs/N
     * order starts with 0
     * 
     * @param order
     * @param sampleRate
     * @return
     */

    public static int getFrequencyOfSample(int order, int sampleRate, int sampleSize) {
        return (int)(order * sampleRate / (double)sampleSize);
    }

    /**
     * 根�?�以上的结果，就�?�以计算出n点（n≠1，且n<=N/2）对应的信�?�的表达�?为：An/(N/2)*cos(2*pi*Fn*t+Pn)
     * ，�?�2*An/
     * N*cos(2*pi*Fn*t+Pn)。对于n=1点的信�?�，是直�?分�?，幅度�?�为A1/N。由于FFT结果的对称性，通常我们�?�使用�
     * ?�?�部分的结果，
     * �?��?于采样频率一�?�的结果。
     * 
     * @return
     */
    public String getSignalExp(int order, int Fn) {
        if (order == 0) {
            return real[0] + "/" + sampleSize;
        }
        double A = 2 * (Math.sqrt(real[order] * real[order] + imag[order] * imag[order])) / (double)sampleSize;
        //Pn=atan2(b,a)
        return A + "*cos(" + (Fn << 1) + "pi*t+" + Math.atan2(imag[order], real[order]) + ")";
    }

    /**
     * 如果�?�??高频率分辨力，则必须增加采样点数，也�?�采样时间。频率分辨率和采样时间是倒数关系。
     * 
     * @param sampleRate
     * @param sampleSize
     * @return
     */
    public static double getFreqResolution(int sampleRate, int sampleSize) {
        return sampleRate / (double)sampleSize;

    }

    public int getSampleSizeForFFT() {
        return sampleSize;
    }

    public void setValve(float valve) {
        MINY = valve;
    }
}
