package sintactico.arbol;

public class NodoParamCompuesto extends Nodo {

    NodoParametros nodoParametros;
    NodoSimbolos nodoComa;
    NodoExpresion nodoExpresion;

    public NodoParamCompuesto(NodoParametros nodoParametros, NodoSimbolos nodoComa, NodoExpresion nodoExpresion, int l, int c) {
        super("PARAMCOMPUESTO", false, l, c);

        this.nodoParametros = nodoParametros;
        this.nodoComa = nodoComa;
        this.nodoExpresion = nodoExpresion;
    }
    
}
