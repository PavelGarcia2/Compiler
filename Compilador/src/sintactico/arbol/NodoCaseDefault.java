package sintactico.arbol;

public class NodoCaseDefault extends Nodo {

    NodoKeyWords reservadoDefault;
    NodoSimbolos nodoDosPuntos;
    NodoSents nodoSents;
    NodoKeyWords reservadoBreak;
    NodoSimbolos nodoPuntoComa;

    public NodoCaseDefault(NodoKeyWords reservadoDefault, NodoSimbolos nodoDosPuntos, NodoSents nodoSents, NodoKeyWords reservadoBreak, NodoSimbolos nodoPuntoComa, int l, int c) {
        super("CASEDEFAULT", false, l, c);
        this.reservadoDefault = reservadoDefault;
        this.nodoDosPuntos = nodoDosPuntos;
        this.nodoSents = nodoSents;
        this.reservadoBreak = reservadoBreak;
        this.nodoPuntoComa = nodoPuntoComa;
    }
    
}
