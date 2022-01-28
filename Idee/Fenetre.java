import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
 
public class Fenetre extends JFrame{

    public Fenetre(){
        //Définit un titre pour notre fenêtre
        this.setTitle("Essai Affichage");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        this.setSize(640, 480);
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        this.setVisible(true);
    }
}