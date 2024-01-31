package semantico;

public class Procedimiento{
    private final int ambito;
    private final String etiquetaInicial;
    private int numParams;
    private int sizeVariables;
    private String id;

    public Procedimiento(int ambito, String etiquetaInicial, int numParams, String id){
        this.ambito = ambito;
        this.etiquetaInicial = etiquetaInicial;
        this.numParams = numParams;
        this.id = id;
    }

    public int getAmbito(){
        return this.ambito;
    }

    public String getEtiquetaInicial(){
        return this.etiquetaInicial;
    }

    public int getNumParams(){
        return this.numParams;
    }

    public void setNumParams(int numParams){
        this.numParams = numParams;
    }

    public int getSizeVariables(){
        return this.sizeVariables;
    }

    public void setSizeVariables(int sizeVariables){
        this.sizeVariables = sizeVariables;
    }

    public String getId(){
        return this.id;
    }

}