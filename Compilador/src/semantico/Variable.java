package semantico;

import herramientas.Tipo;

public class Variable{

    private final String nombre;
    private final TipoVar tipoVar;
    private final String idProcedimiento;
    private int bytes;
    private final Tipo tipo;
    private final boolean esArray;
    private int dimension;
    private int size;

    public enum TipoVar{
        PARAMETRO, VARIABLE;
    }

    public Variable(String nombre, TipoVar tipoVar, String idProcedimiento, Tipo tipo, boolean esArray, int bytes, int size){
        this.nombre = nombre;
        this.tipoVar = tipoVar;
        this.idProcedimiento = idProcedimiento;
        this.tipo = tipo;
        this.esArray = esArray;
        this.bytes = bytes;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getNombre(){
        return nombre;
    }

    public TipoVar getTipoVar(){
        return tipoVar;
    }

    public String getIdProcedimiento(){
        return idProcedimiento;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public boolean esArray(){
        return esArray;
    }

    public int getBytes(){
        return bytes;
    }

    public int getDimension(){
        return dimension;
    }



    /*Bloques dinamicos ?¿?¿?¿?¿*/
    public void setBytes(int bytes){
        this.bytes = bytes;
    }

    public void setDimension(int dimension){
        this.dimension = dimension;
    }




}