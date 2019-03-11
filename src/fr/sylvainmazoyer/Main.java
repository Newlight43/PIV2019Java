package fr.sylvainmazoyer;

public class Main {

    public static void main(String[] args) {
        String imageName = new String();
        imageName = "YoupiOufDegrade.bmp";
        TraitementImage imgTraitement = new TraitementImage("YoupiOufDegrade.bmp");
        imgTraitement.openImage();
        System.out.println(imgTraitement.largeurImage+ " x " + imgTraitement.hauteurImage);
     //   CutRoi.calculRoiSize(OpenBmp.largeurImage,  OpenBmp.hauteurImage);

    }
}
