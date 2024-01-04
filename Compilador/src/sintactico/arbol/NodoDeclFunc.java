package sintactico.arbol;

public class NodoDeclFunc extends Nodo {

    NodoDeclFunc nodoDeclFunc;
    NodoFunc nodoFunc;


    public NodoDeclFunc(NodoDeclFunc nodoDeclFunc, NodoFunc nodoFunc, int l, int c) {
        super("DECLFUNC", false, l, c);

        this.nodoDeclFunc = nodoDeclFunc;
        this.nodoFunc = nodoFunc;
    }

    public NodoDeclFunc() {
        super("DECLFUNC", true);
    } 

    public NodoDeclFunc getNodoDeclFunc() {
        return nodoDeclFunc;
    }

    public NodoFunc getNodoFunc() {
        return nodoFunc;
    }
}