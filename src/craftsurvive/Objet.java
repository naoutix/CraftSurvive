package craftsurvive;

import java.io.Serializable;

public class Objet implements Serializable{
    // bonus de sante
    private double sante;

    // bonus de dégats
    private double degats;
    
    // Nom de l'objet
    private NomObjet nom;
    
    //Est-il equipé?
    private boolean equipe;
    
    //identifiant
    private int id;

    /** Constructeur
     * @param sante bonus de santé
     * @param degats bonus de dégâts
     * @param nom nom de l'objet
     * @param equipe boolean vrai si l'objet est equipe
     * @param id identifiant si plusieur fois le meme objet dans l'inventaire, initialisé à 1
     */
    public Objet(double degats, double sante, NomObjet nom, boolean equipe) {
        this.sante = sante;
        this.degats = degats;
        this.nom = nom;
        this.equipe = equipe;
        this.id = Partie.idObj;
        ++ Partie.idObj;
    }

    /** Obtenir la sante bonus qu'apporte un objet
     * @return bonus de santé
     */
    public double getSante() {
    	if (this == null) {
    		return 0;
    	}
    	return this.sante;
    }
    /** Obtenir les dégâts bonus qu'apporte un objet
     * @return bonus de dégâts
     */
    public double getDegats() {
        return this.degats;
    }
    
    /** Savoir si un objet est equipé
     * @return Si l'objet est equipe
     */
    public boolean estEquipe() {
        return this.equipe;
    }
    
    /** Donne le nom de l'objet
     * @return le nom de l'objet
     */
    public NomObjet getNom() {
    	return this.nom;
    }
    
    /** Donne l'id de l'objet
     * @return l'identifiant de l'objet
     */
    public int getId() {
    	return this.id;
    }
    
    /** Change le statut equipe de l'objet
     */
    public void desequipe() {
    	this.equipe= false;
    }
    
    /** Change le statut equipe de l'objet
     */
    public void equipe() {
    	this.equipe = true;
    }
    /** Donne le caractère léthal ou non de l'objet
     * @return vrai si c'est léthal
     */
    public boolean estLethal() {
    	return this.degats > 0;
    }
    
    /** Compare si deux objets sont egaux
     * @return si les objets sont égaux
     */
    public boolean compare(Objet ob) {
    	return this.id == ob.id;
    }
    
}