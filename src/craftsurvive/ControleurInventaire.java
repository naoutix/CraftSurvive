package craftsurvive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControleurInventaire extends JPanel {
	
	static final int nbLigne = 5;
	static final int nbColonne = 5;
	private Partie partie;
    private VueInventaire inventaire[];

    public ControleurInventaire(Partie partie) {
        super(new GridLayout(nbLigne, nbColonne, 5,5));
        this.partie = partie;
        
        // initialisé l'inventaire
        this.inventaire = new VueInventaire[Inventaire.TailleMax];
        
        for (int i = 0; i < Inventaire.TailleMax; i++) {
			this.inventaire[i] = new VueInventaire(this.partie, i); 
			this.add(inventaire[i]);
			inventaire[i].addMouseListener(new CliquerObjet(i,this.partie));
		}
	}

	
	class CliquerObjet extends MouseAdapter{
		int k;
		Partie partie;
		
		public CliquerObjet(int k, Partie partie) {
			this.k = k;
			this.partie = partie;
		}
		
		public void mouseReleased(MouseEvent me) {
			if (me.getButton() == MouseEvent.BUTTON1) {
				cliqueGauche();
			}

			else if (me.getButton() == MouseEvent.BUTTON3) {
				cliqueDroit();
			}
		}

		private void cliqueGauche() {
			if(this.partie.getPerso().getInvent().estOccupe(this.k)) {
				Objet obj= this.partie.getPerso().getInvent().getObjet(this.k);	
				if (obj.estEquipe()) { //Si l'objet est equipé alors on le désequipe
					if(obj.estLethal()) {
						this.partie.getPerso().desequiper(obj,"arme");
					}
					else {
						this.partie.getPerso().desequiper(obj,"armure");
					}
				}
				else {
					if(obj.estLethal()) {
						if (this.partie.getPerso().getArme() != null) {
							Objet ancien = this.partie.getPerso().getArme();
							int n = this.partie.getPerso().getInvent().getPosition(ancien);
							inventaire[n].majInventaire();
						}
						this.partie.getPerso().equiper(obj,"arme");
					}
					else {
						if (this.partie.getPerso().getArmure() != null) {
							Objet ancien = this.partie.getPerso().getArmure();
							int n = this.partie.getPerso().getInvent().getPosition(ancien);
							inventaire[n].majInventaire();
						}
						this.partie.getPerso().equiper(obj,"armure");
					}
				}
				inventaire[this.k].majInventaire();
			}
		}

		private void cliqueDroit() {
			Inventaire invent = this.partie.getPerso().getInvent();
			if(invent.estOccupe(this.k)) {
				Objet obj = invent.getObjet(this.k);
				if (obj.estEquipe()) {
					if(obj.estLethal()) {
						this.partie.getPerso().desequiper(obj,"arme");
					}
					else {
						this.partie.getPerso().desequiper(obj,"armure");
					}
				}
				invent.supprimerObjet(obj);
				for (int i = 0; i < Inventaire.TailleMax; i++) {
					inventaire[i].majInventaire();
				}
			}
		}
	}
	public VueInventaire getInvent(int i){
		return this.inventaire[i];
	}
}
