package sintactico.arbol;

public class NodoDimArray extends Nodo{

    NodoDimensiones nodoDimensiones;          //PARA RECURSIVIDAD
    NodoExpresion nodoExpresion;


    public NodoDimArray(NodoDimensiones nodoDimensiones,NodoExpresion nExpr,int l, int c) {
        super("DIMARRAY", false, l, c);
        this.nodoDimensiones = nodoDimensiones;
        nodoExpresion = nExpr;
    }

    public NodoDimArray(){
        super("DIMARRAY",true);
    }

    public NodoDimensiones getNodoDimensiones() {
        return nodoDimensiones;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

}
