package tsimbolos.descripciones;

import herramientas.Tipo;
import sintactico.arbol.NodoId;

public class Dargin extends Descripcion{                   // paràmetre formal constant

    private int nv = 0;                 // identificador únic
    private Tipo tipus;               // identificador del tipus del paràmetre
    private boolean esArray = false;   // indica si el paràmetre és un array
    private NodoId nodoId;

    public Dargin (Tipo tipus, int nv, boolean esArray, NodoId nodoId) {
        super(TDesc.dargin);
        nv += 1;
        this.tipus = tipus;
        this.nv = nv;
        this.esArray = esArray;
        this.nodoId = nodoId;
    }

    public Dargin (Tipo tipus, int dim, int nv, boolean esArray, NodoId nodoId) {
        super(TDesc.dargin);
        nv += 1;
        this.tipus = tipus;
        this.nv = nv;
        this.esArray = esArray;
        this.nodoId = nodoId;
    }


    public int getnv(){
        return nv;
    }

    public NodoId getNodoId(){
        return nodoId;
    }
    
    public Tipo getTipus(){
        return tipus;
    }

    @Override
    public String toString() {
        return "argin{" + nv + " " + tipus+ "}";
    }
}