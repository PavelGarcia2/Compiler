package sintactico.arbol;

public class NodoMain extends Nodo{
    
    //NODO keyword main
    NodoKeyWords nodoMain;

    //NODO lbracket
    NodoSimbolos nodoLBracket;

    //NODO declvars
    NodoDeclVars nodoDeclVars;

    int linea;
    int columna;

    

    public NodoMain(int l, int c) {
        super("MAIN", false);
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
