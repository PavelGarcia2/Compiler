package sintactico.arbol;

public class NodoOpArit extends Nodo {

NodoSimbolos opArit;

    public NodoOpArit(NodoSimbolo opArit){
        super("OP_ARIT", false, l, c);
        this.opArit = opArit;
    }
}