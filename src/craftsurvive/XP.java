package craftsurvive;

import java.io.Serializable;

public class XP implements Serializable{
    // XP par niveau
    private double xpMax;

    // niveau d'experience
    private int niveau;

    // experience dans un niveau
    private double xp;

    /** Constructeur
     * @param niveau niveau 
     * @param xp experience dans un niveau
     */
    public XP(int niveau, double xp) {
        this.niveau = niveau;
        this.xp = xp;
        this.xpMax = 100;
    }

    /** Passer au niveau suivant */
    public void evolution(double xpPrec, double coef) {
        ++ this.niveau;
        this.xp = xpPrec - this.xpMax;
        this.xpMax = this.xpMax * coef;
    }

    /** Obtenir l'experience dans un niveau d'un personnage
     * @return xp
     */
    public double getXP() {
        return this.xp;
    }

    public double getXPMax() {
    	return this.xpMax;
    }
    /** Obtenir le niveau d'un personnage
     * @return niveau
     */
    public int getNiveau() {
        return this.niveau;
    }

    /** Gagner de l'experience
     * @param gain
     */
    public void gainXP(int gain) {
        this.xp += gain;
    }
}
