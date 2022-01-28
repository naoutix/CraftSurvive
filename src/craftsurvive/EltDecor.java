/* Classe définissant un element du décor */
package craftsurvive;

import java.io.Serializable;

public class EltDecor implements Serializable{

    /* Type du Decor */
    private TypeEltDecor type;

    /* Taille en x et en y */
    private int[] taille = new int[2];

    /* Position sur la map de l'objet */
    private Position position;

    /* Objet dans ce décor */
    private Objet[] objets;

    /* l'objet est il cassable */
    private boolean breakable;
    
    /* Sante du decor*/
    private double sante;

    /**
     * Construit un element de decor
     * @param type le type de decor que l'on veut
     * @param position la position de l'element
     */
    public EltDecor (TypeEltDecor type, Position position) {
        this.type = type;
        this.position = position;
        switch (type) {
            case Arbre:
                this.taille[0] = 1;
                this.taille[1] = 2;
                this.breakable = true;
                this.sante = 100;
                break;
            case Rocher:
                this.taille[0] = 2;
                this.taille[1] = 2;
                this.breakable = true;
                this.sante = 100;
                break;
            case Caillou:
                this.taille[0] = 1;
                this.taille[1] = 1;
                this.breakable = true;
                this.sante = 100;
                break;
            case Fleur:
                this.taille[0] = 1;
                this.taille[1] = 1;
                this.breakable = true;
                this.sante = 100;
                break;
            case Buisson:
                this.taille[0] = 2;
                this.taille[1] = 1;
                this.breakable = true;
                this.sante = 100;
                break;
            case Coffre:
                this.taille[0] = 1;
                this.taille[1] = 1;
                this.breakable = false;
                this.sante = 100;
                break;
        }
    }

    /**
     * @return taille du decor 
     */
    public int[] getTaille() {
        return this.taille;
    }

    /**
     * @return le type de decor
     */
    public TypeEltDecor getType() {
        return this.type;
    }

    /**
     * @return la position du decor
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @return si le decor est cassable ou pas 
     */
    public boolean getBreakable() {
        return this.breakable;
    }

    /**
     * @return les objets present dans ce decor
     */
    public Objet[] getObjets() {
        return this.objets;
    }

    /**
     * @return la sante de ce decor
     */
    public double getSante() {
        return this.sante;
    }

    /**
     * change la sante du decor
     * @param chg
     */
    public void chgSante(double chg) {
        this.sante += chg;
    }
}