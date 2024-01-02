package sintactico.arbol;

public class NodoTermino1 extends Nodo {

NodoSimbolos opLog;

    public NodoTermino1(NodoSimbolo opLog){
        super("TERMINO1", false, l, c);
        this.opLog = opLog;
    }
}