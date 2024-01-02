package sintactico.arbol;

public class NodoTerminoLog1 extends Nodo {

NodoSimbolos opLog;

    public NodoTerminoLog1(NodoSimbolo opLog){
        super("TERMINO1", false, l, c);
        this.opLog = opLog;
    }
}