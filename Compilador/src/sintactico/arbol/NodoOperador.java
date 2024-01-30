package sintactico.arbol;

public class NodoOperador extends Nodo{

    NodoOpLog nodoOpLog;
    NodoOpArit nodoOpArit;


    public NodoOperador(NodoOpLog nodoOpLog, NodoOpArit nodoOpArit,int l, int c){
        super("OPERADOR", false, l, c);
        this.nodoOpLog = nodoOpLog;
        this.nodoOpArit = nodoOpArit;
    }

    public NodoOpArit getNodoOpArit() {
        return nodoOpArit;
    }

    public NodoOpLog getNodoOpLog() {
        return nodoOpLog;
    }
}
