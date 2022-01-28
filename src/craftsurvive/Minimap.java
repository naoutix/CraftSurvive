package craftsurvive;

import java.util.Random;
import java.io.Serializable;
import java.lang.Math;

public class Minimap implements Serializable{

    /* Dimension d'une Minimap */
    static final int[] dimensions = {15, 15};

    /* Nombre de décor maximum */
    static final int nbDecorMax = 15;

    /* Liste des decors */
    private Decor decor;

    /* Map contenant tous les decors (y compris les cases vides)*/ 
    private TableauCollision map;

    /** Créer une minimap vide
     * 
     */
    public Minimap() {
        this.decor = new Decor();
        this.map = new TableauCollision();
        generer();
    }

    /** Générer des éléments de décor sur la minimap
     * 
     */
    public void generer() {
        int x, y, type;
        Position position;
        EltDecor Elt;
        Random r = new Random();

        int compteur = 1;
        while (compteur <= Math.min(Minimap.nbDecorMax, (dimensions[0]-1)*(dimensions[1]))){
            x = r.nextInt(dimensions[0]-1)+1;
            y = r.nextInt(dimensions[1]-1)+1;
            position = new Position(x,y);
            type = r.nextInt(TypeEltDecor.taille);
            Elt = new EltDecor(TypeEltDecor.values()[type], position);

            // On ajoute le decor a la map
            try {
                int num = this.decor.addEltDecor(Elt);
                this.map.addEltDecor(Elt,num);
            } catch (CollisionException c ){
                // si exception de collision on n'ajoute pas le decor
            }
            compteur ++;
        }

    }

    /** Savoir si une case est libre
     * 
     * @param Position position
     */
    public boolean estLibre(Position position) {
        return this.map.getNumero(position) == 0;
    }

    /** Retourner un élément de la minimap
     * 
     * @param Position position
     */
    public EltDecor getEltDecor(Position position) {
        return this.decor.getEltDecor(this.map.getNumero(position));
    }

    /** Ajouter un élément à la minimap
     * 
     * @param typeEltDecor type
     * @param Position position
     */
    public void addEltDecor(TypeEltDecor type, Position position) throws CollisionException {
        EltDecor elt = new EltDecor(type, position);
        this.map.addEltDecor(elt, this.decor.addEltDecor(elt));
    }

    /** Retirer un élément de la minimap
     * 
     * @param Position position
     */
    public void cutEltDecor(Position position) {
        int numero = this.map.getNumero(position);
        if (numero != 0) {
            this.map.cutNumero(numero);
            this.decor.cutEltDecor(numero);
        }
    }
    
}