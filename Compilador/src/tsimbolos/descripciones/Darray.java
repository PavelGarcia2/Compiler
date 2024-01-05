package tsimbolos.descripciones;
import herramientas.*;
public class Darray extends Descripcion {
    
    private final int variableNumber;
    private Tipo tipus;  
    private int tam; // numero de la variable que está en la tabla de variables que contiene el total de ocupacion de bytes de la array
    private boolean init;
    private int dimensiones;
    
    public Darray(int variableNumber, Tipo tipus, int dimensiones, boolean init) {
        super(TDesc.darray);
        this.variableNumber = variableNumber;
        this.tipus = tipus;
        this.tam = -1;
        this.init = init;
        this.dimensiones = dimensiones;
    }

    public int getVariableNumber() {
        return variableNumber;
    }

    public Tipo getTipus() {
        return tipus;
    }
    
    public int getTam() {
        return tam;
    }

    public boolean isInit() {
        return init;
    }
    
    public void setInit(Boolean init) {
        this.init = init;
    }
    
    public void setTam(int tam) {
        this.tam = tam;
    }
    
    public boolean isTamSet() {
        return tam != -1;
    }

    @Override
    public String toString() {
        return "ArrayDescription{" + "variableNumber=" + variableNumber + ", type=" + tipus + ", size=" + tam + '}';
    }
}
