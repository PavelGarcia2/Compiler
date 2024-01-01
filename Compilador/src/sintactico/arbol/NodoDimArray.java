package sintactico.arbol;

public class NodoDimArray extends Nodo{

    NodoDimArray nodoDimArray;          //PARA RECURSIVIDAD
    NodoSimbolos nodoLCorchete;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoRCorchete;


    public NodoDimArray(NodoDimArray nDimArr,NodoSimbolos nSim,NodoExpresion nExpr,NodoSimbolos nSim2,int l, int c) {
        super("DIMARRAY", false, l, c);
        nodoDimArray = nDimArr;
        nodoLCorchete = nSim;
        nodoExpresion = nExpr;
        nodoRCorchete = nSim2;
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

    public NodoSimbolos getNodoLCorchete() {
        return nodoLCorchete;
    }

    public NodoSimbolos getNodoRCorchete() {
        return nodoRCorchete;
    }
    
}
