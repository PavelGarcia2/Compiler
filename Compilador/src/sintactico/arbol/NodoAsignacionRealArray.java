package sintactico.arbol;

/**
 * NodoAsignacionRealArray
 */
public class NodoAsignacionRealArray extends Nodo {

    NodoId nodoId;
    NodoDimArray nodoDimArray;
    NodoAsignacion nodoAsignacion;

    public NodoAsignacionRealArray (NodoId nodoId, NodoDimArray nodoDimArray, NodoAsignacion nodoAsignacion) {
        super("NodoAsignacionRealArray", true);
        this.nodoId = nodoId;
        this.nodoDimArray = nodoDimArray;
        this.nodoAsignacion = nodoAsignacion;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoAsignacion getNodoAsignacion() {
        return nodoAsignacion;
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }

    public void setNodoAsignacion(NodoAsignacion nodoAsignacion) {
        this.nodoAsignacion = nodoAsignacion;
    }

    public void setNodoDimArray(NodoDimArray nodoDimArray) {
        this.nodoDimArray = nodoDimArray;
    }

    public void setNodoId(NodoId nodoId) {
        this.nodoId = nodoId;
    }
}