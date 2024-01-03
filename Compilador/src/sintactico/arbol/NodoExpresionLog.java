package sintactico.arbol;

public class NodoExpresionLog extends Nodo{

    NodoTermino termino1;
    NodoOpLog opLog;
    NodoTermino termino2;

    public NodoExpresionLog(NodoTermino termino1, NodoOpLog opLog, NodoTermino termino2, int f, int l) {
        super("ExpArit",false,f, l);
        this.termino1 = termino1;
        this.opLog = opLog;
        this.termino2 = termino2;
    }
}