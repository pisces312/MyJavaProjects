package kj.dsp;

/**
 * @author Kris
 */
public class KJFFT {

    private float [] xre;
    private float [] xim;
    private float [] mag;

    private float [] fftSin;
    private float [] fftCos;
    private int []   fftBr;

    private int      sampleSize, sampleSizeHalf, nu;

    /**
     * @param The amount of the sample provided to the "calculate" method to use
     *            during
     *            FFT calculations.
     */
    public KJFFT(int pSampleSize) {

        sampleSize = pSampleSize;
        sampleSizeHalf = sampleSize >> 1;

        //////////////////////////
        //TODO has doubled
        xre = new float[sampleSize];
        xim = new float[sampleSize];
        //        xre = new float[sampleSize << 1];
        //        xim = new float[sampleSize << 1];
        //////////////////////////
        mag = new float[sampleSizeHalf];

        //TODO
        //        nu = getExpOf2Ceiling(ss);
        nu = getExpOf2(sampleSize);
        //        nu = (int)(Math.log(ss) / Math.log(2));
        System.out.println("nu:" + nu);
        //        
        prepareFFTTables();

    }

    public static int getExpOf2(int n) {
        if (n == 0) {
            return 1;
        }

        int c = 0;
        while ((n = (n >> 1)) != 0) {
            ++c;
        }
        return c;
    }

    public static int getExpOf2Ceiling(int n) {
        if (n == 0) {
            return 1;
        }

        int c = 0;
        if (n != 1 && (n & (n - 1)) != 0) {
            ++c;
        }
        while ((n = (n >> 1)) != 0) {
            ++c;
        }

        //        if (n == 1 || (n & (n - 1)) == 0) {
        //
        //        }
        //        do {
        //            n = (n >> 1);
        //            ++c;
        //        } while (n != 0);
        return c;
    }

    public int getResultSize() {
        return mag.length;
    }

    public static void main(String [] args) {
        System.out.println(getExpOf2Ceiling(8));
        System.out.println(getExpOf2Ceiling(7));
        System.out.println(getExpOf2Ceiling(6));
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
     * @param pSample The sample to compute FFT values on.
     * @return The results of the calculation, normalized between 0.0 and 1.0.
     */
    public float [] calculate(float [] pSample) {

        int n2 = sampleSizeHalf;
        //        int wAps = pSample.length / sampleSize;

        // -- FIXME: This affects the calculation accuracy, because
        //           is compresses the digital signal. Looks nice on
        //           the spectrum analyzer, as it chops off most of 
        //           sound we cannot hear anyway.
        for (int a = 0, b = 0; a < pSample.length; a += 1, b++) {
            xre[b] = pSample[a];
            xim[b] = 0.0f;
        }

        float tr, ti, c, s;
        int k, kn2, x = 0;

        for (int l = 1; l <= nu; l++) {

            k = 0;

            while (k < sampleSize) {

                for (int i = 1; i <= n2; i++) {

                    // -- Tabled sin/cos
                    c = fftCos[x];
                    s = fftSin[x];

                    kn2 = k + n2;

                    //TODO
                    //                    if (kn2 >= xre.length) {
                    //                        break;
                    //                    }

                    tr = xre[kn2] * c + xim[kn2] * s;
                    ti = xim[kn2] * c - xre[kn2] * s;

                    xre[kn2] = xre[k] - tr;
                    xim[kn2] = xim[k] - ti;
                    xre[k] += tr;
                    xim[k] += ti;

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
            r = fftBr[k];

            if (r > k) {

                tr = xre[k];
                ti = xim[k];

                xre[k] = xre[r];
                xim[k] = xim[r];
                xre[r] = tr;
                xim[r] = ti;

            }

        }

        // -- Calculate magnitude.
        mag[0] = (float)(Math.sqrt(xre[0] * xre[0] + xim[0] * xim[0])) / sampleSize;

        for (int i = 1; i < sampleSizeHalf; i++) {
            mag[i] = 2 * (float)(Math.sqrt(xre[i] * xre[i] + xim[i] * xim[i])) / sampleSize;
        }

        return mag;

    }

    private void prepareFFTTables() {

        int n2 = sampleSizeHalf;
        int nu1 = nu - 1;

        // -- Allocate FFT SIN/COS tables.
        //////////////////////////
        //TODO has doubled
        fftSin = new float[nu * n2];
        fftCos = new float[nu * n2];
        //        fftSin = new float[nu * n2 * 2];
        //        fftCos = new float[nu * n2 * 2];
        //////////////////////////
        System.out.println("n2=" + n2);
        System.out.println("nu * n2=" + fftSin.length);
        System.out.println("ss=" + sampleSize);

        float p, arg;
        int k = 0, x = 0;

        double twoPIDevideSampleSize = 2 * Math.PI / sampleSize;
        // -- Prepare SIN/COS tables.
        for (int l = 1; l <= nu; l++) {

            while (k < sampleSize) {

                for (int i = 1; i <= n2; i++) {
                    //TODO
                    //                    if (x >= fftSin.length) {
                    //                        break;
                    //                    }
                    p = bitrev(k >> nu1, nu);

                    arg = (float)(twoPIDevideSampleSize * p);

                    fftSin[x] = (float)Math.sin(arg);
                    fftCos[x] = (float)Math.cos(arg);

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
        fftBr = new int[sampleSize];

        for (k = 0; k < sampleSize; k++) {
            fftBr[k] = bitrev(k, nu);
        }

    }

}
