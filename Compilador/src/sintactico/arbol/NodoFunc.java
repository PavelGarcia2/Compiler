package sintactico.arbol;

public class NodoFunc extends Nodo{
    NodoTipo nodoTipo;
    NodoDeclArray nodoDeclArray;
    NodoId nodoId;
    NodoDeclFuncP nodoDeclFuncP;
    NodoDeclVars nodoDeclVars;
    NodoSents nodoSents;
    NodoReturn nodoReturn;

    public NodoFunc(NodoTipo nodoTipo, NodoDeclArray nodoDeclArray, NodoId nodoId, NodoDeclFuncP nodoDeclFuncP, NodoDeclVars nodoDeclVars, NodoSents nodoSents, NodoReturn nodoReturn, int l, int c) {
        super("FUNC", false, l, c);
        this.nodoTipo = nodoTipo;
        this.nodoDeclArray = nodoDeclArray;
        this.nodoId = nodoId;
        this.nodoDeclFuncP = nodoDeclFuncP;
        this.nodoDeclVars = nodoDeclVars;
        this.nodoSents = nodoSents;
        this.nodoReturn = nodoReturn;
    }

    public NodoTipo getNodoTipo() {
        return nodoTipo;
    }

    public NodoDeclArray getNodoDeclArray() {
        return nodoDeclArray;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoDeclFuncP getNodoDeclFuncP() {
        return nodoDeclFuncP;
    }

    public NodoDeclVars getNodoDeclVars() {
        return nodoDeclVars;
    }

    public NodoSents getNodoSents() {
        return nodoSents;
    }

    public NodoReturn getNodoReturn() {
        return nodoReturn;
    }
}
