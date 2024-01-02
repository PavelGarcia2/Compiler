package sintactico.arbol;


/*
 * No se como gestionar los tokens para palabras reservadas, y cuando crear un nodo para dicho token.
 */
public class NodoOtrasSent extends Nodo{

    NodoKeyWords nodoIF;
    NodoSimbolos nodoLParen;
    NodoParametros nodoParametros;
    NodoSimbolos nodoRParen;
    NodoSimbolos nodoLBracket;
    NodoSents nodoSents;
    NodoSimbolos nodoRBracket;
    NodoElse nodoElse;
    NodoKeyWords nodoWhile;
    NodoKeyWords nodoFor;
    NodoId nodoId;
    NodoSimbolos nodoPuntoComa;
    NodoSimbolos nodoPuntoComa2;
    NodoExpresion nodoExpresion;
    NodoOpRapidos nodoOpRapidos;
    NodoKeyWords nodoSwitch;
    NodoCase nodoCase;
    NodoKeyWords nodoPrint;
    NodoKeyWords nodoPrintLn;
    NodoLlamadaFunc nodoLlamadaFunc;

    int linea;
    int columna;

    //if
    public NodoOtrasSent(NodoKeyWords nodoIF, NodoSimbolos nodoLParen, NodoParametros nodoParametros, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket,NodoElse nodoElse, int l, int c) {
        super("OtrasSents_IF", false, l, c);
        this.nodoIF = nodoIF;
        this.nodoLParen = nodoLParen;
        this.nodoParametros = nodoParametros;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
        this.nodoElse = nodoElse;
        linea = l;
        columna = c;
    }

    //while
    public NodoOtrasSent(NodoKeyWords nodoWhile, NodoSimbolos nodoLParen, NodoParametros nodoParametros, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket, int l, int c) {
        super("OtrasSents_WHILE", false, l, c);
        this.nodoWhile = nodoWhile;
        this.nodoLParen = nodoLParen;
        this.nodoParametros = nodoParametros;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
        linea = l;
        columna = c;
    }

    //for
    public NodoOtrasSent(NodoKeyWords nodoFor, NodoSimbolos nodoLParen, NodoId nodoId, NodoSimbolos nodoPuntoComa, NodoExpresion nodoExpresion, NodoSimbolos nodoPuntoComa2, NodoOpRapidos nodoOpRapidos, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket, int l, int c) {
        super("OtrasSents_FOR", false, l, c);
        this.nodoFor = nodoFor;
        this.nodoLParen = nodoLParen;
        this.nodoId = nodoId;
        this.nodoPuntoComa = nodoPuntoComa;
        this.nodoExpresion = nodoExpresion;
        this.nodoPuntoComa2 = nodoPuntoComa2; //Ns si esto esta bien
        this.nodoOpRapidos = nodoOpRapidos;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
        linea = l;
        columna = c;
    }

    //switch
    public NodoOtrasSent(NodoKeyWords nodoSwitch, NodoSimbolos nodoLParen, NodoId nodoId, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoCase nodoCase, NodoSimbolos nodoRBracket, int l, int c) {
        super("OtrasSents_SWITCH", false, l, c);
        this.nodoSwitch = nodoSwitch;
        this.nodoLParen = nodoLParen;
        this.nodoId = nodoId;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoCase = nodoCase;
        this.nodoRBracket = nodoRBracket;
        linea = l;
        columna = c;
    }

    //print
    public NodoOtrasSent(NodoKeyWords nodoPrint, NodoSimbolos nodoLParen, NodoExpresion nodoExpresion, NodoSimbolos nodoRParen, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("OtrasSents_PRINT", false, l, c);
        this.nodoPrint = nodoPrint;
        this.nodoLParen = nodoLParen;
        this.nodoExpresion = nodoExpresion;
        this.nodoRParen = nodoRParen;
        this.nodoPuntoComa = nodoPuntoComa;
        linea = l;
        columna = c;
    }

    //println
    public NodoOtrasSent(NodoKeyWords nodoPrintLn, NodoSimbolos nodoLParen, NodoExpresion nodoExpresion, NodoSimbolos nodoRParen, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("OtrasSents_PRINTLN", false, l, c);
        this.nodoPrintLn = nodoPrintLn;
        this.nodoLParen = nodoLParen;
        this.nodoExpresion = nodoExpresion;
        this.nodoRParen = nodoRParen;
        this.nodoPuntoComa = nodoPuntoComa;
        linea = l;
        columna = c;
    }

    //llamadaFunc
    public NodoOtrasSent(NodoLlamadaFunc nodoLlamadaFunc, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("OtrasSents_LLAMADAFUNC", false, l, c);
        this.nodoLlamadaFunc = nodoLlamadaFunc;
        this.nodoPuntoComa = nodoPuntoComa;
        linea = l;
        columna = c;
    }






}