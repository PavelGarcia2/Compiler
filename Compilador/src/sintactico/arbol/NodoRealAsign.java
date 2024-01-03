package sintactico.arbol;

public class NodoRealAsign extends Nodo {

    NodoId NodoId;
    NodoSimbolos nodoIgual;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoPuntocoma;


    public NodoRealAsign(NodoId NodoId,  NodoSimbolos nodoIgual,  NodoExpresion nodoExpresion, NodoSimbolos nodoPuntocoma, int l, int c) {
        super("REALASIGN", false, l, c);
        this.NodoId = NodoId;
        this.nodoIgual = nodoIgual;
        this.nodoExpresion = nodoExpresion;
        this.nodoPuntocoma = nodoPuntocoma;

    }


}
