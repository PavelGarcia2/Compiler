package sintactico.arbol;

public class NodoOpRapidosResta extends Nodo {

    NodoId nodoId;

    public NodoOpRapidosResta(NodoId nodoId, int l, int c) {
        super("OPRAPIDOS_Resta", false, l, c);
        this.nodoId = nodoId;
    }
    
}
