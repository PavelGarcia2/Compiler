package sintactico.arbol;

public class NodoOpArit extends Nodo {

NodoSimbolos opArit;

    public NodoOpArit(NodoSimbolos opArit, int l, int c){
        super("OP_ARIT", false, l, c);
        this.opArit = opArit;
    }
}