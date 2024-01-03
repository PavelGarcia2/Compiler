package sintactico.arbol;

public class NodoCaseDefault extends Nodo {

    NodoKeyWords reservadoDefault;
    NodoSents nodoSents;


    public NodoCaseDefault(NodoKeyWords reservadoDefault,  NodoSents nodoSents, int l, int c) {
        super("CASEDEFAULT", false, l, c);
        this.reservadoDefault = reservadoDefault;
        this.nodoSents = nodoSents;
    }
    
}
