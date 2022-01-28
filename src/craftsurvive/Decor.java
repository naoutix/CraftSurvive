/* Inventaire des elements de decor de la minimap */
package craftsurvive;

import java.io.Serializable;

public class Decor implements Serializable{

    /* Tableau comportant les Decors */
    private EltDecor tab[];

    /** Permet d'initialiser un inventaire d'elements de decor'
     */
    public Decor(){
        this.tab = new EltDecor[Minimap.nbDecorMax]; 
    }
 

    /** Supprimer le décor associer a un numero
     * 
     * @param int num_decor
     */
    public void cutEltDecor(int num_decor){
    	this.tab[num_decor - 1] = null;
    }

    /** Ajouter un element de decor a la liste
    *
    * @param Position position
    * @param typeEltDecor
    * @return int le numero dans la liste auquelle il a été affecté
    */
    public int addEltDecor(EltDecor elt) throws CollisionException {
        int i = 0;
        // Parcourir les cases jusqu'a trouver une case vide 
        while (this.tab[i] != null && i < Minimap.nbDecorMax){
            i++;
        }
        if (i < Minimap.nbDecorMax) {
            this.tab[i] = elt;
            return i+1;
        }
        /* i = nbDecorMax */
        else {
            throw new CollisionException("Le nombre max d'éléments à été atteint");
        }
    }

   /** Renvoie un element de decor
    *
    * @param int num_decor
    * @return EltDecor
    */
    public EltDecor getEltDecor(int num_decor) {
        if (num_decor <= 0) {
            return null;
        }
        else {
            return this.tab[num_decor - 1];
        }
    }
}