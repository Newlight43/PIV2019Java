package fr.sylvainmazoyer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class TraitementImage {

    static int largeurImage;
    static int hauteurImage;
    static int roiSize = 64;
    String imageName = new String();

    public TraitementImage(String imageName) {
        this.imageName = imageName;
    }


    public int[][] openImage() {

        String pathBmpFile = new String();
        pathBmpFile = System.getProperty("user.dir") +"\\" + imageName;

        File bmpFile = new File(pathBmpFile);
        try {
            BufferedImage image = read(bmpFile);
            largeurImage = image.getWidth();
            hauteurImage = image.getHeight();
            int[][] greyImage = new int[largeurImage][hauteurImage];

            Color couleur;
            for(int colonne = 0; colonne < largeurImage; colonne++){
                for(int ligne = 0; ligne < hauteurImage; ligne++){
                    couleur = new Color(image.getRGB(colonne, ligne), false);
                    greyImage[colonne][ligne] = (couleur.getRed() + couleur.getBlue() + couleur.getGreen())/3;
                }
            }
            System.out.println(largeurImage+ " x " + hauteurImage);
            System.out.println("Vous avez bien chargé l'image " + pathBmpFile );
            return greyImage;

        } catch (IOException e) {
            e.printStackTrace();
            int[][] greyImage = new int[1][1];
            greyImage[0][0]=-1;
            return greyImage;
        }
    }

    public int[] calculRoiSize(int largeurImage, int hauteurImage) {
        int[] nbRoi = new int[2];
        int[] imgReste = new int[2];

        nbRoi[0] = largeurImage/roiSize;
        nbRoi[1] = hauteurImage/roiSize;

        imgReste[0] = largeurImage%roiSize;
        imgReste[1] =  hauteurImage%roiSize;

        if (imgReste[0]!=0 || imgReste[1]!=0){
            System.out.println("Les dimensions de l'image ne sont pas des multiples de 64 px, l'image a été tronquée");
        }

        System.out.println("L'image comporte "+ nbRoi[0] +" par "+ nbRoi[0] +"ROIs de taille 64 px");
        return nbRoi;

    }

}
