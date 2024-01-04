package sintactico.arbol;

public class NodoExpresionArit extends Nodo{

    NodoTermino termino1;
    NodoOpArit opArit;
    NodoTermino termino2;

    public NodoExpresionArit(NodoTermino termino1, NodoOpArit opArit, NodoTermino termino2, int f, int l) {
        super("ExpArit",false,f, l);
        this.termino1 = termino1;
        this.opArit = opArit;
        this.termino2 = termino2;
    }

    public NodoTermino getTermino1() {
        return termino1;
    }

    public NodoOpArit getOpArit() {
        return opArit;
    }

    public NodoTermino getTermino2() {
        return termino2;
    }
}