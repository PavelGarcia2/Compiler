package sintactico.arbol;


public class NodoInitCases extends Nodo {

    // puede ser un numero, un string largo o un caracter


    Tipo tipo;
    int valor;
    String valor2;

    public NodoInitCases(Tipo tipo,int valor,String valor2,int l, int c) {
        super("INIT_CASES", false, l, c);
        this.tipo = tipo;
        this.valor = valor;
        this.valor2 = valor2;
    }
    
}
