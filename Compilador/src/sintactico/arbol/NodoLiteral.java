package sintactico.arbol;

import herramientas.Tipo;

public class NodoLiteral extends Nodo{
    
    String valor;
    Tipo tipo;
    NodoSigno signo;

    public NodoLiteral(NodoSigno signo,String valor, Tipo tipo,int f, int c) {
        super("Literal",false,f, c);
        this.signo = signo;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public NodoSigno getSigno() {
        return signo;
    }
    
}
