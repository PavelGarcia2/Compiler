package tsimbolos;

import java.util.ArrayList;
import tsimbolos.auxi.DatosTE;

public class TablaExpansion {

    private ArrayList<DatosTE> te = new ArrayList<>();

    public TablaExpansion() {
    }

    public void put(int ind, DatosTE d) {
        if (te.contains(d)) {
            te.set(ind, d);
        } else {
            te.add(ind, d);
        }
    }

    public DatosTE get(int ind) {
        return te.get(ind);
    }

    public String toString() {
        String teS = "Tabla de expansion \n";
        teS+=".............................................\n";
        for (int i = 0; i < te.size(); i++) {
            teS+="["+i+"]"+te.get(i)+"\n";
        }
        return teS+'\n';
    }
}
