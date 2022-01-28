import java.awt.Color;
import java.lang.Math;

public class Vecteur3D {

    private int x;
    private int y;
    private int z;

    public Vecteur3D(int newX, int newY, int newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    public Vecteur3D(Point3D point1, Point3D point2) {
        this.x = point1.getX()-point2.getX();
        this.y = point1.getY()-point2.getY();
        this.z = point1.getZ()-point2.getZ();
    }

    public Point3D getIntersection(Point3D origineVect, int z) {
        double zDiff = z - origineVect.getZ();
        double coeff = zDiff / this.z;
        int xNew = (int) Math.round(origineVect.getX() + coeff*this.x);
        int yNew = (int) Math.round(origineVect.getY() + coeff*this.y);
        return new Point3D(xNew, yNew, z);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

}