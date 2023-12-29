package tsimbolos;

import java.util.ArrayList;
import tsimbolos.aux.DatosTE;

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
}
