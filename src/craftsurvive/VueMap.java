package craftsurvive;

import javax.swing.*;
import java.util.*;

public class VueMap extends JLabel {

    // les images pour chaque obstacle
    private static final Map<TypeEltDecor, ImageIcon> images = new HashMap<TypeEltDecor, ImageIcon>();

    static {
        images.put(TypeEltDecor.Arbre, new ImageIcon("images/arbre.png"));
        images.put(TypeEltDecor.Rocher, new ImageIcon("images/rocher.png"));
        images.put(TypeEltDecor.Caillou, new ImageIcon("images/caillou.png"));
        images.put(TypeEltDecor.Fleur, new ImageIcon("images/fleur.jpg"));
        images.put(TypeEltDecor.Buisson, new ImageIcon("images/buisson.png"));
        images.put(TypeEltDecor.Coffre, new ImageIcon("images/coffre.png"));
    }

    // la partie en cours
    private Partie partie;

    // coordonnées de la case
    private int x;
    private int y;

    public VueMap(Partie partie, int x, int y) {
        super();
        this.partie = partie;
        this.x = x;
        this.y = y;
        // initialiser l'image d'un case
        maj();
    }
    
    public void maj() {

        // la case est libre
        Position pos = new Position(this.x, this.y);
        if (this.partie.getEnv().getMinimap().estLibre(pos)) {
            this.setIcon(new ImageIcon("images/herbe.png"));
        // la case contient un objet 
        } else {
            this.setIcon(images.get(this.partie.getEnv().getMinimap().getEltDecor(pos).getType()));
        }

        // C'est le personnage
        if (this.x == this.partie.getPerso().getPosition().getX()
                && this.y == this.partie.getPerso().getPosition().getY()) {
            Joueur.Etat perso = this.partie.getPerso().getEtat();
            switch(perso){
                case ATTACK:
                    if (this.partie.getPerso().getDirection() == "gauche"){
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/attack_g.gif")));
                    } else {
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/attack.gif")));
                    }
                    break;
                case BLOCK:
                    if (this.partie.getPerso().getDirection() == "gauche"){
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/block_g.gif")));
                    } else {
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/block_d.gif")));
                    }    
                    break;
                case IDLE:
                    if (this.partie.getPerso().getDirection() == "gauche") {
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/personnage_g.gif")));
                    } else if (this.partie.getPerso().getDirection() == "droite") {
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/personnage_d.gif")));
                    } else {
                        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/personnage_g.gif")));
                    }
                    break;
                case DEAD:
                    this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/dead.png")));
                    break;
                default :
                    break;
            }
        }
        // C'est un ennemi
        if (this.partie.getHorde().isEnnemiAt(new Position(this.x,this.y))){
            switch (this.partie.getHorde().getEnnemi(new Position(this.x,this.y)).getEtat()) {
                case ATTACK:
                    this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/skeleton_attack.gif")));
                    break;
                case IDLE:
                    this.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/skeleton.gif")));
                default:
                    break;
            }
        }
    }
}