package tsimbolos.descripciones;
import herramientas.*;
import sintactico.arbol.NodoId;
public class Dvar extends Descripcion{                         // entrada que correspon a una variable

    private int nv = 0;                     // identificador únic
    private Tipo tipus;                   // identificador del tipus de la variable
    private NodoId nodoId;

    public Dvar(int nv,Tipo tipus, NodoId nodoId){
        super(TDesc.dvar);
        this.nv = nv;
        this.tipus = tipus;
        this.nodoId = nodoId;
    }

    public NodoId getNodoId(){
        return nodoId;
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
