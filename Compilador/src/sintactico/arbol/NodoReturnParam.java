package sintactico.arbol;

public class NodoReturnParam extends Nodo{

    NodoId nodoId;
    NodoLiteral nodoLiteral;

    public NodoReturnParam(NodoId nodoId,int l, int c) {
        super("RETURN_PARAM", false, l ,c);
        this.nodoId = nodoId;
    }
    public NodoReturnParam(NodoLiteral nodoLiteral,int l, int c) {
        super("RETURN_PARAM", false, l ,c);
        this.nodoLiteral = nodoLiteral;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoLiteral getNodoLiteral() {
        return nodoLiteral;
    }
}