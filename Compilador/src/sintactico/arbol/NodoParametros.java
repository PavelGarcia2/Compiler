package sintactico.arbol;

public class NodoParametros extends Nodo {

    NodoParamSimple nodoParamSimple;
    NodoParamCompuesto nodoParamCompuesto;


    public NodoParametros(NodoParamSimple nodoParamSimple, int l, int c) {
        super("PARAMETROS", false, l, c);

        this.nodoParamSimple = nodoParamSimple;
    }

    public NodoParametros(NodoParamCompuesto nodoParamCompuesto, int l, int c) {
        super("PARAMETROS", false, l, c);

        this.nodoParamCompuesto = nodoParamCompuesto;
    }

    public NodoParametros(){
        super("PARAMETROS", true);
    }

    public NodoParamSimple getNodoParamSimple() {
        return nodoParamSimple;
    }

    public NodoParamCompuesto getNodoParamCompuesto() {
        return nodoParamCompuesto;
    }

    
    
}
