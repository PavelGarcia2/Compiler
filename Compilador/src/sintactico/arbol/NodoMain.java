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



    public NodoMain(NodoDeclVars nodoDeclVars, NodoSents nodoSents, int l, int c) {
        super("MAIN", false, l, c);
        this.nodoDeclVars = nodoDeclVars;
        this.nodoSents = nodoSents;
    }

}
