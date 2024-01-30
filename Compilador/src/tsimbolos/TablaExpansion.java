package tsimbolos;

import java.util.ArrayList;
import tsimbolos.auxi.Datos;

public class TablaExpansion {

    private ArrayList<Datos> te = new ArrayList<>();


    public TablaExpansion() {
    }

    public void put(int ind, Datos d) {
        if (te.contains(d)) {
            te.set(ind, d);
        }
        this.te.add(ind, d);
    }

    public Datos get(int ind) {
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

    public int getSize(){
        return te.size();
    }
}
