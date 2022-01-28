package craftsurvive;

import java.time.chrono.IsoEra;
import java.util.ArrayList;
import java.util.Random;

import craftsurvive.Personnage.Etat;

public class Horde extends ArrayList<Ennemi> {

    /* Nombre d'ennemi de la horde */
    private int nbEnnemi;

    /* Partie ou se trouve la horde */
    private Partie partie;

    /* Limite d'ennemi dans la horde */
    private int randomLim[];

/*      Constructeur        */
    /** Constuire une horde
     * 
     * @param partie
     */
    public Horde(Partie partie) {
        this(partie, new int[]{0,3});
    }

    /** Constuire une horde d'unz taille alzatoire spécifique
     * 
     * @param partie
     * @param int dimension du groupe sous la forme d'un tableau[min,max]
     */
    public Horde(Partie partie,int dim[]) {
        this.partie = partie;
        this.randomLim = new int[]{dim[0],dim[1]};
        this.createHorde();
    }

/*      Requete/comande     */

    /** Nombre d'ennemi dans la horde
     * 
     * @return nbEnnemi
     */
    public int getNbEnnemi() {
        return this.nbEnnemi;
    }

    /** Permet de savoir si sur une position donnee il ya un ennemi
     * 
     * @param position position a verifier
     * @return true si il ya un ennemi a cette position
     */
    public Boolean isEnnemiAt(Position position) {
        Boolean flag = false;
        for(Ennemi ennemi: this){
            Position position_ennemi = ennemi.getPosition();
            if (position.getX() ==position_ennemi.getX() && position.getY() == position_ennemi.getY() ){
                flag = true;
            }
        }
        return flag;
    }

    /** Creer une nouvelle horde en supprimant la derniere
     * 
     */
    public void refresh(){
        this.clear();
        this.createHorde();
    }

    /** Permet d'attaquer un ennemi a une position donnee
     * Si il n'y a pas d'ennemi cette fonction ne fait rien
     * 
     * @param position position de l'ennemi a attacker
     * @param degat    degat a faire sur l'ennemi
     */
    public void attackEnnemi(Position position,double degat){
        // On verifie qu'il ya un ennemi
        if (isEnnemiAt(position)){
            Ennemi ennemi = this.getEnnemi(position);
            // On change les degats
            ennemi.chgSante(-degat);
            System.out.println(ennemi.getSante());
            if (ennemi.getEtat() == Etat.DEAD){
                this.remove(ennemi);
                this.nbEnnemi--;
                this.partie.getPerso().augmenterXP(20);
            }
        }
    }

    /** Deplacer tous les ennemis
     * 
     */
    public void deplacer() {
        for(Ennemi ennemi : this){
            ennemi.deplacer();
        }
    }

    /** L'IA attaque le joueur */
    public void attack(){
        for(Ennemi ennemi: this)
        {
            ennemi.attack();
        }
    }

/*      Static fonction      */
    /** Permet de generer un nombre aleatoire entre min et max
     * 
     * @param min nombre minimal qui peut etre tire
     * @param max nombre maximal qui peut etre tire
     * @return le nombre choisi
     */
    private static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


/*      Fonction privee       */
    /** Creer la horde 
     * Cette horde est place de façon aleatoire dans la map 
     * 
     */
    private void createHorde(){
        int i =0;
        // Generation de nbEnnemi aleatoire
        this.nbEnnemi = generateRandomIntIntRange(this.randomLim[0], randomLim[1]);
        while(i < this.nbEnnemi){ 
            
            // Random position
            int x = generateRandomIntIntRange(0, Minimap.dimensions[0]-1);
            int y = generateRandomIntIntRange(0, Minimap.dimensions[1]-1);
            Position position = new Position(x, y);

            // On ne met pas d'ennemis sur un autre et pas sur un décor ou sur le joueur
            if (this.partie.islibre(position)){
                this.add(new Ennemi(generateRandomIntIntRange(200, 200)*this.partie.getPerso().getXP().getNiveau(), generateRandomIntIntRange(5, 5)*this.partie.getPerso().getXP().getNiveau(),"Ennemi", x, y, this.partie, 5));
                i++;

            }
        }
    }

    /** Recuperer l'ennemi a la position donné
     * ATTENTION: cette fonction renvoie null si elle ne trouve pas l'ennemi
     * 
     * @param position
     * @return l'ennemi a cette position
     */
    public Ennemi getEnnemi(Position position){
        Ennemi ennemi_return = null;
        for(Ennemi ennemi: this){
            Position position_ennemi = ennemi.getPosition();
            if (position.getX() ==position_ennemi.getX() && position.getY() == position_ennemi.getY() ){
                ennemi_return = ennemi;
                return ennemi_return;
            }
        }
        return ennemi_return;
    }

}