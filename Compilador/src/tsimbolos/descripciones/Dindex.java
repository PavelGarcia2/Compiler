package tsimbolos.descripciones;

public class Dindex extends Descripcion {                         // entrada que correspon a una variable

    private int indice;                   // identificador del tipus de la variable

    public Dindex(int indice){
        super(TDesc.dindex);
        this.indice = indice;
    }
    
    public int getIndice(){
        return indice;
    }

}