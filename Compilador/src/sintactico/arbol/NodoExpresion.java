package sintactico.arbol;

/**
 * NodoExpresion
 */
public class NodoExpresion extends Nodo{

    NodoExpresion conParentesis;
    NodoExpresion conNegacion;
    NodoExpresionArit nodoExpresionArit;
    NodoExpresionLog nodoExpresionLog;
    NodoId nodoId;
    NodoLiteral nodoLiteral;
    int bool;
    NodoLlamadaFunc nodoLlamadaFunc;

    public NodoExpresion(NodoExpresion conParentesis, NodoExpresion conNegacion, NodoExpresionArit nodoExpresionArit, NodoExpresionLog nodoExpresionLog, 
    NodoId nodoId, NodoLiteral nodoLiteral, int bool, NodoLlamadaFunc nodoLlamadaFunc,int l, int c) {
        super("EXPR", false, l ,c);
        this.conParentesis = conParentesis;        
        this.conNegacion = conNegacion;
        this.nodoExpresionArit = nodoExpresionArit;
        this.nodoId = nodoId;
        this.nodoLiteral = nodoLiteral;
        this.bool = bool;
        this.nodoLlamadaFunc = nodoLlamadaFunc; 
        this.nodoLlamadaFunc = nodoLlamadaFunc; 
    }
}