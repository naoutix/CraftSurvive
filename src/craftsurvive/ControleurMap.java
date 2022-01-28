package craftsurvive;

import javax.swing.*;
import java.awt.*;

public class ControleurMap extends JPanel {

    public Partie partie;
    public VueMap map[][];

    public ControleurMap(Partie partie) {
        super(new GridLayout(Minimap.dimensions[0], Minimap.dimensions[1]));
        this.partie = partie;

        // initialiser la map
        this.map = new VueMap[Minimap.dimensions[0]][Minimap.dimensions[1]];
        for (int i = 0; i < Minimap.dimensions[0]; i++) {
            for (int j = 0; j < Minimap.dimensions[1]; j++) {
                this.map[i][j] = new VueMap(this.partie, i, j);
                this.add(map[i][j]);
            }
        }
        //mettre dans le bonne ordre les cases
        for (int j = Minimap.dimensions[1]-1; j >= 0; j--) {
            for (int i = 0; i < Minimap.dimensions[0]; i++) {
                this.add(map[i][j]);
            }
        }
    }

    public VueMap getMap(int i, int j) {
        return this.map[i][j];
    }
}