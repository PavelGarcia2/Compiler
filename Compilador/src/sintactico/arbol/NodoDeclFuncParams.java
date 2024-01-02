package sintactico.arbol;

public class NodoDeclFuncParams extends Nodo {

    NodoDeclFuncParam nodoDeclFuncParam;
    NodoSimbolos nodoComa;
    NodoDeclFuncParams nodoDeclFuncParams;

    int linea;
    int columna;

    public NodoDeclFuncParams(NodoDeclFuncParam NodoDeclFuncParam, int l, int c) {
        super("DECLFUNCSPARAMS", false, l, c);
        this.nodoDeclFuncParam = NodoDeclFuncParam;
        linea = l;
        columna = c;
    }

    public NodoDeclFuncParams(NodoDeclFuncParam nodoDeclFuncParam, NodoSimbolos nodoComa, NodoDeclFuncParams nodoDeclFuncParams, int l, int c) {
        super("DECLFUNCPARAMS", false, l, c);
        this.nodoDeclFuncParam = nodoDeclFuncParam;
        this.nodoComa = nodoComa;
        this.nodoDeclFuncParams = nodoDeclFuncParams;
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
