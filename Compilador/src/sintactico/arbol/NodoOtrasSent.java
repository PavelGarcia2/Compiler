package sintactico.arbol;


/*
 * No se como gestionar los tokens para palabras reservadas, y cuando crear un nodo para dicho token.
 */
public class NodoOtrasSent extends Nodo{

    
    NodoExpresion nodoExpresion;
    NodoSents nodoSents;
    NodoElse nodoElse;
    NodoId nodoId;
    NodoOpRapidos nodoOpRapidos;
    NodoCase nodoCase;
    NodoLlamadaFunc nodoLlamadaFunc;
    NodoCaseDefault def;
    NodoDevuelto nodoDevuelto;

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
    public NodoOtrasSent(int diff, NodoExpresion nodoExpresion, NodoSents nodoSents,NodoElse nodoElse, int l, int c) {
        super("OtrasSents_IF", false, l, c);
        this.nodoExpresion = nodoExpresion;
        this.nodoSents = nodoSents;
        this.nodoElse = nodoElse;
        identificador = diff;
    }

    //while
    public NodoOtrasSent(int diff,NodoExpresion nodoExpresion, NodoSents nodoSents, int l, int c) {
        super("OtrasSents_WHILE", false, l, c);
        this.nodoExpresion = nodoExpresion;
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
    public NodoOtrasSent(int diff,NodoId nodoId, NodoCase nodoCase, NodoCaseDefault def, int l, int c) {
        super("OtrasSents_SWITCH", false, l, c);
        this.nodoId = nodoId;
        this.nodoCase = nodoCase;
        identificador = diff;
        this.def = def;
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
    public NodoOtrasSent(int diff,NodoDevuelto nodoDevuelto, int l, int c) {
        super("OtrasSents_IN", false, l, c);
        identificador = diff;
        this.nodoDevuelto = nodoDevuelto;
    }

    public int getIdentificador() {
        return identificador;
    }


    public NodoSents getNodoSents() {
        return nodoSents;
    }

    public NodoElse getNodoElse() {
        return nodoElse;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

    public NodoOpRapidos getNodoOpRapidos() {
        return nodoOpRapidos;
    }

    public NodoCase getNodoCase() {
        return nodoCase;
    }

    public NodoLlamadaFunc getNodoLlamadaFunc() {
        return nodoLlamadaFunc;
    }


    public NodoCaseDefault getNodoCasoDefault() {
        return def;
    }

    public NodoDevuelto getNodoDevuelto() {
        return nodoDevuelto;
    }

}