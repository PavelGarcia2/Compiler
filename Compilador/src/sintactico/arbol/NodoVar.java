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
    //NODO DIM ARRAY
    NodoDimArray nodoDimArray;

    int tipoNodo;
    /* 0: Declaracion
     * 1: Asignacion var
     * 2: Asignacion array
     */

    public NodoVar(int tipoNodo,NodoDimArray nodoDimArray,NodoTipo nT, NodoId nId, NodoDeclArray nDeclArr, NodoAsignacion nAsign, int l, int c) {

        // Contructor en mantenimiento
        super("Var", false, l , c);
        this.tipoNodo = tipoNodo;
        nodoTipo = nT;
        nodoId = nId;
        nodoDeclArray = nDeclArr;
        nodoAsignacion = nAsign;
        this.nodoDimArray = nodoDimArray;
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }

    public void setNodoDimArray(NodoDimArray nodoDimArray) {
        this.nodoDimArray = nodoDimArray;
    }

    public int getTipoNodo() {
        return tipoNodo;
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
    
   /* 
    DECL_VAR ::= TIPO:t ID:id DECL_ARRAY:darr ASIGN:asign  tPuntocoma                                                                                                                                           {: RESULT = new NodoVar(t,id,darr,asign,t.getLine(),t.getColumn()); :}
          | tSet ID:id ASIGN:asign tPuntocoma
          | tSet ID:id DIM_ARRAY:dim ASIGN:asign tPuntocoma
          ;
    */
}
