package craftsurvive;

public class CraftSurvive {

    public static void main(String[] args) {
        IHMCraft jeu = new IHMCraft();
        Attente att = new Attente(jeu);
        
        att.start();
        try {
            att.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}