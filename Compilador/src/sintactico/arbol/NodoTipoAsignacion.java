package sintactico.arbol;

public class NodoTipoAsignacion extends Nodo{

    NodoAsignacionNormal nodoAsignacionNormal;
    NodoAsignacionArray nodoAsignacionArray;

    public NodoTipoAsignacion(NodoAsignacionNormal nAsignNor, NodoAsignacionArray nAsignArr,int l, int c) {
        super("TIPOASIGN", false, l, c);
        nodoAsignacionNormal = nAsignNor;
        nodoAsignacionArray = nAsignArr;
    }

    public NodoAsignacionArray getNodoAsignacionArray() {
        return nodoAsignacionArray;
    }

    public NodoAsignacionNormal getNodoAsignacionNormal() {
        return nodoAsignacionNormal;
    }
}
