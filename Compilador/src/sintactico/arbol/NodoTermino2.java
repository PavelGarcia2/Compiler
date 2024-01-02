package sintactico.arbol;

public class NodoTermino2 extends Nodo {

NodoSimbolos opLog;

    public NodoTermino2(NodoSimbolo opLog){
        super("TERMINO1", false, l, c);
        this.opLog = opLog;
    }
}