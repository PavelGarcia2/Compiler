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
    NodoExpresion nodoExpresion1;
    NodoExpresion nodoExpresion2;
    NodoOperador nodoOperador;
    NodoDimArray dim;
    int valorAritmetico;
    int nv;

    public NodoExpresion(NodoExpresion conNegacion,NodoExpresion nodoExpresion1,NodoOperador nodoOperador,NodoExpresion nodoExpresion2, 
    NodoId nodoId, NodoLiteral nodoLiteral, int bool, NodoLlamadaFunc nodoLlamadaFunc, NodoDimArray dim,int l, int c) {
        super("EXPR", false, l ,c);        
        this.conNegacion = conNegacion;
        this.nodoId = nodoId;
        this.nodoLiteral = nodoLiteral;
        this.bool = bool;
        this.nodoLlamadaFunc = nodoLlamadaFunc; 
        this.nodoExpresion1 = nodoExpresion1;
        this.nodoExpresion2 = nodoExpresion2;
        this.nodoOperador = nodoOperador;
        this.dim = dim;
    }

    public void setNv(int nv) {
        this.nv = nv;
    }

    public int getNv() {
        return nv;
    }

    public NodoOperador getNodoOperador() {
        return nodoOperador;
    }

    public NodoExpresion getNodoExpresion1() {
        return nodoExpresion1;
    }

    public NodoExpresion getNodoExpresion2() {
        return nodoExpresion2;
    }

    public NodoExpresion getNodoExpresionConParentesis() {
        return conParentesis;
    }

    public NodoExpresion getNodoExpresionConNegacion() {
        return conNegacion;
    }

    public NodoExpresionArit getNodoExpresionArit() {
        return nodoExpresionArit;
    }

    public NodoExpresionLog getNodoExpresionLog() {
        return nodoExpresionLog;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoLiteral getNodoLiteral() {
        return nodoLiteral;
    }

    public int getBool() {
        return bool;
    }

    public NodoDimArray getNodoDimArray(){
        return dim;
    }

    public NodoLlamadaFunc getNodoLlamadaFunc() {
        return nodoLlamadaFunc;
    }
}