package sintactico.arbol;

public class NodoVar extends Nodo{

    // NODO TIPO
    NodoTipo nodoTipo;
    // NODO ID
    NodoId nodoId;
    // NODO DECLARR
    NodoDeclArray nodoDeclArray; 
    //NODO ASIGN
    NodoAsignacion nodoAsignacion;
    // NODO PUNTOCOMA
    NodoSimbolos nodoPuntoComa;

    public NodoVar(NodoTipo nT, NodoId nId, NodoDeclArray nDeclArr, NodoAsignacion nAsign, NodoSimbolos nPc, int l, int c) {

        // Contructor en mantenimiento
        super("Var", false, l , c);

        nodoTipo = nT;
        nodoId = nId;
        nodoDeclArray = nDeclArr;
        nodoAsignacion = nAsign;
        nodoPuntoComa = nPc;

    }

    public NodoTipo getNodoTipo() {
        return nodoTipo;
    }

    public NodoId getNodoId() {
        return nodoId;
    }

    public NodoDeclArray getNodoDeclArray() {
        return nodoDeclArray;
    }
    
    public NodoAsignacion getNodoAsignacion() {
        return nodoAsignacion;
    }

    public NodoSimbolos getNodoPuntoComa() {
        return nodoPuntoComa;
    }
}
