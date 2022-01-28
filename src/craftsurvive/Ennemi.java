package craftsurvive;

import java.util.Random;

public class Ennemi extends Personnage {

	/** Rayon de decection du joueur */
	private int rayon;

	/** pointeur sur la partie */
	private Partie partie;


	/** Creer un ennemi 
	 * 
	 * @param sante sante de l'ennemi
	 * @param degats degat de l'ennemi
	 * @param nom	nom de l'ennemi
	 * @param x		position x
	 * @param y		position y
	 * @param partie partie ou est l'ennemi
	 * @param rayon	rayon de voyance de l'ennemi
	 */
	public Ennemi(double sante, double degats, String nom, int x, int y, Partie partie, int rayon) {
		super(sante, degats, nom, x, y);
		this.partie = partie;
		this.rayon = rayon;
	}

	/** Deplacer l'ennemi
	 * Il se deplacer aleatoirement si il n'est pas dans le champ de vision de l'ennemi
	 */
	public void deplacer() {
		// Position de depart
		Position newPos = new Position(this.position.getX(), this.position.getY());

		if ( norme1(this.position, this.partie.getPerso().getPosition()) != 1 ){
			this.chgtEtat(Etat.IDLE);
		}
		if (!joueurAProximite(this.partie.getPerso(), newPos)) {
			//le joueur n'est pas a cote
			switch (generateRandomIntIntRange(0, 3)) {
				case 0:
					newPos.incrX(-1);
					break;
				case 1:
					newPos.incrX(+1);
					break;
				case 2:
					newPos.incrY(-1);
					break;
				case 3:
					newPos.incrY(+1);
					break;
				default:
					break;
			}
			if (this.partie.islibre(newPos)) {
					this.position = newPos;
			}
        }
		else {
			// Le joueur est a proximite
			this.position = meilleurDeplacement();
		}

	}


	public void attack() {
		if ( norme1(this.position, this.partie.getPerso().getPosition()) == 1){
			this.chgtEtat(Etat.ATTACK);
			this.partie.attaquerPerso(this.degats);
		}
	}
/*		fonction privee */

	/** Tester si le joueur est a proximité
	 * Pour cela on prend la norme infini du vecteur
	 * @param joueur
	 * @param newPos
	 * @return true si l'ennemi est a proximité
	 */
	private boolean joueurAProximite(Joueur joueur, Position pos) {
		return normeInfi(joueur.getPosition(), pos)<= this.rayon;
	}

	/** Retourne la nome infini d'un vecteur defini par 2 points
	 * 
	 * @param position1
	 * @param position2
	 * @return la norme 2
	 */
	private int normeInfi(Position position1,Position position2){
		return Math.max(position1.getX()-position2.getX(), position1.getY()-position2.getY());
	}

	/** Retourne la norme 1 d'un vecteur defini par 2 point
	 * 
	 * @param position1
	 * @param position2
	 * @return la norme 1
	 */
	private int norme1(Position position1,Position position2){
		return Math.abs(position1.getX()-position2.getX()) + Math.abs(position1.getY()-position2.getY());
	}


	/** Calcul grace a la norme1 la case qui rapproche l'ennemi le plus du joueur
	 * 
	 * @return une position plus proche du joueur
	 */
	private Position meilleurDeplacement() {
		Position joueur = this.partie.getPerso().getPosition();
		Position resultat = this.position;
		int resultatDistance = 1000;
		// Chercher une case si l'ennemi n'est pas deja dvnt le personage
		if (norme1(joueur, this.position) != 1) {
			// On regarde toute les cases a cote
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <=1; j++) {
					Position positionTest = new Position(this.position.getX() + i, this.position.getY() + j);
					// Tester si elle est libre
					if (this.partie.islibre(positionTest)) {
						// Norme1 entre le joueur et la nouvelle positition
						int distance = norme1(joueur, positionTest);
						if (distance < resultatDistance && !(i == 0 && j == 0)) {
							resultatDistance = distance;
							resultat = positionTest;
						}
					}
				}
			}
		}
		return resultat;
	}


	/** Generer un entier aleatoire 
	 * 
	 * @param min borne min
	 * @param max borne max 
	 * @return un entier entre min et max 
	 */
	private static int generateRandomIntIntRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
}
