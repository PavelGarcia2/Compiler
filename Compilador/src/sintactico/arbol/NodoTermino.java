package sintactico.arbol;

public class NodoTermino extends Nodo {

    NodoId identificador;
    int entero;
    NodoLlamadaFunc nodoLlamadaFunc;
    float decimal;


    public NodoTermino(NodoId identificador,int entero,NodoLlamadaFunc nodoLlamadaFunc, float decimal, int l, int c) {
        super("TERMINO", false, l, c);
        this.identificador = identificador;
        this.entero = entero;
        this.nodoLlamadaFunc = nodoLlamadaFunc;
        this.decimal = decimal; 
    }
}