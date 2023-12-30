package tsimbolos.descripciones;

public class Dindex extends Descripcion {                         // entrada que correspon a una variable

    private String  tipus;                   // identificador del tipus de la variable

    public Dindex(String tipus){
        super(TDesc.dindex);
        this.tipus = tipus;
    }

    public String getTipus(){
        return tipus;
    }


    public String toString(){
        return "Dindex {" + "tipus=" + tipus + '}';
    }
    
    
}