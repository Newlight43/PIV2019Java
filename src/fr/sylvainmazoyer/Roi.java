package fr.sylvainmazoyer;

public class Roi {

    protected int roiSize;
    protected int nbIRoi;
    protected int nbJRoi;

    protected int[][][][] roiIJXY = new int[nbIRoi][nbJRoi][roiSize][roiSize];

    public int[][][][] getRoiIJXY() {
        return roiIJXY;
    }

    public Roi(int nbIRoi, int nbJRoi, int roiSize) {

        this.roiSize = roiSize;
        this.nbIRoi = nbIRoi;
        this.nbJRoi = nbJRoi;

    }

}
