package sintactico.arbol;

public class NodoSents extends Nodo{
    

    NodoSents nodoSents;
    NodoSent nodoSent;

    int linea;
    int columna;

    public NodoSents(NodoSents nodoSents, NodoSent nodoSent, int l, int c) {
        super("SENTS", false, l, c);
        this.nodoSents = nodoSents;
        this.nodoSent = nodoSent;
    }

     public NodoSents() {
        super("SENTS", true);
    }


}
