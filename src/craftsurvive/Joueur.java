package craftsurvive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import craftsurvive.Personnage.Etat;

public class Joueur extends Personnage {

	/** xp */
	private XP xp;

	/** arme */
	private Objet arme;

	/** armure */
	private Objet armure;

	/** coefficient d'evolution */
	private double coef;

	/** inventaire */
    private Inventaire inventaire;
    
    /** sante de depart pour evolution */
    private double santeDepart;

    /** Constuire un joueur 
     * 
     * @param sante Sante du joueur
     * @param degats Degat du joueur
     * @param nom   nom du joueur
     * @param x     position du joueur 
     * @param y     position du joueur
     */
	public Joueur(double sante, double degats, String nom, int x, int y) {
		super(sante, degats, nom, x, y);
		this.santeDepart = sante;
		this.xp = new XP(1, 0);
		this.coef = 1.1;
        this.inventaire = new Inventaire();
        
        File sauvegardeJeu = new File(Environnement.NOM_DOSSIER_SAUV + "/sauvegarde_joueur.ser");
        if (sauvegardeJeu.exists()) {
        	this.chargerAttributs();
        }
	}

    /** Deplacer un personnage 
     * @param direction direction de deplacement
     * gauche droite haut bas
     * @param partie la partie dans laquelle le joueur joue 
     * origine en haut a gauche de l ecran
     */
	public boolean deplacer(Partie jeu, String direction) {
        this.setDirection(direction);
        boolean chgtTab = false;
		Position newPos = this.getNextPos(direction);
        if (newPos.getX() < 0){
            jeu.getEnv().getNextTableau("gauche");
            newPos.setX(Minimap.dimensions[0]-1);
            chgtTab = true;
        }
        if (newPos.getX() > Minimap.dimensions[0]-1){
            jeu.getEnv().getNextTableau("droite");
            newPos.setX(0);
            chgtTab = true;
        }

        if (newPos.getY() > Minimap.dimensions[1]-1){
            jeu.getEnv().getNextTableau("haut");
            newPos.setY(0);
            chgtTab = true;
        }
        if (newPos.getY() < 0){
            jeu.getEnv().getNextTableau("bas");
            newPos.setY(Minimap.dimensions[0]-1);
            chgtTab = true;
        }
        if (chgtTab) {
            this.position = newPos;
        } else {
        	// On verifie que il n'y a rien sur la case sur laquelle il veut aller 
            if (!jeu.getHorde().isEnnemiAt(newPos) && jeu.getEnv().getMinimap().estLibre(newPos)){
                this.position = newPos;
            }
        }
        return chgtTab;
    }
    
	/**
	 * Permet de savoir la prochaine postition ou il arrivera si il continue tout droit
	 * @param direction orientation du joueur 
	 */
    public Position getNextPos(String direction){
        Position newPos = new Position(this.position.getX(),this.position.getY());
        switch (direction) {
            case "gauche":
                newPos.incrX(-1);
                break;
            case "droite":
                newPos.incrX(+1);
                break;
            case "haut":
                newPos.incrY(+1);
                break;
            case "bas":
                newPos.incrY(-1);
                break;
            default:
                //gerer l'erreur
        }
        return newPos;
    }

	/** Obtenir l experience d'un personnage
     * @return xp
     */
    public XP getXP() {
        return this.xp;
    }

    /** Evoluer un joueur met a jour la sante les degats ainsi que le niveau du joueur 
     * @param xpPrec l xp avant le changement de niveau
     * */
    public void evolutionJoueur(double xpPrec) {
        // gain de sante
        this.sante = this.santeDepart * this.coef + this.getSanteArmure();
        this.santeDepart = this.santeDepart * this.coef + this.getSanteArmure();
        // augmenter les degats
        this.degats = this.degats * this.coef + this.getDegatsArmes();
        // passage de niveau
        this.xp.evolution(xpPrec, this.coef);
    }
    
    /**
     * Augmenter XP et changer de niveau
     * @param gain l xp que l on veut ajouter au joueur 
     */
    public void augmenterXP(int gain) {
    	this.xp.gainXP(gain);
    	if (this.xp.getXP() >= this.xp.getXPMax()) {
    		evolutionJoueur(this.xp.getXP());
    	}
    }

    /*                    ARME ET ARMURE                       */

    /** Obtenir l'arme d'un joueur
     * @return arme
     */
    public Objet getArme() {
        return this.arme;
    }

