package sintactico.arbol;

public class NodoOpArit extends Nodo {

TipoArit opArit;

    public NodoOpArit(TipoArit opArit, int l, int c){
        super("OP_ARIT", false, l, c);
        this.opArit = opArit;
    }

    public TipoArit getOpArit() {
        return opArit;
    }
}