package sintactico.arbol;

public class NodoTermino extends Nodo {

    NodoId identificador;
    int entero;
    NodoLlamadaFunc nodoLlamadaFunc;
    float decimal;
    NodoSigno nodoSigno;


    public NodoTermino(NodoId identificador, int l, int c) {
        super("TERMINO", false, l, c);
        this.identificador = identificador;
    }

    public NodoTermino(int entero, NodoSigno s, int l, int c){
        super("TERMINO", false, l, c);
        this.entero = entero;
        this.nodoSigno = s;
    }

    public NodoTermino(NodoLlamadaFunc nodoLlamadaFunc, int l, int c){
        super("TERMINO", false, l, c);
        this.nodoLlamadaFunc = nodoLlamadaFunc;
    }

    public NodoTermino(float decimal, NodoSigno s, int l, int c){
        super("TERMINO", false, l, c);
        this.decimal = decimal; 
        this.nodoSigno = s;
    }

    public NodoSigno getNodoSigno() {
        return nodoSigno;
    }
    
    public NodoId getIdentificador() {
        return identificador;
    }

    public int getEntero() {
        return entero;
    }

    public NodoLlamadaFunc getNodoLlamadaFunc() {
        return nodoLlamadaFunc;
    }

    public float getDecimal() {
        return decimal;
    }
}