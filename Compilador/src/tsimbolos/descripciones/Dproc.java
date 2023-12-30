package tsimbolos.descripciones;

public class Dproc extends Descripcion{

    private int np;

    public Dproc(){
        super(TDesc.dproc);
        np += 1;
    }

    public int getNp() {
        return np;
    }

    @Override
    public String toString() {
        return "Dproc{" +"np=" + np +'}';
    }
}
