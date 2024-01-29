package sintactico.arbol;

public class NodoTerminoLog extends Nodo {

    NodoId nodoId;
    int bool;
    NodoLlamadaFunc nodoLlamadaFunc;
    int entero;
    float decimal;
    NodoSigno nodoSigno;

    public NodoTerminoLog(NodoId nodoId,int bool,NodoLlamadaFunc nodoLlamadaFunc, int entero, float decimal, NodoSigno nodoSigno,int l, int c){
        super("TERMINO_LOG", false, l, c);
        this.nodoId = nodoId;
        this.bool = bool;
        this.nodoLlamadaFunc = nodoLlamadaFunc;
        this.entero = entero;
        this.decimal = decimal;
        this.nodoSigno = nodoSigno;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public int getBool() {
        return bool;
    }

    public int getEntero(){
        return entero;
    }

    public float getDecimal(){
        return decimal;
    }

    public NodoSigno getNodoSigno(){
        return nodoSigno;
    }
    
    public NodoLlamadaFunc getNodoLlamadaFunc() {
        return nodoLlamadaFunc;
    }
}