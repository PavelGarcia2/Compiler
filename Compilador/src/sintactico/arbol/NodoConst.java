package sintactico.arbol;

public class NodoConst extends Nodo{

    NodoTipo nodoTipo;
    NodoId nodoId;
    NodoAsignacion nodoAsignacion;

    public NodoConst(NodoTipo nodoTipo,NodoId nodoId,NodoAsignacion nodoAsignacion,int linea, int columna){
        super("CONST",false,linea,columna);
        this.nodoTipo = nodoTipo;
        this.nodoId = nodoId;
        this.nodoAsignacion = nodoAsignacion; 
    }

    public NodoTipo getNodoTipo() {
        return nodoTipo;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoAsignacion getNodoAsignacion() {
        return nodoAsignacion;
    }
}
