package sintactico.arbol;

public class NodoRealAsign extends Nodo {

    NodoId NodoId;
    NodoExpresion nodoExpresion;
    NodoDimensiones nodoDim;
    

    public NodoRealAsign(NodoId NodoId, NodoDimensiones nodoDim, NodoExpresion nodoExpresion, int l, int c) {
        super("REALASIGN", false, l, c);
        this.NodoId = NodoId;
        this.nodoExpresion = nodoExpresion;
        this.nodoDim = nodoDim;
    }

    public NodoId getNodoId() {
        return NodoId;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

    public NodoDimensiones getNodoDimensiones(){
        return nodoDim;
    }

    
}
