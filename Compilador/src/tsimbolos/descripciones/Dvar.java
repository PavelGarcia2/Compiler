package tsimbolos.descripciones;

public class Dvar extends Descripcion{                         // entrada que correspon a una variable

    private int nv = 0;                     // identificador únic
    private String tipus;                   // identificador del tipus de la variable

    public Dvar(int nv, String tipus){
        super(TDesc.dvar);
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
        return "Dvar{" + "nv=" + nv + ", tipus=" + tipus + '}';
    }    
}
