package sintactico.arbol;


/*
 * No se como gestionar los tokens para palabras reservadas, y cuando crear un nodo para dicho token.
 */
public class NodoOtrasSent extends Nodo{

    
    NodoParametros nodoParametros;
    NodoSents nodoSents;
    NodoElse nodoElse;
    NodoId nodoId;
    NodoExpresion nodoExpresion;
    NodoOpRapidos nodoOpRapidos;
    NodoCase nodoCase;
    NodoLlamadaFunc nodoLlamadaFunc;

    NodoKeyWords nodoIn;    



    // IDENTIFICADOR
    int identificador;
    /* 
        if -> 0 
        while -> 1
        for -> 2
        switch -> 3
        print -> 4
        println -> 5
        llamadaFunc -> 6
        In -> 7
    */ 
    

    //if
    public NodoOtrasSent(int diff, NodoParametros nodoParametros, NodoSents nodoSents,NodoElse nodoElse, int l, int c) {
        super("OtrasSents_IF", false, l, c);
        this.nodoParametros = nodoParametros;
        this.nodoSents = nodoSents;
        this.nodoElse = nodoElse;
        identificador = diff;
    }

    //while
    public NodoOtrasSent(int diff,NodoParametros nodoParametros, NodoSents nodoSents, int l, int c) {
        super("OtrasSents_WHILE", false, l, c);
        this.nodoParametros = nodoParametros;
        this.nodoSents = nodoSents;
        identificador = diff;
    }

    //for
    public NodoOtrasSent(int diff,NodoId nodoId, NodoExpresion nodoExpresion, NodoOpRapidos nodoOpRapidos, NodoSents nodoSents, int l, int c) {
        super("OtrasSents_FOR", false, l, c);
        this.nodoId = nodoId;
        this.nodoExpresion = nodoExpresion;
        this.nodoOpRapidos = nodoOpRapidos;
        this.nodoSents = nodoSents;
        identificador = diff;
    }

    //switch
    public NodoOtrasSent(int diff,NodoId nodoId, NodoCase nodoCase, int l, int c) {
        super("OtrasSents_SWITCH", false, l, c);
        this.nodoId = nodoId;
        this.nodoCase = nodoCase;
        identificador = diff;
    }

    //print
    public NodoOtrasSent(int diff, NodoExpresion nodoExpresion, int l, int c, int a) {
        super("OtrasSents_PRINT", false, l, c);
        this.nodoExpresion = nodoExpresion;
        identificador = diff;
    }

    //println
    public NodoOtrasSent(int diff,NodoExpresion nodoExpresion, int l, int c) {
        super("OtrasSents_PRINTLN", false, l, c);
        this.nodoExpresion = nodoExpresion;
        identificador = diff;
    }

    //llamadaFunc
    public NodoOtrasSent(int diff,NodoLlamadaFunc nodoLlamadaFunc, int l, int c) {
        super("OtrasSents_LLAMADAFUNC", false, l, c);
        this.nodoLlamadaFunc = nodoLlamadaFunc;
        identificador = diff;
    }

    //In
    public NodoOtrasSent(int diff, int l, int c) {
        super("OtrasSents_IN", false, l, c);
        identificador = diff;
    }

    public int getIdentificador() {
        return identificador;
    }

}