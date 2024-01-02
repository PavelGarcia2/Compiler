package sintactico.arbol;

public class NodoParamSimple extends Nodo {
    
    NodoExpresion nodoExpresion;

    public NodoParamSimple(NodoExpresion nodoExpresion, int l, int c) {
        super("PARAMSIMPLE", false, l, c);

        this.nodoExpresion = nodoExpresion;
    }
}
