package sintactico.arbol;

public class NodoDevuelto extends Nodo{

    NodoId id;
    int linea;
    int col;

    public NodoDevuelto(NodoId id, int l, int c){
        super("NODODEVUELTO", false);
        this.id = id;
        linea = l;
        col = c;
    }

    public NodoDevuelto(){
        super("NODODEVUELTO", true);
    }

    public NodoId getId() {
        return id;
    }
}
