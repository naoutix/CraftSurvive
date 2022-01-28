package craftsurvive;

public enum TypeEltDecor {
    Arbre("Arbre"), Rocher("Rocher"), Caillou("Caillou"), Fleur("Fleur"), Buisson("Buisson"), Coffre("Coffre");

    private String nom;
    static final int taille = 6;
    
    private TypeEltDecor(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return this.nom;
    }
}