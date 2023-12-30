package tsimbolos.descripciones;

public class Descripcion {

    public enum TDesc {
        darg,dargin,dcamp,dconst,dindex,dnula,dproc,dtipus,dvar,darray
    }

    TDesc tipoDescripcion;

    public Descripcion (TDesc tipoDescripcion){
        this.tipoDescripcion = tipoDescripcion;
    }

    public String toString(){
        return tipoDescripcion.toString();
    }

    public String getTDescripcion(){
        return tipoDescripcion.toString();
    }
}
