package tsimbolos.descripciones;
import herramientas.*;
public class Dvar extends Descripcion{                         // entrada que correspon a una variable

    private int nv = 0;                     // identificador únic
    private Tipo tipus;                   // identificador del tipus de la variable

    public Dvar(int nv,Tipo tipus){
        super(TDesc.dvar);
        this.nv = nv;
        this.tipus = tipus;
    }

    public int getnv(){
        return nv;
    }

    public Tipo getTipus(){
        return tipus;
    }

    @Override
    public String toString(){
        return "Dvar{" + "nv=" + nv + ", tipus=" + tipus + '}';
    }    
}
