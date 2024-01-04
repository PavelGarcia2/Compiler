package sintactico.arbol;

public class NodoPrograma extends Nodo{

    private NodoDeclVars nodoDeclaracionVariables;
    private NodoDeclFunc nodoDeclaracionFunciones;
    private NodoDeclConst nodoDeclaracionConstantes;
    private NodoMain nodoMain;

    public NodoPrograma(NodoDeclConst ndc, NodoDeclVars ndv,NodoDeclFunc ndf, NodoMain nm, int l, int c) {

        // Constructor en mantenimiento
        super("MAIN", false, l ,c);

        nodoDeclaracionConstantes = ndc;
        nodoDeclaracionVariables = ndv;
        nodoDeclaracionFunciones = ndf;
        nodoMain = nm;

    }

    public NodoDeclConst getNodoDeclaracionConstantes(){
        return nodoDeclaracionConstantes;
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
