package sintactico.arbol;

public class NodoRealAsign extends Nodo {

    NodoId NodoId;
    NodoSimbolos nodoIgual;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoPuntocoma;

    int linea;
    int columna;

    public NodoRealAsign(NodoId NodoId,  NodoSimbolos nodoIgual,  NodoExpresion nodoExpresion, NodoSimbolos nodoPuntocoma, int l, int c) {
        super("REALASIGN", false, l, c);
        this.NodoId = NodoId;
        this.nodoIgual = nodoIgual;
        this.nodoExpresion = nodoExpresion;
        this.nodoPuntocoma = nodoPuntocoma;

        linea = l;
        columna = c;

    }

   
    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }


}
