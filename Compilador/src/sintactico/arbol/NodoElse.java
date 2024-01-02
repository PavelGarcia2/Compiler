package sintactico.arbol;

public class NodoElse extends Nodo {

    NodoKeyWords nodoElseIf;
    NodoSimbolos nodoLParen;
    NodoExpresion nodoExpresion;
    NodoSimbolos nodoRParen;
    NodoSimbolos nodoLBracket;
    NodoSents nodoSents;
    NodoSimbolos nodoRBracket;
    NodoElse nodoElse;

    NodoKeyWords reservadoElse;


    public NodoElse(NodoKeyWords nodoElseIf, NodoSimbolos nodoLParen, NodoExpresion nodoExpresion, NodoSimbolos nodoRParen, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket, NodoElse nodoElse, int l, int c) {
        super("ELSE", false, l, c);
        this.nodoElseIf = nodoElseIf;
        this.nodoLParen = nodoLParen;
        this.nodoExpresion = nodoExpresion;
        this.nodoRParen = nodoRParen;
        this.nodoLBracket = nodoLBracket;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
        this.nodoElse = nodoElse;
    }

    public NodoElse(NodoKeyWords reservadoElse, NodoSimbolos nodoLBracket, NodoSents nodoSents, NodoSimbolos nodoRBracket, int l, int c) {
        super("ELSE", false, l, c);
        this.reservadoElse = reservadoElse;
        this.nodoLBracket = nodoLBracket;
        this.nodoSents = nodoSents;
        this.nodoRBracket = nodoRBracket;
    }

    public NodoElse(){
        super("ELSE",true);
    }

    
}
