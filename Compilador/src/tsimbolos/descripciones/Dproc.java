package tsimbolos.descripciones;

import java.util.ArrayList;

public class Dproc extends Descripcion{

    private int np;
    private ArrayList<Dargin> argumentos;

    public Dproc(){
        super(TDesc.dproc);
        np += 1;
    }

    public int getNp() {
        return np;
    }

    public void addArg(Dargin dargin){
        argumentos.add(dargin);
    }

    @Override
    public String toString() {
        return "Dproc{" +"np=" + np +'}';
    }
}
