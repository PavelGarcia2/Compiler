package sintactico.arbol;

public class NodoPar extends Nodo {

    NodoExpresion nodoExpresion;
    NodoContParam nodoContParam;


    public NodoPar(NodoExpresion nodoExpresion, NodoContParam nodoContParam, int l, int c) {
        super("PARAMETROS", false, l, c);

        this.nodoExpresion = nodoExpresion;
        this.nodoContParam = nodoContParam;
    }

    public NodoPar(){
        super("PARAMETROS", true);
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

    public NodoContParam getNodoContParam() {
        return nodoContParam;
    }

    
    
}
