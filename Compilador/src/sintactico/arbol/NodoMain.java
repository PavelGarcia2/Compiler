package sintactico.arbol;

public class NodoMain extends Nodo{

    //NODO declvars
NodoDeclVars nodoDeclVars;

    NodoSents nodoSents;

    public NodoMain(NodoDeclVars nodoDeclVars, NodoSents nodoSents, int l, int c) {
        super("MAIN", false, l, c);
this.nodoDeclVars = nodoDeclVars;
        this.nodoSents = nodoSents;
    }

    public NodoDeclVars getNodoDeclVars() {
        return nodoDeclVars;
    }

    public NodoSents getNodoSents() {
        return nodoSents;
    }

}
