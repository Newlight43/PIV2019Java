package fr.sylvainmazoyer;

public class FFT2D {


    static Complex[][] twoDfft(Complex[][] inputData, boolean directFFT) {

        int width = inputData.length;
        int height = inputData[0].length;

        double theta;
        double sign;
        double[][] realOut = new double[width][height];
        double[][] imagOut = new double[width][height];
        Complex[][] outputData = new Complex[width][height];
        Complex piTimes2 = new Complex(2* Math.PI, 0);

        if (directFFT) {
            sign = 1.D;
        } else {
            sign=-1.D;
        }

        // Two outer loops iterate on output data.

        for (int yWave = 0; yWave < height; yWave++)

        {

            for (int xWave = 0; xWave < width; xWave++)

            {

                // Two inner loops iterate on input data.

                for (int ySpace = 0; ySpace < height; ySpace++)

                {

                    for (int xSpace = 0; xSpace < width; xSpace++)

                    {

                        // Compute real, imag.
                        theta=2* Math.PI* ((1.0 * xWave * xSpace / width) + (1.0* yWave * ySpace / height));
                        realOut[xWave][yWave] += (inputData[xSpace][ySpace].re() * Math.cos(theta) + sign * inputData[xSpace][ySpace].im() * Math.sin(theta))/ Math.sqrt(width * height);
                        imagOut[xWave][yWave] += (inputData[xSpace][ySpace].im() * Math.cos(theta) - sign * inputData[xSpace][ySpace].re() * Math.sin(theta))/ Math.sqrt(width * height);

                    }
                }

                Complex compTemp = new Complex(realOut[xWave][yWave], imagOut[xWave][yWave]);

                if (!directFFT){
                    outputData[xWave][yWave]=compTemp.divides(piTimes2);
                } else {
                    outputData[xWave][yWave] = compTemp;
                }
            }
        }
        return outputData;
    }

    // compute the circular convolution of x and y
    public static Complex[][] cconvolve(Complex[][] x, Complex[][] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length && x[0].length != y[0].length) {
            throw new IllegalArgumentException("Dimensions don't agree");
        }

        int n = x.length;
        int m = x[0].length;

        // compute FFT of each sequence
        Complex[][] a = twoDfft(x, true);
        Complex[][] b = twoDfft(x, true);;

        // point-wise multiply
        Complex[][] c = new Complex[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                c[i][j] = a[i][j].times(b[i][j]);
            }
        }

        // compute inverse FFT
        return twoDfft(c, false);
    }


    public static Complex[][] convolve(Complex[][] x, Complex[][] y) {
        Complex ZERO = new Complex(0, 0);

        int n = x.length;
        int m = x[0].length;

        if (x.length != y.length && x[0].length != y[0].length) {
            throw new IllegalArgumentException("Dimensions of complex matrix to convolve don't agree");
        }

        Complex[][] a = new Complex[2*n][2*m];
        for (int i = 0; i < 2*n; i++) {
            for (int j = 0; j < 2*m; j++) {
                a[i][j] = ZERO;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; i++) {
                a[i][j] = x[i][j];
            }
        }


        Complex[][] b =  new Complex[2*n][2*m];
        for (int i = 0; i < 2*n; i++) {
            for (int j = 0; j < 2*m; j++) {
                b[i][j] = ZERO;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; i++) {
                b[i][j] = y[i][j];
            }
        }
        return cconvolve(a, b);
    }

}