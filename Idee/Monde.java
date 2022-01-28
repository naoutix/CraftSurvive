import java.awt.Color;

public class Monde {

    public static final int LARGEUR = 480;
    public static final int LONGUEUR = 640;
    public static final int PROFONDEUR = 1000;

    private Cube objet;
    private Point3D perso;

    public Monde(Cube newCube, Point3D newPerso) {
        this.objet = newCube;
        this.perso = newPerso;
    }

    public int[][] coordSommetsProjete() {
        int[][] projete = new int[2][8];
        for (int i = 0; i < 8; ++i) {
            Point3D iSommet = this.objet.getSommets()[i];
            Vecteur3D vect = new Vecteur3D(iSommet ,this.perso);
            Point3D pointEcran = vect.getIntersection(iSommet, PROFONDEUR);
            projete[0][i] = LONGUEUR/2 + pointEcran.getX();
            projete[1][i] = LARGEUR/2 - pointEcran.getY();
        }
        return projete;
    }

}