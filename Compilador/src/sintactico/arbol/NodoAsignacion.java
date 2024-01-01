package sintactico.arbol;

public class NodoAsignacion extends Nodo{

    NodoSimbolos nodoIgual;
    NodoTipoAsignacion nodoTipoAsignacion;

    public NodoAsignacion(NodoSimbolos nIg, NodoTipoAsignacion ntAsign, int l, int c) {
        super("NODOASIGN", false, l, c);
        nodoIgual = nIg;
        nodoTipoAsignacion = ntAsign;
    }
    
    
}
