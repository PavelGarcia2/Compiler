package sintactico.arbol;

public class NodoAsignacionVar extends Nodo{

    NodoId id;
    NodoAsignacion nodoAsignacion;

    public NodoAsignacionVar (NodoId id, NodoAsignacion nodoAsignacion, int fila, int columna){
        super("NodoAsignacionVar", true);
        this.id = id;
        this.nodoAsignacion = nodoAsignacion;
    }

    public NodoId getId() {
        return id;
    }

    public NodoAsignacion getNodoAsignacion() {
        return nodoAsignacion;
    }

    public void setId(NodoId id) {
        this.id = id;
    }

    public void setNodoAsignacion(NodoAsignacion nodoAsignacion) {
        this.nodoAsignacion = nodoAsignacion;
    }

}
