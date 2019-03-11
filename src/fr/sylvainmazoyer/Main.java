package fr.sylvainmazoyer;

public class Main {

    public static void main(String[] args) {
        String imageName = new String();
        imageName = "YoupiOufDegrade.bmp";
        OpenBmp.openImage(imageName);
        System.out.println(OpenBmp.largeurImage+ " x " + OpenBmp.hauteurImage);
        CutRoi.calculRoiSize(OpenBmp.largeurImage,  OpenBmp.hauteurImage);

    }
}
