package tsimbolos.descripciones;


public class DConst extends Descripcion{

    private Object valor;
    private String tipo;

    public DConst (String tipo, Object valor){
        super(TDesc.dconst);
        this.tipo = tipo;
        this.valor = valor;
    }

    public Object getValor(){
        return this.valor;
    }

    public String getTipo(){
        return this.tipo;
    }


    @Override
    public String toString(){
        return "DConst {" + this.tipo + " " + this.valor + "}";
    }



}