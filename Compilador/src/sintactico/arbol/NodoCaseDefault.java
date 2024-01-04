package sintactico.arbol;

public class NodoCaseDefault extends Nodo {

    NodoSents nodoSents;

    public NodoCaseDefault(NodoSents nodoSents, int l, int c) {
        super("CASEDEFAULT", false, l, c);
        this.nodoSents = nodoSents;
    }

    public NodoSents getNodoSents() {
        return nodoSents;
    }
    
}
