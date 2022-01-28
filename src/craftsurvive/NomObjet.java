package craftsurvive;

import java.io.Serializable;

public enum NomObjet implements Serializable{
    epee("epee"), bouclier("bouclier"), hache("hache"),
    epeeBois("epeeBois"), marteau("marteau"), couteau("couteau"), arc("arc"), armor("armure"), casque("casque");

    private String nom;
    
    
    private NomObjet(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return this.nom;
    }
}