    /** Obtenir l'armure d'un joueur
     * @return armure
     */
    public Objet getArmure() {
        return this.armure;
    }

    /**
     * Obtenir si il y en a une d'equipee la sante de l'armure
     * @return 0 si il n'y en a pas la sante de l'armure si il y en a une 
     */
    private double getSanteArmure() { 
    	if (this.armure != null) {
    		return this.armure.getSante();
    	}
    	return 0;
    }
    
    public double getSanteDepart() {
        return this.santeDepart;
    }
    /**
     * Obtenir si il y en a une d'equipe les degats de l'arme
     * @return 0 si il n'y en a pas les de l'armure si il y en a une 
     */
    private double getDegatsArmes() { 
    	if (this.arme != null) {
    		return this.arme.getDegats();
    	}
    	return 0;
    }
    
    
    /** Equiper un objet
     * @param objet celui qu'on equipe
     * @param type arme ou armure
     */
    public void equiper(Objet objet, String type) {
        if (this.inventaire.appartient(objet)) {
            // equiper l'arme ou l'armure
            if (type.equals("arme")) {
            	if (this.arme != null) {
            		this.arme.desequipe();
            		this.degats -= this.arme.getDegats();
            	}
            	this.arme = objet;
                this.arme.equipe();
                
            }
            else if (type.equals("armure")) {
            	if (this.armure != null) {
            		this.armure.desequipe();
            		this.sante -= this.armure.getSante();
            		this.santeDepart -= this.armure.getSante();
            	}
                this.armure = objet;
                this.armure.equipe();
                
            }
            this.degats += objet.getDegats();
            this.sante += objet.getSante();
            this.santeDepart += objet.getSante();
        }
     }
    
    /** Desequiper une arme
     * @param objet objet a desequiper
     * @param type arme ou armure
     */
    public void desequiper(Objet objet, String type) {
        // desequiper l'arme ou l'armure
        if (type.equals("arme") && objet.compare(this.arme)) {
        	this.arme.desequipe();
            this.arme = null;
            this.degats -= objet.getDegats();
        }
        else if (type.equals("armure") && objet.compare(this.armure)) {
        	this.armure.desequipe();
            this.armure = null;
            this.sante -= objet.getSante();
            this.santeDepart -= objet.getSante();
        }
    }

/*               INVENTAIRE                  */

    /** Ajouter un objet dans l'inventaire 
     * @param obj objet a ajouter
     */
    public void addInventaire(Objet obj) {
    	this.inventaire.ajouterObjet(obj);//La procedure ajouterObjet traite le cas ou l'inventaire est plein.
    }//(Elle ne fait rien)
    
    public Inventaire getInvent() {
        return this.inventaire;
    }
    
    /**
     *  Sauvegarde les attributs du joueur
     */
    public void sauvegarderAttributs() {
    	String nomFichier = Environnement.NOM_DOSSIER_SAUV + "/sauvegarde_joueur.ser";   /** Nom du fichier de sauvegarde */
		File fichier = new File(nomFichier);
		if (fichier.exists()) {
			fichier.delete(); 	/** Si le fichier existe déjà alors on le supprime */
		}
		try {
			FileOutputStream fos = new FileOutputStream(nomFichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.xp);
			oos.writeObject(this.arme);   /** Sauvegarde dans le fichier */
			oos.writeObject(this.armure);
			oos.writeObject(this.coef);
			oos.writeObject(this.inventaire);
			oos.writeObject(this.santeDepart);
			oos.writeObject(this.sante);
			oos.writeObject(this.degats);
			oos.writeObject(this.position);
			oos.writeObject(this.etat_courant);
			oos.writeObject(this.nom);
			oos.writeObject(this.direction);
			oos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Charge les attributs du joueur a partir des fichiers de sauvegarde
     */
    public void chargerAttributs() {
    	try {
			/** Ouverture du fichier */
			FileInputStream fis = new FileInputStream(Environnement.NOM_DOSSIER_SAUV + "/sauvegarde_joueur.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.xp = (XP) ois.readObject();
			this.arme = (Objet) ois.readObject();
			this.armure = (Objet) ois.readObject();
			this.coef = (double) ois.readObject();
			this.inventaire = (Inventaire) ois.readObject();
			this.santeDepart = (double) ois.readObject();
			this.sante = (double) ois.readObject();
			this.degats = (double) ois.readObject();
			this.position = (Position) ois.readObject();
			this.etat_courant = (Etat) ois.readObject();
			this.nom = (String) ois.readObject();
			this.direction = (String) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
   
}
