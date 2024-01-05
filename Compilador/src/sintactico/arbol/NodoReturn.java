package sintactico.arbol;

public class NodoReturn extends Nodo {

    NodoReturnParam nodoParam;

    public NodoReturn(NodoReturnParam nodoParam, int l, int c) {
        super("RETURN", false, l, c);
        this.nodoParam = nodoParam;
    }

    public NodoReturn() {
        super("RETURN", true);
    }


    public NodoReturnParam getNodoReturnParam() {
        return nodoParam;
    }

}
