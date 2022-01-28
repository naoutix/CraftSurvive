package craftsurvive;

import java.io.Serializable;

public class Position implements Serializable{
    // composante x de la position
    private int x;

    // composante y de la position
    private int y;

    /** Constructeur
     * @param x composante x
     * @param y composante y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Obtenir la composante x de la position
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /** Obtenir la composante y de la position
     * @return
     */
    public int getY() {
        return this.y;
    }

    /** Ajouter ou soustraire à la composante x
     * @param x composante x de la position
     */
    public void incrX(int x) {
        this.x += x;
    }

    /** Ajouter ou soustraire à la compsoante y
     * @param y composante y de la position
     */
    public void incrY(int y) {
        this.y += y;
    }

    public String toString(){
        return this.x+":"+this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}