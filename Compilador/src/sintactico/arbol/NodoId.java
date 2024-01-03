package sintactico.arbol;

public class NodoId extends Nodo {

    String nombre;
    int linea;
    int columna;

    public NodoId(String n, int linea, int columna) {
        super("ID", false);
        nombre = n;
        this.linea = linea;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }
}
