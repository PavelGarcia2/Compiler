package sintactico.arbol;

public class NodoReturn extends Nodo {

    NodoKeyWords nodoReturn;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoPuntoComa;

    int linea;
    int columna;

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

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
    
}
