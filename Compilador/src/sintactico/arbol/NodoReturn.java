package sintactico.arbol;

public class NodoReturn extends Nodo {

    NodoKeyWords nodoReturn;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoPuntoComa;

    public NodoReturn(NodoKeyWords nodoReturn, NodoExpresion nodoExpresion, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("RETURN", false, l, c);
        this.nodoReturn = nodoReturn;
        this.nodoExpresion = nodoExpresion;
        this.nodoPuntoComa = nodoPuntoComa;
        linea = l;
        columna = c;
    }

    public NodoReturn() {
        super("RETURN", true);
    }

}
