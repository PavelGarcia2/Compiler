package tsimbolos;

import tsimbolos.auxi.Datos;

public class NodoExpansion {
    Datos notVisible;
    Datos visible;

    public NodoExpansion(Datos not, Datos yes){
        notVisible  =not;
        visible = yes;
    }
}
