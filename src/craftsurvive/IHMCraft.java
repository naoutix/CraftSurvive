package craftsurvive;

import javax.swing.*;

import craftsurvive.Personnage.Etat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class IHMCraft extends JFrame implements KeyListener {

    /** Contient la partie courante */
    private Partie partie;

    /** Controleur de la map */
    private ControleurMap ctrMap;

    /** Controleur de l'inventaire */
    private ControleurInventaire ctrInv;

    private JPanel inventBar;

    /** Controleur de la bar de sante */
    private JProgressBar progresSante;

    /** Controleur de la bar d'XP */
    private JProgressBar progresXP;

    /** Booleen d'attente de mise a jour */
    public Boolean maj;

    /** niveau du joueur */
    private JLabel niv;

    /** degats du joueur */
    private JLabel deg;

    /* ancien niveau **/
    private int oldNiv;

    Container container;

    /** Boolean de stopage de mise a jour */
    Boolean stop;

    public Boolean quitter;

    /** 
     * Constructeur de l'interface graphique
     */
    public IHMCraft() {

        System.out.println("Bienvenue à craftSurvive");
        this.setTitle("craftsurvive");
        // organisation globale
        container = this.getContentPane();
        ControleurMenu bar = new ControleurMenu(this);    // Menu du haut
        container.setLayout(new BorderLayout());
        container.add(bar, BorderLayout.NORTH);
        this.start();
    }

    /**
     * Initialise l'interface graphique
     */
    public void start(){
        this.partie = new Partie();                         // Creation de la partie
        this.ctrMap = new ControleurMap(partie);            // Controleur map
        container.add(ctrMap, BorderLayout.CENTER);

        // Bar de progression
        JPanel barProgression = new JPanel(new GridLayout(8, 1));
        // Sante 
        this.progresSante = new JProgressBar(0, (int)this.partie.getPerso().getSanteDepart());
        this.progresSante.setStringPainted(true);
        this.progresSante.setValue((int) this.partie.getPerso().getSante());
        // Niveau
        this.niv = new JLabel(String.valueOf(this.partie.getPerso().getXP().getNiveau()), SwingConstants.CENTER);
        this.oldNiv = this.partie.getPerso().getXP().getNiveau();
        // Degats
        this.deg = new JLabel(String.valueOf((int)this.partie.getPerso().getDegats()), SwingConstants.CENTER);
        // XP
        this.progresXP = new JProgressBar(0, (int) this.partie.getPerso().getXP().getXPMax());
        this.progresXP.setStringPainted(true);
        this.progresXP.setValue((int) this.partie.getPerso().getXP().getXP());

        barProgression.add(new JLabel("Degats", SwingConstants.CENTER));
        barProgression.add(this.deg);
        barProgression.add(new JLabel("Vie", SwingConstants.CENTER));
        barProgression.add(this.progresSante);
        barProgression.add(new JLabel("Niveau", SwingConstants.CENTER));
        barProgression.add(this.niv);
        barProgression.add(new JLabel("XP", SwingConstants.CENTER));
        barProgression.add(this.progresXP);

        // Inventaire et Bar
        this.inventBar = new JPanel(new BorderLayout());
        this.ctrInv = new ControleurInventaire(partie);
        this.inventBar.add(this.ctrInv, BorderLayout.CENTER);
        this.inventBar.add(barProgression, BorderLayout.NORTH);
        container.add(this.inventBar, BorderLayout.EAST);

        // indication sur le comportement de la fenetre
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // Key Listener
        this.addKeyListener(this);
        this.revalidate();
        this.maj = false;

        // L'appli est de nouveau active
        this.stop = false;
    }

    /** Retire les composants pour une nouvelle partie
     * 
     */
    public void stop(){
        this.container.remove(this.ctrMap);
        this.container.remove(this.inventBar);
    }

    /**
     * Mise a jour de l'ecran 
     */
    public void maj(){
        // Mise a jour de la map
        for (int i = 0; i < Minimap.dimensions[0]; i++) {
            for (int j = 0; j < Minimap.dimensions[1]; j++) {
                this.ctrMap.getMap(i, j).maj(); 
            }
        }
        for (int i = 0; i < Inventaire.TailleMax; i++) {
                this.ctrInv.getInvent(i).majInventaire();
        }
        // Mise a jour des bar
        if (this.oldNiv != this.partie.getPerso().getXP().getNiveau()) {
            this.progresSante.setMaximum((int) this.partie.getPerso().getSanteDepart());
            this.progresXP.setMaximum((int) this.partie.getPerso().getXP().getXPMax());
            this.oldNiv = this.partie.getPerso().getXP().getNiveau();
            
        }
        this.progresSante.setValue((int) this.partie.getPerso().getSante());
        this.progresXP.setValue((int) this.partie.getPerso().getXP().getXP());
        this.niv.setText(String.valueOf(this.partie.getPerso().getXP().getNiveau()));
        this.deg.setText(String.valueOf((int)this.partie.getPerso().getDegats()));
    }

    /**
     * Changement d'affichage quand on meurt
     */
    public void gameOver(){
        this.maj();
        this.stop();
        this.stop = true;
        JOptionPane.showMessageDialog( this, "You are dead: type New game in menu to restart","Too bad!", JOptionPane.INFORMATION_MESSAGE);
        //Le jeu est arreter
    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean chgtTab = false;
        if (!maj){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Z:
                    chgtTab = this.partie.getPerso().deplacer(this.partie, "haut");
                    break;
                case KeyEvent.VK_S:
                    chgtTab = this.partie.getPerso().deplacer(this.partie, "bas");
                    break;
                case KeyEvent.VK_D:
                    chgtTab = this.partie.getPerso().deplacer(this.partie, "droite");
                    break;
                case KeyEvent.VK_Q:
                    chgtTab = this.partie.getPerso().deplacer(this.partie, "gauche");
                    break;
                case KeyEvent.VK_E:
                    // Direction ou il va attaquer
                    Position maj = this.partie.getPerso().getNextPos(this.partie.getPerso().getDirection());
                    // Passage en mode attaque
                    this.partie.getPerso().chgtEtat(Etat.ATTACK);
                    if (maj.getX()>=0 && maj.getX() <= Minimap.dimensions[0]-1 && maj.getY()>=0 && maj.getY() <= Minimap.dimensions[1]-1 ) {
                        partie.interagir(maj);
                    }
                    break;
                case KeyEvent.VK_A:
                    this.partie.getPerso().chgtEtat(Etat.BLOCK);
                    break;
                default:
                    break;
            }
            // position final du personnage

            this.maj=true;
            if (chgtTab){
                this.partie.getHorde().refresh();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_E:
                this.partie.getPerso().chgtEtat(Etat.IDLE);
                break;
            case KeyEvent.VK_A:
                this.partie.getPerso().chgtEtat(Etat.IDLE);
                break;
            default:
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    /** 
     * @return la partie liee a cette IHM
     */
    public Partie getPartie(){
        return this.partie;
    }
}
