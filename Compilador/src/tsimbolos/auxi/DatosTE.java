package tsimbolos.auxi;

import tsimbolos.descripciones.Descripcion;

public class DatosTE {

    private String idcamp;
    private Descripcion d;
    private int np; //Ambito o nivel donde se ha declarado
    private int next;

    public DatosTE(int next,String idcamp, Descripcion d, int np){
        this.next = next;
        this.idcamp = idcamp;
        this.d = d;
        this.np = np;
    }

    public int getNext(){
        return next;
    }

    public String getIdcamp() {
        return idcamp;
    }

    public Descripcion getD() {
        return d;
    }

    public int getNp() {
        return np;
    }

    public void setNext(int next){
        this.next = next;
    }

    public void setIdcamp(String idcamp) {
        this.idcamp = idcamp;
    }

    public void setD(Descripcion d) {
        this.d = d;
    }

    public void setNp(int np) {
        this.np = np;
    }


    public String toString(){
        return "[ idCamp: "+idcamp+", descripcion: "+d+", np: "+np+"]";
    }


}
