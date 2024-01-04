package sintactico.arbol;

public class NodoAsignacionArray extends Nodo{

    NodoTipo nodoTipo;
    NodoDimArray nodoDimArray;
    

    public NodoAsignacionArray(NodoTipo nt, NodoDimArray nDimArr,int l, int c) {
        super("ASIGNARR", false, l, c);
        nodoTipo = nt;
        nodoDimArray = nDimArr;
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }
    public NodoTipo getNodoTipo() {
        return nodoTipo;
    }
    
}
