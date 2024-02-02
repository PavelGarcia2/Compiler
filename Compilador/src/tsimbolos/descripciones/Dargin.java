package tsimbolos.descripciones;

import herramientas.Tipo;

public class Dargin extends Descripcion{                   // paràmetre formal constant

    private int nv = 0;                 // identificador únic
    private Tipo tipus;               // identificador del tipus del paràmetre
    private boolean esArray = false;   // indica si el paràmetre és un array

    public Dargin (Tipo tipus, int nv, boolean esArray) {
        super(TDesc.dargin);
        nv += 1;
        this.tipus = tipus;
        this.nv = nv;
        this.esArray = esArray;
    }

    public Dargin (Tipo tipus, int dim, int nv, boolean esArray) {
        super(TDesc.dargin);
        nv += 1;
        this.tipus = tipus;
        this.nv = nv;
        this.esArray = esArray;
    }


    public int getnv(){
        return nv;
    }
    
    public Tipo getTipus(){
        return tipus;
    }

    @Override
    public String toString() {
        return "argin{" + nv + " " + tipus+ "}";
    }
}