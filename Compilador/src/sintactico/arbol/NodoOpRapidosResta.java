package sintactico.arbol;

public class NodoOpRapidosResta extends Nodo {

    NodoId nodoId;
    NodoSimbolos nodoResta;
    NodoSimbolos nodoResta2;

    public NodoOpRapidosResta(NodoId nodoId,NodoSimbolos nodoResta,NodoSimbolos nodoResta2, int l, int c) {
        super("OPRAPIDOS_Resta", false, l, c);
        this.nodoId = nodoId;
        this.nodoResta = nodoResta;
        this.nodoResta2 = nodoResta2;
    }
    
}
