package tsimbolos.auxi;

import tsimbolos.descripciones.Descripcion;

public class Datos {

    private Descripcion descripcion;
    private int first;
    private String idcamp;
    private int np; // Ambito o nivel donde se ha declarado
    private int next;

    public Datos(Descripcion descripcion, int first, String idcamp, int np, int next) {
        this.descripcion = descripcion;
        this.first = first;
        this.idcamp = idcamp;
        this.next = next;
        this.np = np;
    }


    public Descripcion getDescripcion() {
        return descripcion;
    }

    public int getFirst() {
        return first;
    }

    public String getIdcamp() {
        return idcamp;
    }

    public int getNext() {
        return next;
    }

    public int getNp() {
        return np;
    }

    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setIdcamp(String idcamp) {
        this.idcamp = idcamp;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public void setNp(int np) {
        this.np = np;
    }
}
