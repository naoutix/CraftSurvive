package craftsurvive;

import java.io.Serializable;

public class Inventaire implements Serializable{
	
	static final int TailleMax=25; // La taille max

    // taille courrante
    private int tailleCour;

    // inventaire
    private Objet[] inventaire;

    public Inventaire() {
        this.tailleCour = 0;
        this.inventaire = new Objet[TailleMax];
        Objet epeeBois = new Objet(50,0,NomObjet.epeeBois,false);
        Objet armure = new Objet(0,30,NomObjet.armor,false);
        Objet epee = new Objet(100,0,NomObjet.hache,false);
        Objet bouclier = new Objet(0,70,NomObjet.bouclier,false);
        this.ajouterObjet(epee);
        this.ajouterObjet(epeeBois);
        this.ajouterObjet(armure);
        this.ajouterObjet(bouclier);
    }
    
    /** Permet d'ajouter un objet à l'inventaire
     * @param obj objet à ajouter
     */
    public void ajouterObjet(Objet obj) {
    	if (!this.estPlein()) {
	    	this.inventaire[this.tailleCour]=obj;
	    	this.tailleCour++;
    	}
    }
    
    /** Permet de savoir si l'inventaire est plein
     * @return si l'inventaire est plein
     */
    public boolean estPlein() {
    	return (this.tailleCour > TailleMax-1);
    }
    
    /** Donne l'objet d'indice ind
     * @param ind
     * @return l'objet d'indice ind
     */
    public Objet getObjet(int ind) {
    	return this.inventaire[ind];
    }
    
    /** Informe sur l'appartenance d'objet dans l'inventaire
     * @param obj à regarder
     * @return si il se trouve dans l'inventaire
     */
    public boolean appartient(Objet obj) {
    	boolean estPresent = false;
    	for (int i = 0; i < this.tailleCour; i++) {
    		estPresent = this.inventaire[i].compare(obj);
    		if (estPresent) {
    			return estPresent;
    		}
    	}
    	return false;
    }
    
    /** Supprime un objet de l'inventaire
     * @param obj à supprimer
     */
    public void supprimerObjet(Objet obj) {
    	if (this.appartient(obj)){
            int indice = this.getPosition(obj);
    		for (int i = indice; i < this.tailleCour-1; i++) {
                this.inventaire[i] = this.inventaire[i+1];
            }
            this.tailleCour--;
    	}
    }

    /**
     * @return tailleCour la taille courante de l'inventaire
     */
    public int getTailleCourante() {
    	return this.tailleCour;
    }
    
   
    /**
     * Permet de savoir si la position d'indice n est occupee
     * @param n indice 
     * @return true si occupee false sinon
     */
    public boolean estOccupe(int n) {
    	return (this.tailleCour > n);
    }

    /**
     * Obtenir la position d'un certain objet 
     * @param obj l'objet dont on veut la position
     * @return int pos la position dans l'inventaire
     */
    public int getPosition(Objet obj) {
        int pos=0;
    	while (!obj.compare(this.inventaire[pos])) {
    		pos++;
    	}
    	return pos;
    }
}