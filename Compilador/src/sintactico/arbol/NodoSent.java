package sintactico.arbol;

public class NodoSent extends Nodo {
    
    NodoOtrasSent nodoOtrasSent;
    NodoRealAsign nodoRealAsign;


    public NodoSent(NodoOtrasSent nodoOtrasSent, NodoRealAsign nodoRealAsign, int l, int c) {
        super("SENT", false, l, c);
        this.nodoOtrasSent = nodoOtrasSent;
        this.nodoRealAsign = nodoRealAsign;
    }

    public NodoOtrasSent getNodoOtrasSent() {
        return nodoOtrasSent;
    }

    public NodoRealAsign getNodoRealAsign() {
        return nodoRealAsign;
    }


}
