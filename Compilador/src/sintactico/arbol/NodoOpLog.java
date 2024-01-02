package sintactico.arbol;

public class NodoOpLog extends Nodo {

NodoSimbolos opLog;

    public NodoOpLog(NodoSimbolo opLog){
        super("OP_LOG", false, l, c);
        this.opLog = opLog;
    }
}