package fr.sylvainmazoyer;

public class CorrCalc {

    static int roiLag = Main.roiSize/Main.lagRatio;

    protected int[] nbRoi = new int[2];
    protected int[][][][] roi1IJXY;
    protected int[][][][] roi2IJXY;

    public CorrCalc(int[] nbRoi, int[][][][] roi1IJXY, int[][][][] roi2IJXY) {
        this.nbRoi = nbRoi;
        this.roi1IJXY = roi1IJXY;
        this.roi2IJXY = roi2IJXY;
    }

    protected int[][] extractRoi(int[][][][] roiIJXY, int roiI, int roiJ){
        int xsize = roiIJXY[0][0].length;
        int ysize = roiIJXY[0][0][0].length;

        int[][] roiXY = new int[xsize][ysize];

        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {

                roiXY[x][y]=roiIJXY[roiI][roiJ][x][y];

            }
        }
        return roiXY;
    }

    protected int[][] invertSensRoi(int[][] roiXY){
        int xsize = roiXY.length;
        int ysize = roiXY[0].length;
        int[][] invertSensRoiXY = new int[xsize][ysize];
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {

                invertSensRoiXY[x][y]=roiXY[xsize-x-1][ysize-y-1];

            }
        }
        return invertSensRoiXY;
    }

    static Complex[][] img2ComplexImg(int[][] imgInt){
        int width = imgInt.length;
        int height = imgInt[0].length;

        Complex[][] complexImg =  new Complex [width][height];

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                Complex pixel = new Complex(imgInt[x][y], 0);
                complexImg[x][y] = pixel;
            }
        }
        return complexImg;
    }


    public double[][] corrRoiCalc(int i, int j){

        double[][] corrRoi= new double[Main.roiSize][Main.roiSize];
        Complex[][] cov12;
        Complex[][] var1;
        Complex[][] var2;
        
        int[][] roi1XY;
        int[][] roi2XY;
        int[][] roi1XYMinus;
        int[][] roi2XYMinus;
        Complex[][] roi1C;
        Complex[][] roi2C;
        Complex[][] roi1MC;
        Complex[][] roi2MC;

        Complex[][] tFroi1C;
        Complex[][] tFroi2C;
        Complex[][] tFroi1MC;
        Complex[][] tFroi2MC;


        // extractions des rois des deux images découpées
        roi1XY=extractRoi(roi1IJXY, i, j);
        roi2XY=extractRoi(roi2IJXY, i, j);

        //Inversion du sens de parcours des roi (les deux pour les calcul de variances)
        roi1XYMinus=invertSensRoi(roi1XY);
        roi2XYMinus=invertSensRoi(roi2XY);

        //Conversion des rois int en Complex
        roi1C=img2ComplexImg(roi1XY);
        roi2C=img2ComplexImg(roi2XY);
        roi1MC=img2ComplexImg(roi1XYMinus);
        roi2MC=img2ComplexImg(roi2XYMinus);

        //calcul des 4 FFT
        tFroi1C=FFT2D.twoDfft(roi1C, true);
        tFroi2C=FFT2D.twoDfft(roi2C, true);
        tFroi1MC=FFT2D.twoDfft(roi1MC, true);
        tFroi2MC=FFT2D.twoDfft(roi2MC, true);

        //calcul de la covariance
        Complex[][] multiplyTf12 = new Complex[Main.roiSize][Main.roiSize];
        for (int x = 0 ; x < Main.roiSize ; x++){
            for (int y = 0 ; y < Main.roiSize ; y++){

                multiplyTf12[x][y] = tFroi1C[x][y].times(tFroi2MC[x][y]);

            }
        }
        cov12=FFT2D.twoDfft(multiplyTf12,false);

        //calcul de la variance de roi1
        Complex[][] multiplyTf11 = new Complex[Main.roiSize][Main.roiSize];
        for (int x = 0 ; x < Main.roiSize ; x++){
            for (int y = 0 ; y < Main.roiSize ; y++){

                multiplyTf12[x][y] = tFroi1C[x][y].times(tFroi1MC[x][y]);

            }
        }
        var1=FFT2D.twoDfft(multiplyTf11,false);

        //calcul de la variance de roi2
        Complex[][] multiplyTf22 = new Complex[Main.roiSize][Main.roiSize];
        for (int x = 0 ; x < Main.roiSize ; x++){
            for (int y = 0 ; y < Main.roiSize ; y++){

                multiplyTf22[x][y] = tFroi2C[x][y].times(tFroi2MC[x][y]);

            }
        }
        var2=FFT2D.twoDfft(multiplyTf22,false);

        //calcul de fonction de cross-correlation
        for (int x = 0 ; x < Main.roiSize ; x++){
            for (int y = 0 ; y < Main.roiSize ; y++){

                corrRoi[x][y]=cov12[x][y].re()/Math.sqrt(var1[x][y].re()*var2[x][y].re());

            }
        }

        return corrRoi;
    }

}
