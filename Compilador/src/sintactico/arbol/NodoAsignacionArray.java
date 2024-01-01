package sintactico.arbol;

public class NodoAsignacionArray extends Nodo{

    NodoTipo nodoTipo;
    NodoKeyWords nodoNew;
    NodoDimArray nodoDimArray;
    

    public NodoAsignacionArray(NodoTipo nt, NodoKeyWords nw, NodoDimArray nDimArr,int l, int c) {
        super("ASIGNARR", false, l, c);
        nodoTipo = nt;
        nodoNew = nw;
        nodoDimArray = nDimArr;
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }

    public NodoKeyWords getNodoNew() {
        return nodoNew;
    }

    public NodoTipo getNodoTipo() {
        return nodoTipo;
    }
    
}
