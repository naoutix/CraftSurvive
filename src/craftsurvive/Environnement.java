package craftsurvive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Environnement
 */
public class Environnement {

    private Minimap tableauCourant;
    private int xTableau;
    private int yTableau;
    
    static final String NOM_DOSSIER_SAUV = "sauvegarde_craftsurvive";
    
    /**
     * Construire l'environnement
     */
    public Environnement() {
    	File sauvegardeJeu = new File(NOM_DOSSIER_SAUV);
    	boolean nouvellePartie = sauvegardeJeu.mkdir();
    	
    	if (nouvellePartie) {
			/** !!!! CREATION D'UN NOUVEL ENVIRONNEMENT !!! */
			this.xTableau = 0;
			this.yTableau = 0;
			this.creerNouvelEnvironnement();
    	} else {
    		File testSauvX = new File(NOM_DOSSIER_SAUV + "/x.ser");
    		File testSauvY = new File(NOM_DOSSIER_SAUV + "/y.ser");
    		if (testSauvX.exists() && testSauvY.exists()) {
				try {
					/** Ouverture du fichier */
					FileInputStream fis = new FileInputStream(NOM_DOSSIER_SAUV + "/x.ser");
					FileInputStream fis1 = new FileInputStream(NOM_DOSSIER_SAUV + "/y.ser");
					ObjectInputStream ois = new ObjectInputStream(fis);
					ObjectInputStream ois1 = new ObjectInputStream(fis1);
					Object xSauvegarde = ois.readObject();
					Object ySauvegarde = ois1.readObject();
					this.xTableau = (int) xSauvegarde; 
					this.yTableau = (int) ySauvegarde; 
					ois.close();
					ois1.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
    		} else {
    			this.xTableau = 0;
    			this.yTableau = 0;
    		}
			this.getNextTableau("chargement");
    	}
    }

    /** 
     * Permet de creer une nouvelle partie (supprime la sauvegarde)
     * 
     */
    public void nouvellePartie() {
    	File sauvegardeJeu = new File(NOM_DOSSIER_SAUV);
    	File[] subfiles = sauvegardeJeu.listFiles();
    	for (int i = 0; i < subfiles.length; i++) { 
			File subfile = subfiles[i];
			subfile.delete();
		}
    	sauvegardeJeu.delete();
    }

    /**
     * Permet de quitter tout en sauvegardant le necessaire 
     */
    public void quitter() {
    	this.saveTableau();
		File fichierx = new File(NOM_DOSSIER_SAUV + "/x.ser");
		File fichiery = new File(NOM_DOSSIER_SAUV + "/y.ser");
		if (fichierx.exists()) {
			fichierx.delete(); 	/** Si le fichier existe déjà alors on le supprime */
		}
		if (fichiery.exists()) {
			fichiery.delete(); 	/** Si le fichier existe déjà alors on le supprime */
		}
		try {
			FileOutputStream fos = new FileOutputStream(NOM_DOSSIER_SAUV + "/x.ser");
			FileOutputStream fos1 = new FileOutputStream(NOM_DOSSIER_SAUV + "/y.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			oos.writeObject(this.xTableau);   /** Sauvegarde de xTableau dans le fichier */
			oos1.writeObject(this.yTableau);   /** Sauvegarde de yTableau dans le fichier */
			oos.close();
			oos1.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Permet de trouver dans les sauvegarde le prochain tableau et si il n'y est pas de le creer
     * @param direction la direction dans laquelle on va 
     */
	public void getNextTableau(String direction) {
		switch (direction) {
			case "haut" :
				this.saveTableau();
				this.yTableau += 1;
				break;
			case "bas" :
				this.saveTableau();
				this.yTableau -= 1;
				break;
			case "gauche" :
				this.saveTableau();
				this.xTableau -= 1;
				break;
			case "droite" :
				this.saveTableau();
				this.xTableau += 1;
				break;
			default :
		}
		String nomFichier = NOM_DOSSIER_SAUV + "/sauvegarde" + this.xTableau + "_" + this.yTableau + ".ser";
		File fichier = new File(nomFichier);
		if (fichier.exists()) {
			try {
				/** Ouverture du fichier */
				FileInputStream fis = new FileInputStream(nomFichier);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object tableauFichier = ois.readObject();
				this.tableauCourant = (Minimap) tableauFichier; 
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			/** !!!! CREATION D'UN NOUVEL ENVIRONNEMENT !!! */
			this.creerNouvelEnvironnement();
		}
		
	}

	/**
	 * @return la Minimap dans laquelle on est i.e. le tableau courant 
	 */
	public Minimap getMinimap() {
		return this.tableauCourant;
	}

	/** 
	 * Permet de sauvegarder le tableau courant quand on le quitte 
	 */
	private void saveTableau() {
		String nomFichier = NOM_DOSSIER_SAUV + "/sauvegarde" + this.xTableau + "_" + this.yTableau + ".ser";   /** Nom du fichier de sauvegarde */
		File fichier = new File(nomFichier);
		if (fichier.exists()) {
			fichier.delete(); 	/** Si le fichier existe déjà alors on le supprime */
		}
		try {
			FileOutputStream fos = new FileOutputStream(nomFichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.tableauCourant);   /** Sauvegarde dans le fichier */
			oos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Cree un nouveau tableau
	 */
	private void creerNouvelEnvironnement() {
		this.tableauCourant = new Minimap();
	}
}
