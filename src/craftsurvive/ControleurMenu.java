package craftsurvive;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControleurMenu extends JMenuBar {

    IHMCraft vue;

    /** Controleur
    *
    *@ param partie
    */
    public ControleurMenu(IHMCraft vue) {

        this.vue = vue;
        /* Menu jeu ajouté à la barre de menu*/
        JMenu jeu = new JMenu("Jeu");
        this.add(jeu);

        /* Sous menus ajoutés au menu jeu */ 
        JMenuItem newpartie = new JMenuItem("Nouvelle Partie");
        JMenuItem quitter = new JMenuItem("Sauvegarder et Quitter");
        jeu.add(newpartie);
        jeu.addSeparator();
        jeu.add(quitter);

        /* Definition du controleur quitter */
        newpartie.addActionListener(new ActionNouvellePartie(this.vue));
        quitter.addActionListener(new ActionQuitter());
    }

    /* Classe permettant de quitter le jeu */
    class ActionQuitter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	vue.getPartie().getEnv().quitter();
        	vue.getPartie().getPerso().sauvegarderAttributs();
            System.exit(0);
        }
    }

    /* Classe permettant de lancer une nouvelle partie */
    class ActionNouvellePartie implements ActionListener {

        IHMCraft jeu;
        public ActionNouvellePartie(IHMCraft jeu){
            this.jeu = jeu;
            //this.jeu.getPartie().getEnv().nouvellePartie();
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            this.jeu.getPartie().getEnv().nouvellePartie();
            if (this.jeu.stop == false){
                this.jeu.stop =true;
                this.jeu.stop();
            }
            this.jeu.start();
        }
    }
    
}