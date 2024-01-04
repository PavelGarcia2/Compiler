package sintactico.arbol;

public class NodoOpRapidosSuma extends Nodo {

    NodoId nodoId;

    public NodoOpRapidosSuma(NodoId nodoId, int l, int c) {
        super("OPRAPIDOS_Suma", false, l, c);
        this.nodoId = nodoId;
    }
    
    public NodoId getNodoId() {
        return nodoId;
    }
}
