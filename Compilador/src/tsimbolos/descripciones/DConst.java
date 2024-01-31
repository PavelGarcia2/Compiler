package tsimbolos.descripciones;
import herramientas.Tipo;
import sintactico.arbol.NodoId;

public class DConst extends Descripcion{

    private String id;
    private int valor;
    private Tipo tipo;
    private NodoId nodoId;

    public DConst (Tipo tipo, int valor, String id, NodoId nodoId){
        super(TDesc.dconst);
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
        this.nodoId = nodoId;
    }

    public int getValor(){
        return this.valor;
    }

    public Tipo getTipo(){
        return this.tipo;
    }

    public NodoId getNodoId(){
        return this.nodoId;
    }


    @Override
    public String toString(){
        return "DConst {" + this.tipo + " " + this.valor + "}";
    }



}