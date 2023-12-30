package tsimbolos.descripciones;

public class Dargin extends Descripcion{                   // paràmetre formal constant

    private int nv = 0;                 // identificador únic
    private String tipus;               // identificador del tipus del paràmetre

    public Dargin (String tipus) {
        super(TDesc.dargin);
        nv += 1;
        this.tipus = tipus;
    }

    public int getnv(){
        return nv;
    }

    public String getTipus(){
        return tipus;
    }

    @Override
    public String toString() {
        return "argin{" + nv + " " + tipus+ "}";
    }
}