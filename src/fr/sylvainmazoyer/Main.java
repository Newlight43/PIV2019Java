package fr.sylvainmazoyer;

public class Main {

    static int roiSize = 64;
    static int lagRatio = 1;

    public static void main(String[] args) {
        String imageName = new String();
        imageName = "YoupiOufDegrade.bmp";
        int[][][][] roiIJXY;

        TraitementImage imgTraitement = new TraitementImage(imageName);
        imgTraitement.openImage();
        System.out.println(imgTraitement.largeurImage+ " x " + imgTraitement.hauteurImage);
        imgTraitement.calculRoiSize(imgTraitement.largeurImage,  imgTraitement.hauteurImage);
        roiIJXY=imgTraitement.createRoi();
        System.out.println("Pixel : " + roiIJXY[0][3][32][32]);
    }
}
