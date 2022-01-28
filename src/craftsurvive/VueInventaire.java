package craftsurvive;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class VueInventaire extends JLabel{
	
	// les images pour chaque objet
    private static final Map<NomObjet, ImageIcon> images
        = new HashMap<NomObjet, ImageIcon>();

    static {
        images.put(NomObjet.epee, new ImageIcon("images/inventaire/epee.png"));
        images.put(NomObjet.bouclier, new ImageIcon("images/inventaire/bouclier.png"));
        images.put(NomObjet.marteau, new ImageIcon("images/inventaire/marteau.png"));
        images.put(NomObjet.hache, new ImageIcon("images/inventaire/hache.png"));
        images.put(NomObjet.arc, new ImageIcon("images/inventaire/sabre.png"));
        images.put(NomObjet.couteau, new ImageIcon("images/inventaire/couteau.png"));
        images.put(NomObjet.armor, new ImageIcon("images/inventaire/armure.png"));
        images.put(NomObjet.casque, new ImageIcon("images/inventaire/casque.png"));
        images.put(NomObjet.epeeBois, new ImageIcon("images/inventaire/epeeBois.png"));
    }
    
 // les images pour chaque objet
    private static final Map<NomObjet, ImageIcon> imagesEquipe
        = new HashMap<NomObjet, ImageIcon>();

    static {
        imagesEquipe.put(NomObjet.epee, new ImageIcon("images/inventaire/epeeEquipe.png"));
        imagesEquipe.put(NomObjet.bouclier, new ImageIcon("images/inventaire/bouclierEquipe.png"));
        imagesEquipe.put(NomObjet.marteau, new ImageIcon("images/inventaire/marteauEquipe.png"));
        imagesEquipe.put(NomObjet.hache, new ImageIcon("images/inventaire/hacheEquipe.png"));
        imagesEquipe.put(NomObjet.arc, new ImageIcon("images/inventaire/arcEquipe.png"));
        imagesEquipe.put(NomObjet.couteau, new ImageIcon("images/inventaire/couteauEquipe.png"));
        imagesEquipe.put(NomObjet.armor, new ImageIcon("images/inventaire/armureEquipe.png"));
        imagesEquipe.put(NomObjet.casque, new ImageIcon("images/inventaire/casqueEquipe.png"));
        imagesEquipe.put(NomObjet.epeeBois, new ImageIcon("images/inventaire/epeeBoisEquipe.png"));
    }
    
	private Partie partie;
	// coordonnées de la case
    private int k;

    /**
     * Construire la vue inventaire de la case x
     * @param partie la partie courante
     * @param x
     */
	public VueInventaire(Partie partie,int x) {
		this.partie = partie;
		this.k= x;
		majInventaire();
	}

	/** 
	 * Mise a jour de la vue inventaire d'une case 
	 */
	public void majInventaire() {
		Inventaire inv= partie.getPerso().getInvent();
		if (inv.estOccupe(this.k)) {
			if (inv.getObjet(this.k).estEquipe()) {
				this.setIcon(imagesEquipe.get(inv.getObjet(this.k).getNom()));
			}
			else {
                this.setIcon(images.get(inv.getObjet(this.k).getNom()));
            }
        }
        else {
            this.setIcon(new ImageIcon("images/inventaire/vide.png"));
        }
	}
	
}
