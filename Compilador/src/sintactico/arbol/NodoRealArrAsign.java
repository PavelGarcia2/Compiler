package sintactico.arbol;

public class NodoRealArrAsign extends Nodo {

    NodoId NodoId;
    NodoDimArray dim;
    NodoExpresion nodoExpresion;


    public NodoRealArrAsign(NodoId NodoId,NodoDimArray dim, NodoExpresion nodoExpresion, int l, int c) {
        super("REALASIGN", false, l, c);
        this.NodoId = NodoId;
        this.dim = dim;
        this.nodoExpresion = nodoExpresion;
    }

    public NodoId getNodoId() {
        return NodoId;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

    public NodoDimArray getNodoDimArray(){
        return dim;
    }


}