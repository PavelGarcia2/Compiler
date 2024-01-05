package tsimbolos.descripciones;
import herramientas.Tipo;

public class DConst extends Descripcion{

    private String id;
    private int valor;
    private Tipo tipo;

    public DConst (Tipo tipo, int valor, String id){
        super(TDesc.dconst);
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
    }

    public int getValor(){
        return this.valor;
    }

    public Tipo getTipo(){
        return this.tipo;
    }


    @Override
    public String toString(){
        return "DConst {" + this.tipo + " " + this.valor + "}";
    }



}