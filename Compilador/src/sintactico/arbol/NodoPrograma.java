package sintactico.arbol;

public class NodoPrograma extends Nodo{

    private NodoDeclVars nodoDeclaracionVariables;
    private NodoDeclFunc nodoDeclaracionFunciones;
    private NodoMain nodoMain;

    public NodoPrograma(NodoDeclVars ndv,NodoDeclFunc ndf, NodoMain nm, int l, int c) {

        // Constructor en mantenimiento
        super("MAIN", false, l ,c);

        nodoDeclaracionVariables = ndv;
        nodoDeclaracionFunciones = ndf;
        nodoMain = nm;

    }

    public NodoDeclVars getNodoDeclaracionVariables(){
        return nodoDeclaracionVariables;
    }

    public NodoDeclFunc getNodoDeclaracionFunciones(){
        return nodoDeclaracionFunciones;
    }

    public NodoMain getNodoMain(){
        return nodoMain;
    }
    
}
