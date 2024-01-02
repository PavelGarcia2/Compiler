package sintactico.arbol;

public class NodoOpRapidosSuma extends Nodo {

    NodoId nodoId;
    NodoSimbolos nodoSuma;
    NodoSimbolos nodoSuma2;

    public NodoOpRapidosSuma(NodoId nodoId,NodoSimbolos nodoSuma,NodoSimbolos nodoSuma2, int l, int c) {
        super("OPRAPIDOS_Suma", false, l, c);
        this.nodoId = nodoId;
        this.nodoSuma = nodoSuma;
        this.nodoSuma2 = nodoSuma2;
    }
    
}
