package fr.sylvainmazoyer;

public class CutRoi {

    static int roiSize = 64;

    static public int[] calculRoiSize(int largeurImage, int hauteurImage) {
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
