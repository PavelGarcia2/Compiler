package sintactico.arbol;

public class NodoOpRapidos extends Nodo {

    NodoOpRapidosSuma nodoOpRapidosSuma;
    NodoOpRapidosResta nodoOpRapidosResta;

    public NodoOpRapidos(NodoOpRapidosSuma nodoOpRapidosSuma, int l, int c) {
        super("OPRAPIDOS_Suma", false, l, c);
        this.nodoOpRapidosSuma = nodoOpRapidosSuma;

    }

    public NodoOpRapidos(NodoOpRapidosResta nodoOpRapidosResta, int l, int c) {
        super("OPRAPIDOS_Resta", false, l, c);
        this.nodoOpRapidosResta = nodoOpRapidosResta;

    }

    public NodoOpRapidosSuma getNodoOpRapidosSuma() {
        return nodoOpRapidosSuma;
    }

    public NodoOpRapidosResta getNodoOpRapidosResta() {
        return nodoOpRapidosResta;
    }
    
}
