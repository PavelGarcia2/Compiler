package sintactico.arbol;

public class NodoReturnParam extends Nodo{

    NodoId nodoId;
    NodoLiteral nodoLiteral;

    public NodoReturnParam(NodoId nodoId, NodoLiteral nodoLiteral,int l, int c) {
        super("EXPR", false, l ,c);
        this.nodoId = nodoId;
        this.nodoLiteral = nodoLiteral;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoLiteral getNodoLiteral() {
        return nodoLiteral;
    }
}