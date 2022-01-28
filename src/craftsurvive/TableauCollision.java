package craftsurvive;

import java.io.Serializable;

public class TableauCollision implements Serializable{

    /* Tableau contenant les identifiants */
    private int[][] tableau = new int[Minimap.dimensions[0]][Minimap.dimensions[1]];

    /** Créer un tableau collision vide
     * 
     */
    public TableauCollision () {
        for (int i = 0; i < Minimap.dimensions[0]; i++) {
            for (int j = 0; j < Minimap.dimensions[1]; j++) {
                tableau[i][j] = 0;
            }
        }
    }

    /** Retourner le numéro contenu dans une case
     * 
     * @param Position position
     */
    public int getNumero(Position position) {
        return this.tableau[position.getX()][position.getY()];
    }

    /** Ajouter un élément de décor
     * 
     * @param EltDecor eltDecor
     * @param int numero
     */
    public void addEltDecor(EltDecor eltDecor, int numero) throws CollisionException {
        Position position = eltDecor.getPosition(); // Position de l'objet
        int[] taille = eltDecor.getTaille();        // taille de l'objet
        if (position.getX() + taille[0] >= Minimap.dimensions[0] || position.getY() + taille[1] >= Minimap.dimensions[1] || position.getX() <= 0 || position.getY() <= 0) {
            throw new CollisionException("Element hors de la minimap");
        }

        // On verifie qu'il n'y a pas deja des elements
        for (int i = position.getX(); i < position.getX() + taille[0]; i++) {
            for (int j = position.getY(); j < position.getY() + taille[1]; j++) {
                if (tableau[i][j] != 0) {
                    throw new CollisionException("Element superposé à un autre élément");
                }
            }
        }
        // On ecrit le numero
        for (int i = position.getX(); i < position.getX() + taille[0]; i++) {
            for (int j = position.getY(); j < position.getY() + taille[1]; j++) {
                tableau[i][j] = numero;
            }
        }
    }

    // TO-DO faire un truc plus propre que de recherche les decors (Mettre la liste des decor ici)
    /** Enlever un numéro du tableau
     * 
     * @param int numero
     */
    public void cutNumero(int numero) {
        for (int i = 0; i < Minimap.dimensions[0]; i++) {
            for (int j = 0; j < Minimap.dimensions[1]; j++) {
                if (this.tableau[i][j] == numero) {
                    this.tableau[i][j] = 0;
                }
            }
        }
    }
    
}