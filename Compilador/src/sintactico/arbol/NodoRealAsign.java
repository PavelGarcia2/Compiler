package sintactico.arbol;

public class NodoRealAsign extends Nodo {

    NodoId NodoId;
    NodoExpresion nodoExpresion;


    public NodoRealAsign(NodoId NodoId,  NodoExpresion nodoExpresion, int l, int c) {
        super("REALASIGN", false, l, c);
        this.NodoId = NodoId;
        this.nodoExpresion = nodoExpresion;
    }

    public NodoId getNodoId() {
        return NodoId;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }


}
