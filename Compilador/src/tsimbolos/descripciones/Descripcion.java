package tsimbolos.descripciones;

public class Descripcion {


    private final TipoDescripcion desc;

    public enum TipoDescripcion {
        dnula, dvar, dconst, dtipus, dproc, dfunc, dindex, dargin, darg, darray, dstring, dtupel, dcampo
    }

    public Descripcion(TipoDescripcion desc) {
        this.desc = desc;
    }

    public TipoDescripcion getTipoDescripcion() {
        return desc;
    }

}