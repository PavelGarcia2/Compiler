package sintactico.arbol;

public class NodoCase extends Nodo {

    NodoCase nodoCase;
    NodoKeyWords reservadoCase;
    NodoInitCases nodoInitCases;
    NodoSimbolos nodoDosPuntos;
    NodoSents nodoSents;
    NodoKeyWords reservadoBreak;
    NodoSimbolos nodoPuntoComa;

    NodoCaseDefault nodoCaseDefault;


    public NodoCase(NodoCase nodoCase, NodoKeyWords reservadoCase, NodoInitCases nodoInitCases, NodoSimbolos nodoDosPuntos, NodoSents nodoSents, NodoKeyWords reservadoBreak, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("CASE", false, l, c);
        this.nodoCase = nodoCase;
        this.reservadoCase = reservadoCase;
        this.nodoInitCases = nodoInitCases;
        this.nodoDosPuntos = nodoDosPuntos;
        this.nodoSents = nodoSents;
        this.reservadoBreak = reservadoBreak;
        this.nodoPuntoComa = nodoPuntoComa;
    }

    public NodoCase(NodoCaseDefault nodoCaseDefault, int l, int c) {
        super("CASE", false, l, c);
        this.nodoCaseDefault = nodoCaseDefault;
    }

    public NodoCase(){
        super("CASE",true);
    }
    
}
