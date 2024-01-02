package sintactico.arbol;

public class NodoSent extends Nodo {
    
    NodoOtrasSent nodoOtrasSent;
    NodoRealAsign nodoRealAsign;


    public NodoSent(NodoOtrasSent nodoOtrasSent, NodoRealAsign nodoRealAsign, int l, int c) {
        super("SENT", false, l, c);
        this.nodoOtrasSent = nodoOtrasSent;
        this.nodoRealAsign = nodoRealAsign;
        linea = l;
        columna = c;
    }

    public int getColumna() {
        return columna;
    }   

    public int getLinea() {
        return linea;
    }

}
