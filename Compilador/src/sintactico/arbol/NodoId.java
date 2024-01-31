package sintactico.arbol;

public class NodoId extends Nodo {

    String nombre;
    int nv;

    public NodoId(String n, int linea, int columna) {
        super("ID", false,linea,columna);
        nombre = n;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNv(int nv) {
        this.nv = nv;
    }

    public int getNv() {
        return nv;
    }
}
