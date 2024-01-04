package sintactico.arbol;

public class NodoReturn extends Nodo {

    NodoExpresion nodoExpresion;

    public NodoReturn(NodoExpresion nodoExpresion, int l, int c) {
        super("RETURN", false, l, c);
        this.nodoExpresion = nodoExpresion;
    }

    public NodoReturn() {
        super("RETURN", true);
    }


    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

}
