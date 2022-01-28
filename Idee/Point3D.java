import java.awt.Color;

public class Point3D {

    private int x;
    private int y;
    private int z;

    public Point3D(int newX, int newY, int newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
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

    public Vecteur3D soustraction(Point3D point) {
        int newX = this.getX()-point.getX();
        int newY = this.getY()-point.getY();
        int newZ = this.getZ()-point.getZ();
        return new Vecteur3D(newX, newY, newZ);
    }

}