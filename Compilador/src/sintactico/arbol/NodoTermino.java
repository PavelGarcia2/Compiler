package sintactico.arbol;

public class NodoTermino extends Nodo {

    NodoId identificador;
    int entero;
    NodoLlamadaFunc nodoLlamadaFunc;
    float decimal;


    public NodoTermino(NodoId identificador, int l, int c) {
        super("TERMINO", false, l, c);
        this.identificador = identificador;
    }

    public NodoTermino(int entero, int l, int c){
        super("TERMINO", false, l, c);
        this.entero = entero;
    }

    public NodoTermino(NodoLlamadaFunc nodoLlamadaFunc, int l, int c){
        super("TERMINO", false, l, c);
        this.nodoLlamadaFunc = nodoLlamadaFunc;
    }

    public NodoTermino(float decimal, int l, int c){
        super("TERMINO", false, l, c);
        this.decimal = decimal; 
    }
}