package tsimbolos.descripciones;


public class DConst extends Descripcion{

    private int valor;
    private String tipo;

    public DConst (String tipo, int valor){
        super(TDesc.dconst);
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getValor(){
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