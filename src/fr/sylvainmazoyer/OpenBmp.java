package fr.sylvainmazoyer;

import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;


public class OpenBmp {

    static int largeurImage;
    static int hauteurImage;

    public static int[][] openImage(String imageName) {

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
}
