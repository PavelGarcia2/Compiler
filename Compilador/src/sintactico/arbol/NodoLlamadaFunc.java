package sintactico.arbol;

public class NodoLlamadaFunc extends Nodo {
    
    NodoId nodoId;
    NodoSimbolos nodoLParen;
    NodoParametros nodoParams;
    NodoSimbolos nodoRParen;

    public NodoLlamadaFunc(NodoId nodoId, NodoSimbolos nodoLParen, NodoParametros nodoParams, NodoSimbolos nodoRParen, int l, int c) {
        super("LLAMADAFUNC", false, l, c);
        this.nodoId = nodoId;
        this.nodoLParen = nodoLParen;
        this.nodoParams = nodoParams;
        this.nodoRParen = nodoRParen;
    }
}
