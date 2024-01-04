package sintactico.arbol;

public class NodoOpLog extends Nodo {

TipoLog opLog;

    public NodoOpLog(TipoLog opLog, int l, int c){
        super("OP_LOG", false, l, c);
        this.opLog = opLog;
    }
}