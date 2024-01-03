package sintactico.arbol;

public class NodoTerminoLog extends Nodo {

    NodoId nodoId;
    int bool;
    NodoLlamadaFunc nodoLlamadaFunc;

    public NodoTerminoLog(NodoId nodoId,int bool,NodoLlamadaFunc nodoLlamadaFunc, int l, int c){
        super("TERMINO_LOG", false, l, c);
        this.nodoId = nodoId;
        this.bool = bool;
        this.nodoLlamadaFunc = nodoLlamadaFunc;
    }
}