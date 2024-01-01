package sintactico.arbol;

public class NodoId extends Nodo {

    String nombre;

    public NodoId(String n) {
        super("ID", false);
        nombre = n;
    }

    public String getNombre() {
        return nombre;
    }
}
