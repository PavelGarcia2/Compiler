package tsimbolos.descripciones;

public class Dnula extends Descripcion{

    private int nv = 0;
    private String tipus;

    public Dnula (int nv, String tipus){
        super(TDesc.dnula);
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
    public String toString(){
        return "Dnula";
    }
    
}
