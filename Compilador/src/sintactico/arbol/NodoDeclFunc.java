package sintactico.arbol;

public class NodoDeclFunc extends Nodo {

    NodoDeclFunc nodoDeclFunc;
    NodoFunc nodoFunc;

    int linea;
    int columna;

    public NodoDeclFunc(NodoDeclFunc nodoDeclFunc, NodoFunc nodoFunc, int l, int c) {
        super("DECLFUNC", false, l, c);

        this.nodoDeclFunc = nodoDeclFunc;
        this.nodoFunc = nodoFunc;
        this.linea = l;
        this.columna = c;
    }

    public NodoDeclFunc() {
        super("DECLFUNC", true);
    } 
}