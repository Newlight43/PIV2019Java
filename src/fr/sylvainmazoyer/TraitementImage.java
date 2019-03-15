package fr.sylvainmazoyer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class TraitementImage {

    static int largeurImage;
    static int hauteurImage;
    static int roiSize = Main.roiSize;
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

        nbRoi[0] = hauteurImage/roiSize;
        nbRoi[1] = largeurImage/roiSize;

        imgReste[0] =  hauteurImage%roiSize;
        imgReste[1] = largeurImage%roiSize;

        if (imgReste[0]!=0 || imgReste[1]!=0){
            System.out.println("Les dimensions de l'image ne sont pas des multiples de 64 px, l'image a été tronquée");
        }

        System.out.println("L'image comporte "+ nbRoi[0] +" par "+ nbRoi[1] +" ROIs de taille 64 px");
        return nbRoi;
    }

    public int[][][][] createRoi(){

        int[][] image;
        int[] nbRoi = new int[2];
        int[][][][] roiIJXY;

        image=this.openImage();
        nbRoi=calculRoiSize(largeurImage, hauteurImage);
        roiIJXY = new int[nbRoi[0]][nbRoi[1]][roiSize][roiSize];

        for (int i =0 ; i<nbRoi[0] ; i++){
            for (int j =0 ; j<nbRoi[1] ; j++){
                for (int x =0 ; x<64 ; x++){
                    for (int y =0 ; y<64 ; y++){

                        roiIJXY[i][j][x][y] = image[j*64 + x][i*64 + y];

                    }
                }
            }

        }

        return roiIJXY;
    }


}
