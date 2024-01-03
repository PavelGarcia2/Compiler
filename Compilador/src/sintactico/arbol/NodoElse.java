package sintactico.arbol;

public class NodoElse extends Nodo {

    NodoKeyWords nodoElseIf;
    NodoExpresion nodoExpresion;
    NodoSents nodoSents;
    NodoElse nodoElse;

    NodoKeyWords reservadoElse;


    public NodoElse(NodoKeyWords nodoElseIf, NodoExpresion nodoExpresion, NodoSents nodoSents,  NodoElse nodoElse, int l, int c) {
        super("ELSE", false, l, c);
        this.nodoElseIf = nodoElseIf;
        this.nodoExpresion = nodoExpresion;
        this.nodoSents = nodoSents;
        this.nodoElse = nodoElse;
    }

    public NodoElse(NodoKeyWords reservadoElse, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket, int l, int c) {
        super("ELSE", false, l, c);
        this.reservadoElse = reservadoElse;
        this.nodoSents = nodoSents;
    }

    public NodoElse(){
        super("ELSE",true);
    }

    
}
