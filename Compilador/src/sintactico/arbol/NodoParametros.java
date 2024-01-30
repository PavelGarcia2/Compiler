package sintactico.arbol;

public class NodoParametros extends Nodo {

    NodoPar nodoPar;

    public NodoParametros(NodoPar nodoPar, int l, int c) {
        super("PARAMETROS", false, l, c);

        this.nodoPar = nodoPar;
    }

    public NodoParametros(){
        super("PARAMETROS", true);
    }

    public NodoPar getNodoPar() {
        return nodoPar;
    }

    
    
}
