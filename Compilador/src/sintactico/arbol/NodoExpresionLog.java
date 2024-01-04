package sintactico.arbol;

public class NodoExpresionLog extends Nodo{

    NodoTerminoLog termino1;
    NodoOpLog opLog;
    NodoTerminoLog termino2;

    public NodoExpresionLog(NodoTerminoLog termino1, NodoOpLog opLog, NodoTerminoLog termino2, int f, int l) {
        super("ExpArit",false,f, l);
        this.termino1 = termino1;
        this.opLog = opLog;
        this.termino2 = termino2;
    }
}