package sintactico.arbol;

public class NodoAsignacionNormal extends Nodo{

    NodoExpresion nodoExpresion;

    public NodoAsignacionNormal(NodoExpresion nExp,int l, int c) {
        super("ASIGNNORMAL", false, l, c);
        nodoExpresion = nExp;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }
    
}
