package sintactico.arbol;


public class NodoInitCases extends Nodo {

    // puede ser un numero, un string largo o un caracter


    Tipo tipo;
    String valor;

    public NodoInitCases(Tipo tipo,String valor,int l, int c) {
        super("INIT_CASES", false, l, c);
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
    
}
