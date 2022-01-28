package craftsurvive;
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class JoueurTest {
	public final static double EPSILON = 0.0001;
	private Joueur Joueur;
	private Objet epee1, epee2;
	private Objet plastron1, plastron2;
	private Partie partie;
	
	@Before public void setUp() {
		Joueur = new Joueur(100, 5, "Jean", 5, 5);
		epee1 = new Objet(10, 0, NomObjet.epee, false);
		epee2 = new Objet(15, 0, NomObjet.epee, false);
		plastron1 = new Objet(0, 50, NomObjet.bouclier, false);
		plastron2 = new Objet(0, 100, NomObjet.bouclier, false);
		Joueur.addInventaire(epee1);
		Joueur.addInventaire(epee2);
		Joueur.addInventaire(plastron1);
		Joueur.addInventaire(plastron2);
		partie = new Partie();
	}


	@Test public void TesterChgSante() {
		Joueur.chgSante(15);
		assertEquals(115, Joueur.getSante(), EPSILON);
		Joueur.chgSante(-10);
		assertEquals(105, Joueur.getSante(), EPSILON);
	}
	
	@Test public void TesterEvolutionJoueur() {
		Joueur.evolutionJoueur(10);
		assertEquals(150, Joueur.getSante(), EPSILON);
		assertEquals(7.5, Joueur.getDegats(), EPSILON);
		assertEquals(0, Joueur.getXP().getXP(), EPSILON);
		assertEquals(2, Joueur.getXP().getNiveau());
		Joueur.evolutionJoueur(10);
		assertEquals(225, Joueur.getSante(), EPSILON);
		assertEquals(11.25, Joueur.getDegats(), EPSILON);
		assertEquals(0, Joueur.getXP().getXP(), EPSILON);
		assertEquals(3, Joueur.getXP().getNiveau());
	}
	
	@Test public void TesterDeplacer() {
		Joueur.deplacer(partie, "gauche");
		assert(Joueur.getPosition().getX() == 4);
		assert(Joueur.getPosition().getY() == 5);
		Joueur.deplacer(partie, "haut");
		assert(Joueur.getPosition().getX() == 4);
		assert(Joueur.getPosition().getY() == 6);
		Joueur.deplacer(partie, "droite");
		assert(Joueur.getPosition().getX() == 5);
		assert(Joueur.getPosition().getY() == 6);
		Joueur.deplacer(partie, "bas");
		assert(Joueur.getPosition().getX() == 5);
		assert(Joueur.getPosition().getY() == 5);
	}
	
	@Test public void TesterSetNom() {
		Joueur.setNom("Bernard");
		assert(Joueur.getNom() == "Bernard");
	}
	
	@Test public void TesterEquiper() {
		Joueur.equiper(epee1, "arme");
		assert(Joueur.getArme().estEquipe());
		assert(Joueur.getDegats() == 15);
		assert(Joueur.getSante() == 100);
		Joueur.equiper(plastron1, "armure");
		assert(Joueur.getArmure().estEquipe());
		assert(Joueur.getDegats() == 15);
		assert(Joueur.getSante() == 150);
		Joueur.equiper(epee2, "arme");
		assert(Joueur.getArme().estEquipe());
		assert(Joueur.getDegats() == 20);
		assert(Joueur.getSante() == 150);
		Joueur.equiper(plastron2, "armure");
		assert(Joueur.getArmure().estEquipe());
		assert(Joueur.getDegats() == 20);
		assert(Joueur.getSante() == 200);
	}
	
	@Test public void TesterDesequiper() {
		Joueur.equiper(epee1, "arme");
		Joueur.equiper(plastron1, "armure");
		Joueur.desequiper(epee1, "arme");
		assert(Joueur.getArme() == null);
		assert(Joueur.getDegats() == 5);
		assert(Joueur.getSante() == 150);
		Joueur.desequiper(plastron1, "armure");
		assert(Joueur.getArmure() == null);
		assert(Joueur.getDegats() == 5);
		assert(Joueur.getSante() == 100);
	}
}
