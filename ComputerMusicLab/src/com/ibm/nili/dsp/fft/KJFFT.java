package com.ibm.nili.dsp.fft;

/**
 * @author Kris
 */
public class KJFFT extends AbstractFFT {

    /**
     * @param The amount of the sample provided to the "calculate" method to use
     *            during
     *            FFT calculations.
     */
    public KJFFT(int exp) {
        sampleSize = 1 << exp;
        sampleSizeHalf = sampleSize >> 1;

        //////////////////////////
        //real part
        real = new float[sampleSize];
        //image part
        imag = new float[sampleSize];
        //////////////////////////
        result = new float[sampleSizeHalf];
        sampleSizeExp = exp;
        System.out.println("KJFFT sample size:" + sampleSize);
        prepareFFTTables();
    }

    private int bitrev(int j, int nu) {

        int j1 = j;
        int j2;
        int k = 0;

        for (int i = 1; i <= nu; i++) {
            j2 = j1 >> 1;
            k = (k << 1) + j1 - (j2 << 1);
            j1 = j2;
        }

        return k;

    }

    /**
     * The FFT result is sample size complex numbers, each for a frequency
     * 
     * �?�设原始信�?�的峰值为A，那么FFT的结果的�?个点（除了第一个点直�?分�?之外）的模值就是A的N/2�?
     * 
     * @param pSample The sample to compute FFT values on.
     * @return The results of the calculation, normalized between 0.0 and 1.0.
     */
    @Override
    protected void fftCalculation(float [] pSample) {
        //        int wAps = pSample.length / sampleSize;
        //  This affects the calculation accuracy, because
        //           is compresses the digital signal. Looks nice on
        //           the spectrum analyzer, as it chops off most of 
        //           sound we cannot hear anyway.
        ////////////////////////////////////////////
        //construct real and image parts
        for (int a = 0; a < sampleSize; ++a) {
            real[a] = pSample[a];
            imag[a] = 0.0f;
        }
        ////////////////////////////////////////////
        int n2 = sampleSizeHalf;
        float tr, ti, c, s;
        int k, kn2, x = 0;
        for (int l = 1; l <= sampleSizeExp; l++) {
            k = 0;
            while (k < sampleSize) {
                for (int i = 1; i <= n2; i++) {
                    // -- Tabled sin/cos
                    c = costable[x];
                    s = sintable[x];

                    kn2 = k + n2;

                    tr = real[kn2] * c + imag[kn2] * s;
                    ti = imag[kn2] * c - real[kn2] * s;

                    real[kn2] = real[k] - tr;
                    imag[kn2] = imag[k] - ti;
                    real[k] += tr;
                    imag[k] += ti;

                    k++;
                    x++;

                }

                k += n2;

            }

            n2 >>= 1;

        }

        int r;

        // -- Reorder output.
        for (k = 0; k < sampleSize; k++) {
            // -- Use tabled BR values.
            r = bitReverse[k];
            if (r > k) {
                tr = real[k];
                ti = imag[k];
                real[k] = real[r];
                imag[k] = imag[r];
                real[r] = tr;
                imag[r] = ti;
            }

        }

    }

    private void prepareFFTTables() {

        int n2 = sampleSizeHalf;
        int nu1 = sampleSizeExp - 1;

        // -- Allocate FFT SIN/COS tables.
        //////////////////////////
        sintable = new float[sampleSizeExp * n2];
        costable = new float[sampleSizeExp * n2];

        float p, arg;
        int k = 0, x = 0;

        double twoPIDevideSampleSize = 2 * Math.PI / sampleSize;
        // -- Prepare SIN/COS tables.
        for (int l = 1; l <= sampleSizeExp; l++) {

            while (k < sampleSize) {
                for (int i = 1; i <= n2; i++) {
                    p = bitrev(k >> nu1, sampleSizeExp);
                    arg = (float)(twoPIDevideSampleSize * p);
                    sintable[x] = (float)Math.sin(arg);
                    costable[x] = (float)Math.cos(arg);
                    k++;
                    x++;
                }

                k += n2;

            }

            k = 0;

            nu1--;
            n2 >>= 1;

        }

        // -- Prepare bitrev table.
        bitReverse = new int[sampleSize];

        for (k = 0; k < sampleSize; k++) {
            bitReverse[k] = bitrev(k, sampleSizeExp);
        }

    }
}
