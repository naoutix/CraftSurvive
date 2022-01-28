import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
 
public class Panneau extends JPanel {

    public void paintComponent(Graphics g){
        //créer le monde avec notre perso et un cube
        Vecteur3D positionCube = new Vecteur3D(0, 0, 500);
        Cube cube = new Cube(50, Color.red, positionCube);
        Monde monde = new Monde(cube, new Point3D(50, 100, 0));

        //déterminer les sommets projetés
        int[][] coordProjete = monde.coordSommetsProjete();

/**
        System.out.println("Debut coord");
        for (int i = 0; i < 8; ++i) {
            System.out.println(coordProjete[0][i]);
            System.out.println(coordProjete[1][i]);
            System.out.println(" ");
        }
*/

        int[] xFace1 = {coordProjete[0][0], coordProjete[0][1], coordProjete[0][3], coordProjete[0][2]};
        int[] yFace1 = {coordProjete[1][0], coordProjete[1][1], coordProjete[1][3], coordProjete[1][2]};
        int[] xFace2 = {coordProjete[0][4], coordProjete[0][5], coordProjete[0][7], coordProjete[0][6]};
        int[] yFace2 = {coordProjete[1][4], coordProjete[1][5], coordProjete[1][7], coordProjete[1][6]};
        g.setColor(cube.getColor());
        g.drawPolygon(xFace1, yFace1, 4);
        g.drawPolygon(xFace2, yFace2, 4);
        g.drawLine(coordProjete[0][0], coordProjete[1][0], coordProjete[0][4], coordProjete[1][4]);
        g.drawLine(coordProjete[0][2], coordProjete[1][2], coordProjete[0][6], coordProjete[1][6]);
        g.drawLine(coordProjete[0][1], coordProjete[1][1], coordProjete[0][5], coordProjete[1][5]);
        g.drawLine(coordProjete[0][3], coordProjete[1][3], coordProjete[0][7], coordProjete[1][7]);
        System.out.println("c'est fini");
    }

}