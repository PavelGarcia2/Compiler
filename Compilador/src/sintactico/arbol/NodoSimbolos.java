package sintactico.arbol;

public class NodoSimbolos extends Nodo {

    char simbolo;                       // De momento un char para tener simbolos

    public NodoSimbolos (char s, int l, int c) {
        super("SIMBOLOS", false, l, c);
        simbolo = s;
    }
}
