package tsimbolos.auxi;

import tsimbolos.descripciones.Descripcion;

public class DatosTD {
    
    private int np;
    private Descripcion descripcion;
    private int first;

    public DatosTD(int first, int np, Descripcion descripcion){
        this.first = first;
        this.np = np;
        this.descripcion = descripcion;
    }

    public int getFirst(){
        return first;
    }

    public int getnp(){
        return np;
    }

    public Descripcion getDescripcion(){
        return descripcion;
    }

    public void setFirst(int first){
        this.first = first;
    }

    public void setDescripcion(Descripcion descripcion){
        this.descripcion = descripcion;
    }

    public void setnp(int np){
        this.np = np;
    }

    public String toString(){
        return "[ decripcion: "+descripcion+", np:"+np+"]";
    }
}