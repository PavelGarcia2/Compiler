package sintactico.arbol;

public class NodoExpresionArit extends Nodo{

    NodoTermino1 termino1;
    NodoOpArit opArit;
    NodoTermino2 termino2;

    public NodoExpresionArit(NodoTermino1 termino1, NodoOpArit opArit, NodoTermino2 termino2, int f, int l) {
        super("ExpArit",false,f, l);
        this.termino1 = termino1;
        this.opArit = opArit;
        this.termino2 = termino2;
    }
}