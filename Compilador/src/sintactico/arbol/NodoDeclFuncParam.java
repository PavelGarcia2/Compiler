package sintactico.arbol;

public class NodoDeclFuncParam extends Nodo {

    NodoTipo nodoTipo;
    NodoId nodoId;
    NodoDeclArray nodoDeclArray;

    int linea;
    int columna;

    public NodoDeclFuncParam(NodoTipo nodoTipo, NodoId nodoId, NodoDeclArray nodoDeclArray, int l, int c) {
        super("DECLFUNCPARAM", false, l, c);

        this.nodoTipo = nodoTipo;
        this.nodoId = nodoId;
        this.nodoDeclArray = nodoDeclArray;

        linea = l;
        columna = c;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
    
}
