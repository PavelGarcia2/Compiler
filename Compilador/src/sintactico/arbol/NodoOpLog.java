package sintactico.arbol;

public class NodoOpLog extends Nodo {

NodoSimbolos opLog;

    public NodoOpLog(NodoSimbolos opLog, int l, int c){
        super("OP_LOG", false, l, c);
        this.opLog = opLog;
    }
}