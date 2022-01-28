package craftsurvive;

public abstract class Personnage {
    // santé
    protected double sante;

    // dégats
    protected double degats;

    // position
    protected Position position;

    /** Enumeration */
    public static enum Etat {ATTACK,BLOCK,IDLE,DEAD};

    /** Etat courant du joueur*/
    protected Etat etat_courant;

    // nom
    protected String nom;

    // direction dans laquelle le personnage regarde
    protected String direction;

    /** Constructeur
     * @param sante santé d'un personnage
     * @param degats dégâts d'un personnage
     * @param xp expérience d'un personnage
     * @param arme arme d'un joueur
     * @param armure armure d'un joueur
     * @param nom nom d'un joueur
     */
    public Personnage(double sante, double degats, String nom, int x, int y) {
        this.sante = sante;
        this.degats = degats;
        this.position = new Position(x, y);
        this.nom = nom;
        this.etat_courant = Etat.IDLE;
    }

/*                 SANTE ET DEGATS                      */

    /** Obtenir la santé d'un personnage
     * @return sante
     */
    public double getSante() {
        return this.sante;
    }

    /** Obtenir les dégats d'un personnage
     * @return degats
     */
    public double getDegats() {
        return this.degats;
    }

    /** Gagner ou perdre de la santé
     * @param chg
     */
    public void chgSante(double chg) {
        this.sante += chg;
        if (this.sante <= 0){	
            this.etat_courant = Etat.DEAD;
        }
    }


/*              POSITION                 */

    /** Obtenir la position d'un personnage
     * @return position
     */
    public Position getPosition() {
        return this.position;
    }

/*                       NOM                     */

    /** Obtenir le nom d'un joueur
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /** Définir le nom d'un joueur
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

/* Action */

    /**
     * @return etat_courant l'etat du joueur 
     */
    public Etat getEtat(){
        return this.etat_courant;
    }

    /**
     * change l'etat du joueur 
     * @param newEtat le nouvel etat
     */
    public void chgtEtat(Etat newEtat){
        this.etat_courant = newEtat;
    }

/*                DIRECTION              */

    /**
     * Obtenir la direction courante du joueur 
     * @return String direction
     */
    public String getDirection() {
    	if (this.direction != null) { 
    		return this.direction;
    	}
    	return "haut";
        
    }

    /** 
     * Changer la direction du joueur
     * @param direction
     */
    public void setDirection(String direction)  {
        this.direction = direction;
    }

}