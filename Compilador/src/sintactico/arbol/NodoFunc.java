package sintactico.arbol;

public class NodoFunc extends Nodo{
    
    NodoKeyWords nodoFunc;
    NodoTipo nodoTipo;
    NodoDeclArray nodoDeclArray;
    NodoId nodoId;
    NodoSimbolos nodoLParen;
    NodoDeclFuncP nodoDeclFuncP;
    NodoSimbolos nodoRParen;
    NodoSimbolos nodoLBracket;
    NodoDeclVars nodoDeclVars;
    NodoSents nodoSents;
    NodoReturn nodoReturn;
    NodoSimbolos nodoRBracket;

    int linea;
    int columna;

    public NodoFunc(NodoKeyWords nodoFunc, NodoTipo nodoTipo, NodoDeclArray nodoDeclArray, NodoId nodoId, NodoSimbolos nodoLParen, NodoDeclFuncP nodoDeclFuncP, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoDeclVars nodoDeclVars, NodoSents nodoSents, NodoReturn nodoReturn, NodoSimbolos nodoRBracket, int l, int c) {
        super("FUNC", false, l, c);
        this.nodoFunc = nodoFunc;
        this.nodoTipo = nodoTipo;
        this.nodoDeclArray = nodoDeclArray;
        this.nodoId = nodoId;
        this.nodoLParen = nodoLParen;
        this.nodoDeclFuncP = nodoDeclFuncP;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoDeclVars = nodoDeclVars;
        this.nodoSents = nodoSents;
        this.nodoReturn = nodoReturn;
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
