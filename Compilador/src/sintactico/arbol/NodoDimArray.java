package sintactico.arbol;

public class NodoDimArray extends Nodo{

    NodoDimArray nodoDimArray;          //PARA RECURSIVIDAD
    NodoExpresion nodoExpresion;


    public NodoDimArray(NodoDimArray nDimArr,NodoExpresion nExpr,int l, int c) {
        super("DIMARRAY", false, l, c);
        nodoDimArray = nDimArr;
        nodoExpresion = nExpr;
    }

    public NodoDimArray(){
        super("DIMARRAY",true);
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

}
