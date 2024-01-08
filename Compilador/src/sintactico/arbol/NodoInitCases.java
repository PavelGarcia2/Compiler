package sintactico.arbol;

import herramientas.Tipo;

public class NodoInitCases extends Nodo {

    // puede ser un numero, un string largo o un caracter


    Tipo tipo;
    String valor;
    NodoSigno s;

    public NodoInitCases(Tipo tipo,String valor, NodoSigno s, int l, int c) {
        super("INIT_CASES", false, l, c);
        this.tipo = tipo;
        this.valor = valor;
        this.s = s;
    }

    public String getValor(){
        return this.valor;
    }

    public Tipo getTipo(){
        return this.tipo;
    }

    public NodoSigno getSigno(){
        return this.s;
    }
    
}
