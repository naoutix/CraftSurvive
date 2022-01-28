package craftsurvive;

public class Attente extends Thread {

    public static final int REFRESH = 60;

    private IHMCraft jeu;
    private boolean quitter;

    public Attente(IHMCraft jeu) {
        super();
        this.jeu = jeu;
    }

    @Override
    public void run() {
        int cpt = 0;
        // Boucle infini
        Boolean alive = true;
        while (true) {
            if (!this.jeu.stop){
                alive = this.jeu.getPartie().playerIsAlive();
                if (alive) {
                    // Mettre a jour l'affichage
                    this.jeu.maj = false;
                    this.jeu.maj();
                    // Mettre a jour l'IA
                    if (cpt == 15) {
                        this.jeu.getPartie().getHorde().deplacer();
                        this.jeu.getPartie().getHorde().attack();
                        cpt = 0;
                    }
                    // On endore le processus pendant REFRESH millisecond
                    cpt++;
                } else {
                    this.jeu.gameOver();
                    System.out.println("GAME OVER");
                }
            }
            try {
                Thread.sleep(REFRESH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}