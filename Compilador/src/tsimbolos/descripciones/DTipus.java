package tsimbolos.descripciones;
import herramientas.*;

public class DTipus extends Descripcion {



    private int limiteInferior;
    private int limiteSuperior;
    private float limiteSupF;
    private float limiteInfF;
    private Tipo tsb;
    
    public DTipus(Tipo tsb,int limiteInferior, int limiteSuperior) {
        super(TDesc.dtipus);
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.tsb = tsb;
    }

    public DTipus(Tipo tsb,float limiteSupF, float limiteInfF) {
        super(TDesc.dtipus);
        this.limiteSupF = limiteSupF;
        this.limiteInfF = limiteInfF;
        this.tsb = tsb;
    }

    public float getLimiteSupF() {
        return limiteSupF;
    }

    public float getLimiteInfF() {
        return limiteInfF;
    }

    public int getLimiteInferior() {
        return limiteInferior;
    }

    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public Tipo getTsb() {
        return tsb;
    }
}