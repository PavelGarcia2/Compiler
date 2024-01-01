package sintactico.arbol;

public class NodoTipo extends Nodo{

    String tipo;            //De momento un string para saber el tipo

    public NodoTipo(String tipo,int l, int c) {
        super("TIPO", false, l, c);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
