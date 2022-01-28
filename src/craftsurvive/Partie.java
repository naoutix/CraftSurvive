package craftsurvive;

import craftsurvive.Personnage.Etat;

public class Partie {

    /** Joueur de la partie */
    private Joueur player;

    /** Environnement de la partie */
    private Environnement floor;

    /* Horde d'ennemi de la partie */
    private Horde horde;

    static int idObj = 1;


    /** Créer une nouvelle partie à partir d'un nom de personnage
     * 
     * @param nom nom du personnage
     */
    public Partie(){
        this.floor = new Environnement();
        this.player = new Joueur(100, 10, "PlayerOne", 10, 10);
        this.horde = new Horde(this);
    }

    /** Recuperer l'environnement
     * ATTENTION: Passage par pointeur toute modification est définitive
     * 
     * @return l'environnement 
     */
    public Environnement getEnv() {
        return this.floor;
    }

    /** Recuperer le joueur
     * ATTENTION: Passage par pointeur toute modification est définitive
     * 
     * @return le personnage
     */
    public Joueur getPerso() {
        return this.player;
    }

    /** Recuperer la horde de la partie 
     *  ATTENTION: Passage par pointeur toute modification est définitive
     * 
     * @return la horde d'ennemi
     */
    public Horde getHorde(){
        return this.horde;
    }

    /** Permet de savoir si une case est libre. 
     * cad si il n'y a pas de joueur ennemi ou décor
     * 
     * @param position
     * @return true si la case est libre
     */
    public boolean islibre(Position position){
        int posX = position.getX();
        int posY = position.getY();
        int MaxX = Minimap.dimensions[0]-1;
        int MaxY = Minimap.dimensions[1]-1;

        Boolean flag3 = false;
        Boolean flag4 = false;
        //  joueur ?
        Boolean flag1 = !((posX == this.player.getPosition().getX()) && (posY == this.player.getPosition().getY()));
        // Hors map?
        Boolean flag2 = (0 <= posX) && (posX <= MaxX) && (0 <= posY) && (posY <= MaxY);
        // Decor?
        if (flag2){
            flag3 = this.floor.getMinimap().estLibre(position);
            // Ennemi?
            if (this.horde != null){
                flag4 = !(this.horde.isEnnemiAt(position));
            } else {
                flag4 = true;
            }
        }
        return flag1 && flag2 && flag3 && flag4;
    }

    /** Verifier si le joueur est en vie
     * 
     * @return true si le joueur est en vie
     */
    public Boolean playerIsAlive(){
        return this.player.getEtat() != Etat.DEAD;
    }

    /** permet d'attaquer le personnage
     * 
     * @param degat le nombre de degat a infliger au joueur
     */
    public void attaquerPerso(Double degat){
        if(this.player.getEtat() == Etat.BLOCK){

        } else {
            this.player.chgSante(-degat);
        }
    }

    /** Permet d'interagir avec une case de l'environnement
     * Si il y a un décor ou un ennemi il subira des dégats
     * 
     * @param position_interaction position ou interagir
     */
    public void interagir(Position position_interaction) {
        // La case contient un decor
        if (!this.floor.getMinimap().estLibre(position_interaction)){
            EltDecor decor = this.floor.getMinimap().getEltDecor(position_interaction);
            if (decor.getBreakable()){
                // Réduire la santé du decor
                decor.chgSante(-this.player.getDegats());
                // l'element est cassé
                if (decor.getSante() <=0){
                    System.out.println("Decor at [" +position_interaction+ "] detruit");
                    this.floor.getMinimap().cutEltDecor(position_interaction);
                    this.player.augmenterXP(1);

                }
            }
        }
        // La case contient un ennemi
        if (this.horde.isEnnemiAt(position_interaction)){
            this.horde.attackEnnemi(position_interaction, this.player.getDegats());
        }
    }

}