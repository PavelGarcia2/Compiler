package sintactico.arbol;

public class NodoDeclVars extends Nodo {

    NodoDeclVars nodoDeclaracionVariables; // Varias
    NodoVar nodoDeclaracionVariable; // Singular

    public NodoDeclVars(NodoDeclVars ndvs, NodoVar ndv, int l, int c) {

        // Contructor en mantenimiento
        super("DeclVars", false, l, c);

        this.nodoDeclaracionVariables = ndvs;
        this.nodoDeclaracionVariable = ndv;

    }

    public NodoVar getNodoDeclaracionVariable() {
        return nodoDeclaracionVariable;
    }

    public NodoDeclVars getNodoDeclaracionVariables() {
        return nodoDeclaracionVariables;
    }

}
