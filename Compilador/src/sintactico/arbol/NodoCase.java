package sintactico.arbol;

public class NodoCase extends Nodo {

    NodoCase nodoCase;
    NodoInitCases nodoInitCases;
    NodoSents nodoSents;

    NodoCaseDefault nodoCaseDefault;


    public NodoCase(NodoCase nodoCase, NodoInitCases nodoInitCases, NodoSents nodoSents, int l, int c) {
        super("CASE", false, l, c);
        this.nodoCase = nodoCase;
        this.nodoInitCases = nodoInitCases;
        this.nodoSents = nodoSents;

    }

    public NodoCase(NodoCaseDefault nodoCaseDefault, int l, int c) {
        super("CASE", false, l, c);
        this.nodoCaseDefault = nodoCaseDefault;
    }

    public NodoCase(){
        super("CASE",true);
    }

    public NodoCase getNodoCase() {
        return nodoCase;
    }

    public NodoInitCases getNodoInitCases() {
        return nodoInitCases;
    }

    public NodoSents getNodoSents() {
        return nodoSents;
    }

    public NodoCaseDefault getNodoCaseDefault() {
        return nodoCaseDefault;
    }
    
}
