package tsimbolos.descripciones;


public class DTipus extends Descripcion {

    public enum TSB {
        tsb_char, tsb_int, tsb_bool, tsb_void
    }

    private int limiteInferior;
    private int limiteSuperior;
    private TSB tsb;
    
    public DTipus(TSB tsb,int limiteInferior, int limiteSuperior) {
        super(TDesc.dtipus);
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.tsb = tsb;
    }

    public int getLimiteInferior() {
        return limiteInferior;
    }

    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public TSB getTsb() {
        return tsb;
    }
}