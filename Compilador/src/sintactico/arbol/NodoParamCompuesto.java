package sintactico.arbol;

public class NodoParamCompuesto extends Nodo {

    NodoParametros nodoParametros;
    NodoExpresion nodoExpresion;

    public NodoParamCompuesto(NodoParametros nodoParametros, NodoExpresion nodoExpresion, int l, int c) {
        super("PARAMCOMPUESTO", false, l, c);

        this.nodoParametros = nodoParametros;
        this.nodoExpresion = nodoExpresion;
    }
    
}
