package sintactico.arbol;

public class NodoId extends Nodo {

    String nombre;

    public NodoId(String n, int linea, int columna) {
        super("ID", false,linea,columna);
        nombre = n;
    }

    public String getNombre() {
        return nombre;
    }
}
