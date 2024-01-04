package sintactico.arbol;

public class NodoAsignacion extends Nodo{

    NodoTipoAsignacion nodoTipoAsignacion;

    public NodoAsignacion(NodoTipoAsignacion ntAsign, int l, int c) {
        super("NODOASIGN", false, l, c);
        nodoTipoAsignacion = ntAsign;
    }
    
    public NodoAsignacion(){
        super("NODOASIGN",true);
    }


    public NodoTipoAsignacion getNodoTipoAsignacion() {
        return nodoTipoAsignacion;
    }
}
