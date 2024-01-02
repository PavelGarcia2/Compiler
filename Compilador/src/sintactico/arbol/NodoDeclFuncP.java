package sintactico.arbol;

public class NodoDeclFuncP extends Nodo {

    NodoDeclFuncParams nodoDeclFuncParams;

    int linea;
    int columna;

    public NodoDeclFuncP(NodoDeclFuncParams nodoDeclFuncParams,int l, int c) {
        super("DECLFUNCP", false, l, c);
        this.nodoDeclFuncParams = nodoDeclFuncParams;
        linea = l;
        columna = c;
    }

    public NodoDeclFuncP() {
        super("DECLFUNCP", true);
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
}
