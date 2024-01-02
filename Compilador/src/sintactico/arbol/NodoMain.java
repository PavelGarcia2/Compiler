package sintactico.arbol;

public class NodoMain extends Nodo{
    
    //NODO keyword main
    NodoKeyWords nodoMain;

    //NODO lbracket
    NodoSimbolos nodoLBracket;

    //NODO declvars
    NodoDeclVars nodoDeclVars;

    NodoSents nodoSents;

    //NODO rbracket
    NodoSimbolos nodoRBracket;

    int linea;
    int columna;


    public NodoMain(NodoKeyWords nodoMain, NodoSimbolos nodoLBracket, NodoDeclVars nodoDeclVars, NodoSents nodoSents, NodoSimbolos nodoRBracket, int l, int c) {
        super("MAIN", false, l, c);
        this.nodoMain = nodoMain;
        this.nodoLBracket = nodoLBracket;
        this.nodoDeclVars = nodoDeclVars;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
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
