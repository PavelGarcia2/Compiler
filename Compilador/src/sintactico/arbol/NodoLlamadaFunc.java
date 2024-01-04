package sintactico.arbol;

public class NodoLlamadaFunc extends Nodo {
    
    NodoId nodoId;
    NodoParametros nodoParams;

    public NodoLlamadaFunc(NodoId nodoId, NodoParametros nodoParams, int l, int c) {
        super("LLAMADAFUNC", false, l, c);
        this.nodoId = nodoId;
        this.nodoParams = nodoParams;
    }
}
