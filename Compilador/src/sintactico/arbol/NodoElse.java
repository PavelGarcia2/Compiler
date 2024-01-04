package sintactico.arbol;

public class NodoElse extends Nodo {

    NodoExpresion nodoExpresion;
    NodoSents nodoSents;
    NodoElse nodoElse;

    NodoKeyWords reservadoElse;


    public NodoElse(NodoExpresion nodoExpresion, NodoSents nodoSents,  NodoElse nodoElse, int l, int c) {
        super("ELSE", false, l, c);
        this.nodoExpresion = nodoExpresion;
        this.nodoSents = nodoSents;
        this.nodoElse = nodoElse;
    }

    public NodoElse(NodoSents nodoSents, int l, int c) {
        super("ELSE", false, l, c);
        this.nodoSents = nodoSents;
    }

    public NodoElse(){
        super("ELSE",true);
    }

    
}
