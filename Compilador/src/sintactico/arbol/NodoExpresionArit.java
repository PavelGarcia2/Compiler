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
}