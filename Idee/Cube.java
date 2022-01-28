import java.awt.Color;

public class Cube {

    public static final int NBSOMMETS = 8;

    private Point3D[] sommets;
    private Color couleur;

    public Cube(int arete, Color newCouleur, Vecteur3D position) {
        //la couleur
        this.couleur = newCouleur;

        //création de tab
        this.sommets = new Point3D[NBSOMMETS];

        //accéder aux coordonnées de la position
        int xPosition = position.getX();
        int yPosition = position.getY();
        int zPosition = position.getZ();

        //création des sommets
        int i = 0;
        for (int x = xPosition; x <= xPosition+arete; x += arete) {
            for (int y = yPosition; y <= yPosition+arete; y += arete) {
                for (int z = zPosition; z <= zPosition+arete; z += arete) {
                    this.sommets[i] = new Point3D(x, y, z);
                    ++i;
                }
            }
        }
    }

    public Point3D[] getSommets() {
        return this.sommets;
    }

    public Color getColor() {
        return this.couleur;
    }

}