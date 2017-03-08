package com.ibm.nili.dsp.fft;

/**
 * reuse the input buffer!!!
 * actually the fft result is half of input
 * 
 * @author nili66china
 * 
 */
public class FFT extends AbstractFFT {

    /**
     * fftNLog<13 for 8000 hz sample rate
     * 
     * @param fftNLog
     * @throws Exception
     */
    public FFT(int fftNLog) {
        System.out.println(fftNLog);
        sampleSizeExp = fftNLog;
        sampleSize = 1 << sampleSizeExp;
        sampleSizeHalf = sampleSize >> 1;
        //org
        MINY = getMinValve(sampleSize);
        real = new float[sampleSize];
        imag = new float[sampleSize];
        result = new float[sampleSizeHalf];
        sintable = new float[sampleSize >> 1];
        costable = new float[sampleSize >> 1];
        bitReverse = new int[sampleSize];

        int i, j, k, reve;
        for (i = 0; i < sampleSize; i++) {
            k = i;
            for (j = 0, reve = 0; j != sampleSizeExp; j++) {
                reve <<= 1;
                reve |= (k & 1);
                k >>>= 1;
            }
            bitReverse[i] = reve;
        }

        double theta, dt = 2 * 3.14159265358979323846 / sampleSize;
        for (i = sampleSizeHalf - 1; i > 0; i--) {
            theta = i * dt;
            costable[i] = (float)Math.cos(theta);
            sintable[i] = (float)Math.sin(theta);
        }
    }

    /**
     * return the square of module
     * 
     * @param realIO
     */
    @Override
    protected void fftCalculation(float [] realIO) {
        int i, j, k, ir, j0 = 1, idx = sampleSizeExp - 1;
        float cosv, sinv, tmpr, tmpi;

        for (i = 0; i != sampleSize; i++) {
            real[i] = realIO[bitReverse[i]];
            imag[i] = 0;
        }

        for (i = sampleSizeExp; i != 0; i--) {
            for (j = 0; j != j0; j++) {
                cosv = costable[j << idx];
                sinv = sintable[j << idx];
                for (k = j; k < sampleSize; k += j0 << 1) {
                    ir = k + j0;
                    tmpr = cosv * real[ir] - sinv * imag[ir];
                    tmpi = cosv * imag[ir] + sinv * real[ir];
                    real[ir] = real[k] - tmpr;
                    imag[ir] = imag[k] - tmpi;
                    real[k] += tmpr;
                    imag[k] += tmpi;
                }
            }
            j0 <<= 1;
            idx--;
        }

    }

}
