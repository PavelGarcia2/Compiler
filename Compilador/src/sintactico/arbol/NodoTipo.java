package sintactico.arbol;

public class NodoTipo extends Nodo{

    Tipo tipo;            //De momento un string para saber el tipo

    public NodoTipo(Tipo tipo,int l, int c) {
        super("TIPO", false, l, c);
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
