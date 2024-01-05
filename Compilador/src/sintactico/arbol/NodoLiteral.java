package sintactico.arbol;

import herramientas.Tipo;

public class NodoLiteral extends Nodo{
    
    String valor;
    Tipo tipo;

    public NodoLiteral(String valor, Tipo tipo,int f, int c) {
        super("Literal",false,f, c);
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public Tipo getTipo() {
        return tipo;
    }
    
}
